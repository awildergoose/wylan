package awildgoose.wylan.mixin.client;

import awildgoose.wylan.WylanMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WeatherEffectRenderer.class)
public class WeatherEffectRendererMixin {
	@Unique private static final ResourceLocation BLOOD_RAIN_LOCATION = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																											  "textures/environment/blood.png");
	@Shadow @Final @Mutable private static ResourceLocation RAIN_LOCATION;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void modifyRainTexture(CallbackInfo ci) {
		RAIN_LOCATION = BLOOD_RAIN_LOCATION;
	}
}
