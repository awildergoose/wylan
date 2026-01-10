package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.WaterDropParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WaterDropParticle.class)
public class BloodRainMixin {
	@SuppressWarnings("DataFlowIssue")
	@Inject(at = @At("TAIL"), method = "<init>")
	public void create(ClientLevel level, double x, double y, double z, CallbackInfo ci) {
		if (level.dimension().equals(WylanMod.ZELDER_ARENA))
			((WaterDropParticle)(Object)this).setColor(255.0F, 0.0F, 0.0F);
	}
}
