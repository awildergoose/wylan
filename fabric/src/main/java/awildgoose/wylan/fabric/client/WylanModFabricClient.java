package awildgoose.wylan.fabric.client;

import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.init.ModBlockEntities;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class WylanModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(),
                                             PlushieBlockEntityRenderer::new);
    }
}
