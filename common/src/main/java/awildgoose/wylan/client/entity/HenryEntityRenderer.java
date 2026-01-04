package awildgoose.wylan.client.entity;

import awildgoose.wylan.entity.HenryEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

@Environment(EnvType.CLIENT)
public class HenryEntityRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<HenryEntity, R> {
	public HenryEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new HenryEntityModel());
	}
}