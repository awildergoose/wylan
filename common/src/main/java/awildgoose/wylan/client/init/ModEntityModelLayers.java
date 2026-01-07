package awildgoose.wylan.client.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.client.entity.SkinwalkerEntityModel;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModEntityModelLayers {
	public static final ModelLayerLocation SKINWALKER = createMain("skinwalker");

	@SuppressWarnings("SameParameterValue")
	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, name), "main");
	}

	public static void init() {
		EntityModelLayerRegistry.register(ModEntityModelLayers.SKINWALKER,
										  SkinwalkerEntityModel::createBodyLayer);
	}
}
