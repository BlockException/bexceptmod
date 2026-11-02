package dev.blockexception.bexceptmod.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.particles.ExplosionParticleInfo;

import dev.blockexception.bexceptmod.BExceptModClient;

@Mixin(ClientPacketListener.class)
public abstract class ExplosionEffectsMixin {
	@Redirect(
			method = "handleExplosion",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
			)
	)
	private void bexceptmod$reduceExplosionParticle(
			ClientLevel level,
			ParticleOptions particle,
			double x,
			double y,
			double z,
			double velocityX,
			double velocityY,
			double velocityZ
	) {
		if (!BExceptModClient.shouldReduceExplosionParticle()) {
			level.addParticle(particle, x, y, z, velocityX, velocityY, velocityZ);
		}
	}

	@Redirect(
			method = "handleExplosion",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/multiplayer/ClientLevel;trackExplosionEffects(Lnet/minecraft/world/phys/Vec3;FILnet/minecraft/util/random/WeightedList;)V"
			)
	)
	private void bexceptmod$reduceExplosionBlockParticles(
			ClientLevel level,
			Vec3 center,
			float radius,
			int blockCount,
			WeightedList<ExplosionParticleInfo> blockParticles
	) {
		if (!BExceptModClient.shouldReduceExplosionDebris()) {
			level.trackExplosionEffects(center, radius, blockCount, blockParticles);
		}
	}
}
