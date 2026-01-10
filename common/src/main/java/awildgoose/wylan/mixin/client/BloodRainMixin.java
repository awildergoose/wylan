package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.init.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public class BloodRainMixin {
	@Shadow @Final
	private LevelRenderer levelRenderer;

	@SuppressWarnings("DataFlowIssue")
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle" +
			"(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", cancellable = true)
	public void addParticle(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed,
					   double zSpeed, CallbackInfo ci) {
		if (((Level)(Object)this).dimension().equals(WylanMod.ZELDER_ARENA)) {
			ci.cancel();
			var newParticle = ModParticles.BLOOD.get();
			this.levelRenderer.addParticle(
					newParticle,
					newParticle.getType().getOverrideLimiter(),
					x, y, z, xSpeed, ySpeed, zSpeed
			);
		}
	}
}
