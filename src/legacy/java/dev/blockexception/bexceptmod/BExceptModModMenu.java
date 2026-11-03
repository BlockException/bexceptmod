package dev.blockexception.bexceptmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import net.fabricmc.loader.api.FabricLoader;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class BExceptModModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> FabricLoader.getInstance().isModLoaded("cloth-config")
				? BExceptModConfigScreen.create(parent)
				: new MissingConfigDependencyScreen(parent);
	}

	private static final class MissingConfigDependencyScreen extends Screen {
		private final Screen parent;

		private MissingConfigDependencyScreen(Screen parent) {
			super(BExceptModTranslations.text("bexceptmod.config.title"));
			this.parent = parent;
		}

		@Override
		protected void init() {
			addRenderableWidget(Button.builder(
					BExceptModTranslations.text("bexceptmod.config.back"),
					button -> Minecraft.getInstance().setScreenAndShow(parent)
			).bounds(width / 2 - 100, height / 2 + 20, 200, 20).build());
		}

		@Override
		public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
			super.render(graphics, mouseX, mouseY, delta);
			Component message = BExceptModTranslations.text("bexceptmod.config.missing_cloth");
			graphics.drawString(font, message, width / 2 - font.width(message) / 2, height / 2 - 12, 0xFFFFFFFF);
		}
	}
}
