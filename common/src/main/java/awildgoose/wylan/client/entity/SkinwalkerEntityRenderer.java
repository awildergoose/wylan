package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.SkinwalkerEntity;
import awildgoose.wylan.init.ModEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SkinwalkerEntityRenderer extends LivingEntityRenderer<SkinwalkerEntity, SkinwalkerEntityRenderState,
		SkinwalkerEntityModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																						  "textures/entity/skinwalker" +
																								  "/zelder" +
																								  ".png");

	public SkinwalkerEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new SkinwalkerEntityModel(context.bakeLayer(ModEntityModelLayers.SKINWALKER)), 0.375f);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(SkinwalkerEntityRenderState livingEntityRenderState) {
		return TEXTURE;
	}

	@Override
	public @NotNull SkinwalkerEntityRenderState createRenderState() {
		return new SkinwalkerEntityRenderState();
	}

	@Override
	public void extractRenderState(SkinwalkerEntity livingEntity, SkinwalkerEntityRenderState livingEntityRenderState, float f) {
		super.extractRenderState(livingEntity, livingEntityRenderState, f);
		livingEntityRenderState.isCrouching = livingEntity.tickCount % 20 >= 10;
		livingEntityRenderState.nameTag = null;
	}
}
