package awildgoose.wylan.client.entity;

import awildgoose.wylan.entity.ZelderBossEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

@Environment(EnvType.CLIENT)
public class ZelderBossEntityRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<ZelderBossEntity
		, R> {
	public ZelderBossEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new ZelderBossEntityModel());
	}
}