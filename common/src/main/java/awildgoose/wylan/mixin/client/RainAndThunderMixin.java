package awildgoose.wylan.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public class RainAndThunderMixin {
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(BooleanSupplier booleanSupplier, CallbackInfo ci) {
		((ClientLevel)(Object)this).setRainLevel(1.0f);
		((ClientLevel)(Object)this).setThunderLevel(1.0f);
	}
}
