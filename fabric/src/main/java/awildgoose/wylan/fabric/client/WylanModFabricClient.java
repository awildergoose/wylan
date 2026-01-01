package awildgoose.wylan.fabric.client;

import awildgoose.wylan.init.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public final class WylanModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // TODO port to stupid neoforge
        BlockRenderLayerMap.putBlock(ModBlocks.KAT_PLUSHIE.get(), ChunkSectionLayer.CUTOUT);
    }
}
