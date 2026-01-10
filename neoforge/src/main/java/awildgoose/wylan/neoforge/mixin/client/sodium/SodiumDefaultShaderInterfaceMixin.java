package awildgoose.wylan.neoforge.mixin.client.sodium;

import awildgoose.wylan.client.init.ModRendering;
import awildgoose.wylan.neoforge.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.gl.shader.uniform.GlUniformFloat;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ShaderBindingContext;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultShaderInterface.class)
public class SodiumDefaultShaderInterfaceMixin {
	@Unique private @Nullable GlUniformFloat wylan$lavaTransitionProgress;

	@Inject(at = @At("TAIL"), method = "<init>")
	public void create(ShaderBindingContext context, ChunkShaderOptions options, CallbackInfo ci) {
		if (options.pass().equals(SodiumCompat.LAVA_PASS))
			this.wylan$lavaTransitionProgress = context.bindUniform("u_LavaTransitionProgress", GlUniformFloat::new);
		else
			this.wylan$lavaTransitionProgress = null;
	}

	@Inject(at = @At("TAIL"), method = "setupState", remap = false)
	public void setupState(TerrainRenderPass pass, FogParameters parameters, CallbackInfo ci) {
		if (this.wylan$lavaTransitionProgress != null)
			this.wylan$lavaTransitionProgress.set(ModRendering.lavaTransitionProgress);
	}
}
