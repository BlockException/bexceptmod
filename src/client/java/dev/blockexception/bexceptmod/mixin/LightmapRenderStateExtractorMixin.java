package dev.blockexception.bexceptmod.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;

import dev.blockexception.bexceptmod.BExceptModClient;

@Mixin(LightmapRenderStateExtractor.class)
public abstract class LightmapRenderStateExtractorMixin {
	@Shadow
	private boolean needsUpdate;

	@Shadow
	@Final
	public static org.joml.Vector3fc WHITE;

	@Inject(method = "extract", at = @At("HEAD"))
	private void bexceptmod$refreshWhenNightVisionIsEnabled(LightmapRenderState state, float tickProgress, CallbackInfo callbackInfo) {
		if (BExceptModClient.isGammaEnabled()) {
			needsUpdate = true;
		}
	}

	@Inject(method = "extract", at = @At("TAIL"))
	private void bexceptmod$applyClientNightVision(LightmapRenderState state, float tickProgress, CallbackInfo callbackInfo) {
		if (BExceptModClient.isGammaEnabled()) {
			state.nightVisionEffectIntensity = BExceptModClient.getNightVisionStrength();
			state.nightVisionColor = WHITE;
		}
	}
}
