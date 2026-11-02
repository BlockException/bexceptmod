package dev.blockexception.bexceptmod;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ParticleStatus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

public class BExceptModClient implements ClientModInitializer {
	private static final OperatingSystemMXBean OS_BEAN =
			ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	private static final int PADDING = 5;
	private static final int LINE_HEIGHT = 10;
	private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath("bexceptmod", "general")
	);
	private static final KeyMapping TOGGLE_GAMMA = KeyMappingHelper.registerKeyMapping(
			new KeyMapping("key.bexceptmod.toggle_gamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY)
	);
	private static final KeyMapping TOGGLE_PERFORMANCE = KeyMappingHelper.registerKeyMapping(
			new KeyMapping("key.bexceptmod.toggle_performance", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY)
	);
	private static final BExceptModConfig CONFIG = BExceptModConfig.load();
	private static boolean gammaEnabled;
	private static PerformanceSnapshot performanceSnapshot;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (TOGGLE_GAMMA.consumeClick()) {
				gammaEnabled = !gammaEnabled;
			}

			while (TOGGLE_PERFORMANCE.consumeClick()) {
				togglePerformanceMode(client);
			}
		});

		HudElementRegistry.attachElementAfter(
				VanillaHudElements.SUBTITLES,
				Identifier.fromNamespaceAndPath("bexceptmod", "system_stats"),
				BExceptModClient::renderStats
		);
	}

	private static void renderStats(GuiGraphicsExtractor graphics, net.minecraft.client.DeltaTracker deltaTracker) {
		Minecraft minecraft = Minecraft.getInstance();
		long maxMemory = Runtime.getRuntime().maxMemory();
		long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long freeMemory = maxMemory - usedMemory;

		List<String> lines = new ArrayList<>();
		if (CONFIG.showFps) {
			lines.add("FPS: " + minecraft.getFps());
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
			return;
		}

		int widestLine = 0;
		for (String line : lines) {
			widestLine = Math.max(widestLine, minecraft.font.width(line));
		}

		int right = graphics.guiWidth() - 6;
		int left = right - widestLine - PADDING * 2;
		int top = 6;
		graphics.fill(left, top, right, top + LINE_HEIGHT * lines.size() + PADDING * 2, 0x90000000);

		for (int i = 0; i < lines.size(); i++) {
			graphics.text(minecraft.font, lines.get(i), left + PADDING, top + PADDING + i * LINE_HEIGHT, 0xFFFFFFFF);
		}
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

	static KeyMapping getGammaKeyMapping() {
		return TOGGLE_GAMMA;
	}

	static KeyMapping getPerformanceKeyMapping() {
		return TOGGLE_PERFORMANCE;
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
