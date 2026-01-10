package awildgoose.wylan.fabric.client;

import awildgoose.wylan.ScreenshakeInstance;
import awildgoose.wylan.client.ScreenshakeHandler;
import awildgoose.wylan.client.WylanModClient;
import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.client.entity.GumballPelletEntityRenderer;
import awildgoose.wylan.client.entity.HenryEntityRenderer;
import awildgoose.wylan.client.entity.SkinwalkerEntityRenderer;
import awildgoose.wylan.client.entity.ZelderBossEntityRenderer;
import awildgoose.wylan.client.init.ModClientCommands;
import awildgoose.wylan.init.ModBlockEntities;
import awildgoose.wylan.init.ModEntityTypes;
import awildgoose.wylan.payloads.ScreenshakeS2CPayload;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
        EntityRendererRegistry.register(ModEntityTypes.GUMBALL_PELLET.get(), GumballPelletEntityRenderer::new);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> ModClientCommands.init(dispatcher));
        ClientPlayNetworking.registerGlobalReceiver(ScreenshakeS2CPayload.ID, (payload, context) -> ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(
                payload.duration(),
                payload.startingStrength(),
                payload.endingStrength(),
                payload.falloffDistance(),
                payload.center()
        )));
    }
}
