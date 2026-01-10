package awildgoose.wylan.fabric.mixin.client.sodium;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.fabric.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.gl.shader.*;
import net.caffeinemc.mods.sodium.client.render.chunk.ShaderChunkRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderInterface;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderOptions;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.DefaultShaderInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ShaderChunkRenderer.class)
public class SodiumShaderChunkRendererMixin {
	@Shadow @Final private Map<ChunkShaderOptions, GlProgram<ChunkShaderInterface>> programs;

	@Shadow
	private static ShaderConstants createShaderConstants(ChunkShaderOptions options) {
		throw new AssertionError();
	}

	@Inject(at = @At("HEAD"), method = "compileProgram", cancellable = true, remap = false)
	protected void compileProgram(ChunkShaderOptions options,
								  CallbackInfoReturnable<GlProgram<ChunkShaderInterface>> cir) {
		if (options.pass().equals(SodiumCompat.LAVA_PASS)) {
			cir.cancel();

			GlProgram<ChunkShaderInterface> program = this.programs.get(options);
			if (program == null) {
				this.programs.put(options, program = this.createOurShader(options));
			}

			cir.setReturnValue(program);
		}
	}

	@Unique
	private GlProgram<ChunkShaderInterface> createOurShader(ChunkShaderOptions options) {
		ShaderConstants constants = createShaderConstants(options);
		GlShader vertShader = ShaderLoader.loadShader(ShaderType.VERTEX,
													  ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																							"core/rendertype_lava_sodium.vsh"), constants);
		GlShader fragShader = ShaderLoader.loadShader(ShaderType.FRAGMENT,
													  ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																							"core/rendertype_lava_sodium.fsh"), constants);

		GlProgram<ChunkShaderInterface> var6;
		try {
			var6 =
					GlProgram.builder(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "rendertype_lava"))
							.attachShader(vertShader)
							.attachShader(fragShader)
							.bindAttribute("a_Position", 0)
							.bindAttribute("a_Color", 1)
							.bindAttribute("a_TexCoord", 2)
							.bindAttribute("a_LightAndData", 3)
							.bindFragmentData("fragColor", 0)
							.link((shader) -> new DefaultShaderInterface(shader, options));
		} finally {
			vertShader.delete();
			fragShader.delete();
		}

		return var6;
	}
}
