package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.HenryEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT)
public class HenryEntityModel extends DefaultedEntityGeoModel<HenryEntity> {
	public HenryEntityModel() {
		super(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "henry"), true);
	}
}