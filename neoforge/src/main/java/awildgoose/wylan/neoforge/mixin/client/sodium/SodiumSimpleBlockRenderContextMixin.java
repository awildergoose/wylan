package awildgoose.wylan.neoforge.mixin.client.sodium;

import awildgoose.wylan.client.init.ModRendering;
import net.caffeinemc.mods.sodium.client.render.frapi.render.SimpleBlockRenderContext;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleBlockRenderContext.class)
public class SodiumSimpleBlockRenderContextMixin {
	@Inject(at = @At("HEAD"), method = "toRenderLayer", cancellable = true)
	private void toRenderLayer(ChunkSectionLayer renderType, CallbackInfoReturnable<RenderType> cir) {
		if (renderType.equals(ChunkSectionLayer.valueOf("LAVA"))) {
			cir.setReturnValue(ModRendering.LAVA);
		}
	}
}
