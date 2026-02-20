package awildgoose.wylan.neoforge.mixin.client.sodium;

import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.caffeinemc.mods.sodium.client.gl.shader.GlProgram;
import net.caffeinemc.mods.sodium.client.render.chunk.ShaderChunkRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkFogMode;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import net.caffeinemc.mods.sodium.mixin.core.GlCommandEncoderAccessor;
import net.neoforged.neoforge.client.blaze3d.validation.ValidationGpuDevice;
import net.neoforged.neoforge.client.blaze3d.validation.ValidationGpuTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This is a NeoForge only fix!
@Mixin(ShaderChunkRenderer.class)
public abstract class NFSodiumShaderChunkRendererMixin {
	@Shadow @Final protected ChunkVertexType vertexType;
	@Shadow protected GlProgram<ChunkShaderInterface> activeProgram;
	@Shadow protected abstract GlProgram<ChunkShaderInterface> compileProgram(ChunkShaderOptions options);

	@SuppressWarnings("deprecation")
	@Inject(at = @At("HEAD"), method = "begin", cancellable = true)
	protected void begin(TerrainRenderPass pass, FogParameters parameters, CallbackInfo ci) {
		ci.cancel();
		RenderTarget target = pass.getTarget();

		var vColorTexture = target.getColorTexture();
		if (vColorTexture instanceof ValidationGpuTexture v) {
			if (v.getRealTexture() instanceof GlTexture glColorTexture) {
				ci.cancel();

				GlDevice glDevice = (GlDevice) ((ValidationGpuDevice) RenderSystem.getDevice()).getRealDevice();
				//noinspection DataFlowIssue
				GlTexture glDepthTexture =
						(GlTexture) ((ValidationGpuTexture)target.getDepthTexture()).getRealTexture();
				//noinspection ReferenceToMixin
				GlCommandEncoderAccessor glCommandEncoder = (GlCommandEncoderAccessor) glDevice.createCommandEncoder();

				GlStateManager._viewport(0, 0, glColorTexture.getWidth(0), glColorTexture.getHeight(0));
				GlStateManager._glBindFramebuffer(36160,
												  glColorTexture.getFbo(glDevice.directStateAccess(),
															  glDepthTexture));
				glCommandEncoder.sodium$applyPipelineState(pass.getPipeline());
				glCommandEncoder.sodium$setLastProgram(null);
				ChunkShaderOptions options = new ChunkShaderOptions(ChunkFogMode.SMOOTH, pass, this.vertexType);
				this.activeProgram = this.compileProgram(options);
				this.activeProgram.bind();
				this.activeProgram.getInterface().setupState(pass, parameters);
			}
		}
	}
}
