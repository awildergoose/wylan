package awildgoose.wylan.fabric.mixin.client.sodium;

import awildgoose.wylan.fabric.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Mixin;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
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
}
