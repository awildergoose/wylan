package awildgoose.wylan.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(WeatherEffectRenderer.class)
public class WeatherEffectRendererMixin {
	@ModifyVariable(
			method = "renderInstances",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/ARGB;white(F)I"
			)
	)
	private int blood(float alpha) {
		int alphaInt = Math.min(255, Math.max(0, (int)(alpha * 255)));

		int red = 178;
		int green = 34;
		int blue = 34;

		return (alphaInt << 24) | (red << 16) | (green << 8) | blue;
	}
}
