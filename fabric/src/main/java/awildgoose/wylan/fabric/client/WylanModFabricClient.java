package awildgoose.wylan.fabric.client;

import awildgoose.wylan.client.WylanModClient;
import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.client.entity.HenryEntityRenderer;
import awildgoose.wylan.client.entity.SkinwalkerEntityRenderer;
import awildgoose.wylan.client.entity.ZelderBossEntityRenderer;
import awildgoose.wylan.client.init.ModRendering;
import awildgoose.wylan.init.ModBlockEntities;
import awildgoose.wylan.init.ModEntityTypes;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.network.chat.Component;

public final class WylanModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WylanModClient.init();
        BlockEntityRendererRegistry.register(ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(),
                                             PlushieBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.HENRY.get(), HenryEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SKINWALKER.get(), SkinwalkerEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.ZELDER_BOSS.get(), ZelderBossEntityRenderer::new);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("paintitblack").executes(context -> {
                context.getSource().sendFeedback(Component.literal("I see lava, and I want it to turn to oil."));
                ModRendering.lavaTransitionState = ModRendering.LavaTransitionState.LAVA_TO_OIL;
                return 1;
            }));

            dispatcher.register(ClientCommandManager.literal("paintitred").executes(context -> {
                context.getSource().sendFeedback(Component.literal("I see oil, and I want it to turn to lava."));
                ModRendering.lavaTransitionState = ModRendering.LavaTransitionState.OIL_TO_LAVA;
                return 1;
            }));
        });
    }
}
