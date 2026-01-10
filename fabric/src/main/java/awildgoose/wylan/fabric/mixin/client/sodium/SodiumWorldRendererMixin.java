package awildgoose.wylan.fabric.mixin.client.sodium;

import awildgoose.wylan.fabric.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SodiumWorldRenderer.class)
public class SodiumWorldRendererMixin {
	@Shadow(remap = false) private RenderSectionManager renderSectionManager;
	@Shadow(remap = false) private FogParameters lastFogParameters;

	@Inject(at = @At("HEAD"), method = "drawChunkLayer", cancellable = true)
	public void drawChunkLayer(ChunkSectionLayerGroup group, ChunkRenderMatrices matrices, double x, double y, double z, CallbackInfo ci) {
		if (group.equals(ChunkSectionLayerGroup.valueOf("LAVA"))) {
			ci.cancel();

			this.renderSectionManager.renderLayer(matrices, SodiumCompat.LAVA_PASS, x, y, z,
												  this.lastFogParameters);
		}
	}
}
