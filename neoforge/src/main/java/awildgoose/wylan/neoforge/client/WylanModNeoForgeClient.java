package awildgoose.wylan.neoforge.client;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.client.ClientPayloadHandlers;
import awildgoose.wylan.client.WylanModClient;
import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.client.entity.GumballPelletEntityRenderer;
import awildgoose.wylan.client.entity.HenryEntityRenderer;
import awildgoose.wylan.client.entity.SkinwalkerEntityRenderer;
import awildgoose.wylan.client.entity.ZelderBossEntityRenderer;
import awildgoose.wylan.client.init.ModClientCommands;
import awildgoose.wylan.client.particle.BloodDropParticle;
import awildgoose.wylan.init.ModBlockEntities;
import awildgoose.wylan.init.ModEntityTypes;
import awildgoose.wylan.init.ModParticles;
import awildgoose.wylan.payloads.ScreenshakeS2CPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = WylanMod.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = WylanMod.MOD_ID)
public final class WylanModNeoForgeClient {
	public WylanModNeoForgeClient() {
		WylanModClient.init();
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(
				ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(),
				PlushieBlockEntityRenderer::new
		);
		event.registerEntityRenderer(ModEntityTypes.HENRY.get(), HenryEntityRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.SKINWALKER.get(), SkinwalkerEntityRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.ZELDER_BOSS.get(), ZelderBossEntityRenderer::new);
		event.registerEntityRenderer(ModEntityTypes.GUMBALL_PELLET.get(), GumballPelletEntityRenderer::new);
	}

	@SubscribeEvent
	public static void registerClientCommands(RegisterClientCommandsEvent event) {
		ModClientCommands.init(event.getDispatcher());
	}

	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticles.BLOOD.get(), BloodDropParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerClientPayloads(RegisterClientPayloadHandlersEvent event) {
		event.register(
				ScreenshakeS2CPayload.ID,
				(payload, context) -> ClientPayloadHandlers.handleScreenShakePacket(payload)
		);
	}
}
