package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Level.class)
public class AllowWeatherInLevelMixin {
	@SuppressWarnings("DataFlowIssue")
	@Inject(at = @At("HEAD"), method = "canHaveWeather", cancellable = true)
	private void canHaveWeather(CallbackInfoReturnable<Boolean> cir) {
		if (((Level)(Object)this).dimension().equals(WylanMod.ZELDER_ARENA))
			cir.setReturnValue(true);
	}
}
