package dev.blockexception.bexceptmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;

import dev.blockexception.bexceptmod.BExceptModClient;

@Mixin(GameRenderer.class)
public abstract class LightmapRenderStateExtractorMixin {
	@Inject(method = "getNightVisionScale", at = @At("RETURN"), cancellable = true)
	private static void bexceptmod$applyClientNightVision(
			LivingEntity entity,
			float tickProgress,
			CallbackInfoReturnable<Float> callbackInfo
	) {
		if (BExceptModClient.isGammaEnabled()) {
			callbackInfo.setReturnValue(BExceptModClient.getNightVisionStrength());
		}
	}
}
