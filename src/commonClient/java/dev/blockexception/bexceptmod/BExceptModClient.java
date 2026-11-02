package dev.blockexception.bexceptmod;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.CloudStatus;
import net.minecraft.server.level.ParticleStatus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class BExceptModClient implements ClientModInitializer {
	private static final OperatingSystemMXBean OS_BEAN =
			ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	private static final BExceptModConfig CONFIG = BExceptModConfig.load();
	private static boolean gammaEnabled;
	private static PerformanceSnapshot performanceSnapshot;

	@Override
	public void onInitializeClient() {
		BExceptModClientPlatform.initialize();
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (getGammaKeyMapping().consumeClick()) {
				gammaEnabled = !gammaEnabled;
			}

			while (getPerformanceKeyMapping().consumeClick()) {
				togglePerformanceMode(client);
			}
		});
	}

	static List<String> getHudLines() {
		Minecraft minecraft = Minecraft.getInstance();
		long maxMemory = Runtime.getRuntime().maxMemory();
		long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long freeMemory = maxMemory - usedMemory;

		List<String> lines = new ArrayList<>();
		if (CONFIG.showFps) {
			lines.add("FPS: " + minecraft.getFps());
			if (CONFIG.showFrameTime) {
				lines.add(String.format(java.util.Locale.ROOT, "Frame: %.1f ms", minecraft.getFrameTimeNs() / 1_000_000.0));
			}
		}
		if (CONFIG.showCpu) {
			lines.add("CPU: " + getCpuUsage());
		}
		if (CONFIG.showRam) {
			lines.add("RAM: " + toMegabytes(usedMemory) + " / " + toMegabytes(freeMemory) + " MB");
		}
		if (performanceSnapshot != null) {
			lines.add("PERF: ON");
		}
		if (lines.isEmpty()) {
			return List.of();
		}
		return lines;
	}

	private static void togglePerformanceMode(Minecraft client) {
		if (performanceSnapshot != null) {
			performanceSnapshot.restore(client);
			performanceSnapshot = null;
			return;
		}

		performanceSnapshot = PerformanceSnapshot.capture(client);
		applyPerformanceSettings(client);
	}

	private static void applyPerformanceSettings(Minecraft client) {
		client.options.renderDistance().set(CONFIG.renderDistance);
		client.options.simulationDistance().set(CONFIG.simulationDistance);
		client.options.entityDistanceScaling().set(CONFIG.entityDistancePercent / 100.0);
		client.options.cloudStatus().set(CONFIG.disableClouds ? CloudStatus.OFF : performanceSnapshot.cloudStatus);
		client.options.ambientOcclusion().set(!CONFIG.disableAmbientOcclusion && performanceSnapshot.ambientOcclusion);
		client.options.entityShadows().set(!CONFIG.disableEntityShadows && performanceSnapshot.entityShadows);
		client.options.particles().set(CONFIG.minimalParticles ? ParticleStatus.MINIMAL : performanceSnapshot.particles);
		client.options.biomeBlendRadius().set(CONFIG.disableBiomeBlend ? 0 : performanceSnapshot.biomeBlendRadius);
		client.options.mipmapLevels().set(CONFIG.disableMipmaps ? 0 : performanceSnapshot.mipmapLevels);
	}

	static void applyConfigSettings() {
		if (performanceSnapshot != null) {
			Minecraft client = Minecraft.getInstance();
			performanceSnapshot.restore(client);
			performanceSnapshot = null;
			togglePerformanceMode(client);
		}
	}

	public static boolean isGammaEnabled() {
		return gammaEnabled && CONFIG.nightVisionPercent > 0;
	}

	public static float getNightVisionStrength() {
		return CONFIG.nightVisionPercent / 100.0F;
	}

	public static boolean shouldReduceExplosionParticle() {
		return CONFIG.reduceExplosionParticle;
	}

	public static boolean shouldReduceExplosionDebris() {
		return CONFIG.reduceExplosionDebris;
	}

	static KeyMapping getGammaKeyMapping() {
		return BExceptModClientPlatform.getGammaKeyMapping();
	}

	static KeyMapping getPerformanceKeyMapping() {
		return BExceptModClientPlatform.getPerformanceKeyMapping();
	}

	static BExceptModConfig getConfig() {
		return CONFIG;
	}

	private static String getCpuUsage() {
		if (OS_BEAN == null) {
			return "n/v";
		}

		double load = OS_BEAN.getProcessCpuLoad();
		return load < 0 ? "n/v" : Math.round(load * 100) + "%";
	}

	private static long toMegabytes(long bytes) {
		return bytes / (1024 * 1024);
	}

	private record PerformanceSnapshot(
			int renderDistance,
			int simulationDistance,
			double entityDistanceScaling,
			CloudStatus cloudStatus,
			boolean ambientOcclusion,
			boolean entityShadows,
			ParticleStatus particles,
			int biomeBlendRadius,
			int mipmapLevels
	) {
		private static PerformanceSnapshot capture(Minecraft client) {
			return new PerformanceSnapshot(
					client.options.renderDistance().get(),
					client.options.simulationDistance().get(),
					client.options.entityDistanceScaling().get(),
					client.options.cloudStatus().get(),
					client.options.ambientOcclusion().get(),
					client.options.entityShadows().get(),
					client.options.particles().get(),
					client.options.biomeBlendRadius().get(),
					client.options.mipmapLevels().get()
			);
		}

		private void restore(Minecraft client) {
			client.options.renderDistance().set(renderDistance);
			client.options.simulationDistance().set(simulationDistance);
			client.options.entityDistanceScaling().set(entityDistanceScaling);
			client.options.cloudStatus().set(cloudStatus);
			client.options.ambientOcclusion().set(ambientOcclusion);
			client.options.entityShadows().set(entityShadows);
			client.options.particles().set(particles);
			client.options.biomeBlendRadius().set(biomeBlendRadius);
			client.options.mipmapLevels().set(mipmapLevels);
		}
	}
}
