package dev.blockexception.bexceptmod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

final class BExceptModConfig {
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("bexceptmod.properties");

	String language = "en_us";
	boolean showFps = true;
	boolean showFrameTime;
	boolean showCpu = true;
	boolean showRam = true;
	int nightVisionPercent = 100;
	int renderDistance = 8;
	int simulationDistance = 5;
	int entityDistancePercent = 50;
	boolean disableClouds = true;
	boolean disableAmbientOcclusion = true;
	boolean disableEntityShadows = true;
	boolean minimalParticles = true;
	boolean reduceExplosionParticle;
	boolean reduceExplosionDebris;
	boolean disableBiomeBlend = true;
	boolean disableMipmaps = true;

	private BExceptModConfig() {
	}

	static BExceptModConfig load() {
		BExceptModConfig config = new BExceptModConfig();
		if (!Files.exists(FILE)) {
			return config;
		}

		Properties properties = new Properties();
		try (InputStream input = Files.newInputStream(FILE)) {
			properties.load(input);
		} catch (IOException exception) {
			throw new IllegalStateException("Could not read BExceptMod config at " + FILE, exception);
		}

		config.language = BExceptModTranslations.normalizeLanguage(properties.getProperty("language", config.language));
		config.showFps = readBoolean(properties, "showFps", config.showFps);
		config.showFrameTime = readBoolean(properties, "showFrameTime", config.showFrameTime);
		config.showCpu = readBoolean(properties, "showCpu", config.showCpu);
		config.showRam = readBoolean(properties, "showRam", config.showRam);
		config.nightVisionPercent = readInt(properties, "nightVisionPercent", config.nightVisionPercent, 1, 100);
		config.renderDistance = readInt(properties, "renderDistance", config.renderDistance, 2, 32);
		config.simulationDistance = readInt(properties, "simulationDistance", config.simulationDistance, 2, 32);
		config.entityDistancePercent = readInt(properties, "entityDistancePercent", config.entityDistancePercent, 10, 100);
		config.disableClouds = readBoolean(properties, "disableClouds", config.disableClouds);
		config.disableAmbientOcclusion = readBoolean(properties, "disableAmbientOcclusion", config.disableAmbientOcclusion);
		config.disableEntityShadows = readBoolean(properties, "disableEntityShadows", config.disableEntityShadows);
		config.minimalParticles = readBoolean(properties, "minimalParticles", config.minimalParticles);
		boolean reduceExplosionEffects = readBoolean(properties, "reduceExplosionEffects", false);
		config.reduceExplosionParticle = readBoolean(properties, "reduceExplosionParticle", reduceExplosionEffects);
		config.reduceExplosionDebris = readBoolean(properties, "reduceExplosionDebris", reduceExplosionEffects);
		config.disableBiomeBlend = readBoolean(properties, "disableBiomeBlend", config.disableBiomeBlend);
		config.disableMipmaps = readBoolean(properties, "disableMipmaps", config.disableMipmaps);
		return config;
	}

	void save() {
		Properties properties = new Properties();
		properties.setProperty("language", language);
		properties.setProperty("showFps", Boolean.toString(showFps));
		properties.setProperty("showFrameTime", Boolean.toString(showFrameTime));
		properties.setProperty("showCpu", Boolean.toString(showCpu));
		properties.setProperty("showRam", Boolean.toString(showRam));
		properties.setProperty("nightVisionPercent", Integer.toString(nightVisionPercent));
		properties.setProperty("renderDistance", Integer.toString(renderDistance));
		properties.setProperty("simulationDistance", Integer.toString(simulationDistance));
		properties.setProperty("entityDistancePercent", Integer.toString(entityDistancePercent));
		properties.setProperty("disableClouds", Boolean.toString(disableClouds));
		properties.setProperty("disableAmbientOcclusion", Boolean.toString(disableAmbientOcclusion));
		properties.setProperty("disableEntityShadows", Boolean.toString(disableEntityShadows));
		properties.setProperty("minimalParticles", Boolean.toString(minimalParticles));
		properties.setProperty("reduceExplosionParticle", Boolean.toString(reduceExplosionParticle));
		properties.setProperty("reduceExplosionDebris", Boolean.toString(reduceExplosionDebris));
		properties.setProperty("disableBiomeBlend", Boolean.toString(disableBiomeBlend));
		properties.setProperty("disableMipmaps", Boolean.toString(disableMipmaps));

		try {
			Files.createDirectories(FILE.getParent());
			try (OutputStream output = Files.newOutputStream(FILE)) {
				properties.store(output, "BExceptMod settings");
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not save BExceptMod config at " + FILE, exception);
		}
	}

	private static boolean readBoolean(Properties properties, String key, boolean defaultValue) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
			throw new IllegalStateException("Invalid boolean value for BExceptMod config key " + key + ": " + value);
		}
		return Boolean.parseBoolean(value);
	}

	private static int readInt(Properties properties, String key, int defaultValue, int min, int max) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		try {
			int parsed = Integer.parseInt(value);
			if (parsed < min || parsed > max) {
				throw new IllegalStateException("BExceptMod config key " + key + " must be between " + min + " and " + max);
			}
			return parsed;
		} catch (NumberFormatException exception) {
			throw new IllegalStateException("Invalid integer value for BExceptMod config key " + key + ": " + value, exception);
		}
	}
}
