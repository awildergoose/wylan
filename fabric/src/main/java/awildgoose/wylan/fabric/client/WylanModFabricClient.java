package awildgoose.wylan.fabric.client;

import awildgoose.wylan.client.WylanModClient;
import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.client.entity.HenryEntityRenderer;
import awildgoose.wylan.client.entity.SkinwalkerEntityRenderer;
import awildgoose.wylan.client.entity.ZelderBossEntityRenderer;
import awildgoose.wylan.init.ModBlockEntities;
import awildgoose.wylan.init.ModEntityTypes;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class WylanModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WylanModClient.init();
        BlockEntityRendererRegistry.register(ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(),
                                             PlushieBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.HENRY.get(), HenryEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SKINWALKER.get(), SkinwalkerEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.ZELDER_BOSS.get(), ZelderBossEntityRenderer::new);
    }
}
