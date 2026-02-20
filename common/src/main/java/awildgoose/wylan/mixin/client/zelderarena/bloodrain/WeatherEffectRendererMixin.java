package awildgoose.wylan.mixin.client.zelderarena.bloodrain;

import awildgoose.wylan.WylanMod;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(WeatherEffectRenderer.class)
public abstract class WeatherEffectRendererMixin {
	@Unique private static final ResourceLocation BLOOD_RAIN_LOCATION = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																											  "textures/environment/blood.png");

	@Shadow private void renderInstances(VertexConsumer vertexConsumer,
										 List<WeatherEffectRenderer.ColumnInstance> list, Vec3 vec3, float f, int i,
										 float g) {}

	@Shadow private void collectColumnInstances(Level level, int i, float f, Vec3 vec3, int j,
												List<WeatherEffectRenderer.ColumnInstance> list,
												List<WeatherEffectRenderer.ColumnInstance> list2) {}

	@Inject(method = "Lnet/minecraft/client/renderer/WeatherEffectRenderer;render(Lnet/minecraft/world/level/Level;" +
			"Lnet/minecraft/client/renderer/MultiBufferSource;IFLnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"),
			cancellable = true)
	public void render(Level level, MultiBufferSource multiBufferSource, int i, float f, Vec3 vec3, CallbackInfo ci) {
		if (!level.dimension().equals(WylanMod.ZELDER_ARENA)) {
			return;
		}

		ci.cancel();

		float g = level.getRainLevel(f);

		if (!(g <= 0.0F)) {
			int j = Minecraft.useFancyGraphics() ? 10 : 5;
			List<WeatherEffectRenderer.ColumnInstance> list = new ArrayList<>();
			//noinspection MismatchedQueryAndUpdateOfCollection
			List<WeatherEffectRenderer.ColumnInstance> list2 = new ArrayList<>();
			this.collectColumnInstances(level, i, f, vec3, j, list, list2);
			list.addAll(list2);

			//noinspection ConstantValue
			if (!list.isEmpty()) {
				RenderType renderType = RenderType.weather(BLOOD_RAIN_LOCATION, Minecraft.useShaderTransparency());
				this.renderInstances(multiBufferSource.getBuffer(renderType), list, vec3, 1.0F, i, f);
			}
		}
	}
}
