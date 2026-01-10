package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AtmosphericFogEnvironment.class)
public class AtmosphericFogEnvironmentMixin {
	@Inject(at = @At("TAIL"), method = "setupFog")
	public void setupFog(FogData fogData, Entity entity, BlockPos blockPos, ClientLevel clientLevel, float f,
						 DeltaTracker deltaTracker, CallbackInfo ci) {
		if (clientLevel.dimension().equals(WylanMod.ZELDER_ARENA)) {
			fogData.environmentalEnd = (float) (fogData.environmentalStart + 50.0);
		}
	}
}
