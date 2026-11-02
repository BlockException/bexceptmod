package dev.blockexception.bexceptmod;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class BExceptModConfigScreen {
	private BExceptModConfigScreen() {
	}

	static Screen create(Screen parent) {
		BExceptModConfig config = BExceptModClient.getConfig();
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.translatable("bexceptmod.config.title"))
				.setSavingRunnable(() -> {
					config.save();
					BExceptModClient.applyConfigSettings();
				});
		ConfigEntryBuilder entries = builder.entryBuilder();

		ConfigCategory controls = builder.getOrCreateCategory(Component.translatable("bexceptmod.config.category.controls"));
		controls.addEntry(entries.fillKeybindingField(
				Component.translatable("key.bexceptmod.toggle_gamma"),
				BExceptModClient.getGammaKeyMapping()
		).build());
		controls.addEntry(entries.fillKeybindingField(
				Component.translatable("key.bexceptmod.toggle_performance"),
				BExceptModClient.getPerformanceKeyMapping()
		).build());

		ConfigCategory hud = builder.getOrCreateCategory(Component.translatable("bexceptmod.config.category.hud"));
		hud.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.show_fps"), config.showFps)
				.setDefaultValue(true).setSaveConsumer(value -> config.showFps = value).build());
		hud.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.show_cpu"), config.showCpu)
				.setDefaultValue(true).setSaveConsumer(value -> config.showCpu = value).build());
		hud.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.show_ram"), config.showRam)
				.setDefaultValue(true).setSaveConsumer(value -> config.showRam = value).build());

		ConfigCategory nightVision = builder.getOrCreateCategory(Component.translatable("bexceptmod.config.category.night_vision"));
		nightVision.addEntry(entries.startIntSlider(
						Component.translatable("bexceptmod.config.night_vision_strength"),
						config.nightVisionPercent, 1, 100
				).setDefaultValue(100).setSaveConsumer(value -> config.nightVisionPercent = value)
				.setTextGetter(value -> Component.literal(value + "%")).build());

		ConfigCategory performance = builder.getOrCreateCategory(Component.translatable("bexceptmod.config.category.performance"));
		performance.addEntry(entries.startIntSlider(
						Component.translatable("bexceptmod.config.render_distance"),
						config.renderDistance, 2, 32
				).setDefaultValue(8).setSaveConsumer(value -> config.renderDistance = value).build());
		performance.addEntry(entries.startIntSlider(
						Component.translatable("bexceptmod.config.simulation_distance"),
						config.simulationDistance, 2, 32
				).setDefaultValue(5).setSaveConsumer(value -> config.simulationDistance = value).build());
		performance.addEntry(entries.startIntSlider(
						Component.translatable("bexceptmod.config.entity_distance"),
						config.entityDistancePercent, 10, 100
				).setDefaultValue(50).setSaveConsumer(value -> config.entityDistancePercent = value)
				.setTextGetter(value -> Component.literal(value + "%")).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.disable_clouds"), config.disableClouds)
				.setDefaultValue(true).setSaveConsumer(value -> config.disableClouds = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.disable_ambient_occlusion"), config.disableAmbientOcclusion)
				.setDefaultValue(true).setSaveConsumer(value -> config.disableAmbientOcclusion = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.disable_entity_shadows"), config.disableEntityShadows)
				.setDefaultValue(true).setSaveConsumer(value -> config.disableEntityShadows = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.minimal_particles"), config.minimalParticles)
				.setDefaultValue(true).setSaveConsumer(value -> config.minimalParticles = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.reduce_explosion_effects"), config.reduceExplosionEffects)
				.setDefaultValue(false).setSaveConsumer(value -> config.reduceExplosionEffects = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.disable_biome_blend"), config.disableBiomeBlend)
				.setDefaultValue(true).setSaveConsumer(value -> config.disableBiomeBlend = value).build());
		performance.addEntry(entries.startBooleanToggle(Component.translatable("bexceptmod.config.disable_mipmaps"), config.disableMipmaps)
				.setDefaultValue(true).setSaveConsumer(value -> config.disableMipmaps = value).build());

		return builder.build();
	}
}
