package awildgoose.wylan.neoforge.mixin.client.sodium;

import awildgoose.wylan.client.init.ModRendering;
import awildgoose.wylan.neoforge.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultMaterials.class)
public class SodiumDefaultMaterialsMixin {
	@Inject(at = @At("HEAD"), method = "forChunkLayer", cancellable = true)
	private static void forChunkLayer(ChunkSectionLayer layer, CallbackInfoReturnable<Material> cir) {
		if (layer.equals(ChunkSectionLayer.valueOf("LAVA"))) {
			cir.setReturnValue((Material) SodiumCompat.LAVA_MATERIAL);
		}
	}

	@Inject(at = @At("HEAD"), method = "forRenderLayer", cancellable = true)
	private static void forRenderLayer(RenderType layer, CallbackInfoReturnable<Material> cir) {
		if (layer.equals(ModRendering.LAVA)) {
			cir.setReturnValue((Material) SodiumCompat.LAVA_MATERIAL);
		}
	}
}
