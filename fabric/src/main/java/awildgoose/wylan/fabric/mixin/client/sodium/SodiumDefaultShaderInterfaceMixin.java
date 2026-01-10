package awildgoose.wylan.fabric.mixin.client.sodium;

import awildgoose.wylan.client.init.ModRendering;
import net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformFloat;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ShaderBindingContext;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultShaderInterface.class)
public class SodiumDefaultShaderInterfaceMixin {
	@Unique private GlUniformFloat lavaTransitionProgress;

	@Inject(at = @At("TAIL"), method = "<init>")
	public void create(ShaderBindingContext context, ChunkShaderOptions options, CallbackInfo ci) {
		this.lavaTransitionProgress = context.bindUniform("u_LavaTransitionProgress", GlUniformFloat::new);
	}

	@Inject(at = @At("TAIL"), method = "setupState", remap = false)
	public void setupState(TerrainRenderPass pass, FogParameters parameters, CallbackInfo ci) {
		this.lavaTransitionProgress.set(ModRendering.lavaTransitionProgress);
	}
}
