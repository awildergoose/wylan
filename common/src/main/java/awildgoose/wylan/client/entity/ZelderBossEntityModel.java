package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.ZelderBossEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@Environment(EnvType.CLIENT)
public class ZelderBossEntityModel extends DefaultedEntityGeoModel<ZelderBossEntity> {
	public ZelderBossEntityModel() {
		super(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "zelder_boss"), true);
	}
}