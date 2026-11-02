package dev.blockexception.bexceptmod;

import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

final class BExceptModClientPlatform {
	private static final int PADDING = 5;
	private static final int LINE_HEIGHT = 10;
	private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath("bexceptmod", "general")
	);
	private static final KeyMapping TOGGLE_GAMMA = KeyBindingHelper.registerKeyBinding(
			new KeyMapping("key.bexceptmod.toggle_gamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY)
	);
	private static final KeyMapping TOGGLE_PERFORMANCE = KeyBindingHelper.registerKeyBinding(
			new KeyMapping("key.bexceptmod.toggle_performance", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY)
	);

	private BExceptModClientPlatform() {
	}

	static void initialize() {
		HudRenderCallback.EVENT.register((graphics, deltaTracker) -> renderStats(graphics));
	}

	static KeyMapping getGammaKeyMapping() {
		return TOGGLE_GAMMA;
	}

	static KeyMapping getPerformanceKeyMapping() {
		return TOGGLE_PERFORMANCE;
	}

	private static void renderStats(GuiGraphics graphics) {
		Minecraft minecraft = Minecraft.getInstance();
		List<String> lines = BExceptModClient.getHudLines();
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
			graphics.drawString(minecraft.font, lines.get(i), left + PADDING, top + PADDING + i * LINE_HEIGHT, 0xFFFFFFFF);
		}
	}
}
