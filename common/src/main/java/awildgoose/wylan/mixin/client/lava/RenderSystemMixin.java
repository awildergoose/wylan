package awildgoose.wylan.mixin.client.lava;

import awildgoose.wylan.client.render.RenderSystemExtension;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(RenderSystem.class)
public class RenderSystemMixin {
	@Inject(at = @At("TAIL"), method = "bindDefaultUniforms(Lcom/mojang/blaze3d/systems/RenderPass;)V", remap = false)
	private static void bindDefaultUniforms(RenderPass renderPass, CallbackInfo ci) {
		GpuBuffer gpuBuffer = RenderSystemExtension.getGooseUniformBuffer();
		if (gpuBuffer != null) {
			renderPass.setUniform("Goose", gpuBuffer);
		}
	}
}
