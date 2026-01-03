package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.client.entity.HenryEntityModel;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModEntityModelLayers {
	public static final ModelLayerLocation HENRY = createMain("henry");

	@SuppressWarnings("SameParameterValue")
	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, name), "main");
	}

	public static void init() {
		EntityModelLayerRegistry.register(ModEntityModelLayers.HENRY, HenryEntityModel::getTexturedModelData);
	}
}
