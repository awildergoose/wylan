package awildgoose.wylan.neoforge.client;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.client.block.entity.PlushieBlockEntityRenderer;
import awildgoose.wylan.init.ModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = WylanMod.MOD_ID, dist = Dist.CLIENT)
public final class WylanModNeoForgeClient {
	public WylanModNeoForgeClient() {}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(
				ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(),
				PlushieBlockEntityRenderer::new
		);
	}
}
