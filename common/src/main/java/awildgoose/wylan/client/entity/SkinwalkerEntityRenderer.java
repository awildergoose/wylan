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
	private static ResourceLocation getSkinwalkerTexture(String texture) {
		return ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
												"textures/entity/skinwalker/%s.png".formatted(texture));
	}

	private static final ResourceLocation ANIMATED = getSkinwalkerTexture("animated");
	private static final ResourceLocation KAT = getSkinwalkerTexture("kat");
	private static final ResourceLocation LORDUCKIE = getSkinwalkerTexture("lorduckie");
	private static final ResourceLocation SM = getSkinwalkerTexture("sm");
	private static final ResourceLocation WYLAN = getSkinwalkerTexture("wylan");
	private static final ResourceLocation ZELDER = getSkinwalkerTexture("zelder");

	public SkinwalkerEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new SkinwalkerEntityModel(context.bakeLayer(ModEntityModelLayers.SKINWALKER)), 0.375f);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(SkinwalkerEntityRenderState livingEntityRenderState) {
		return switch (livingEntityRenderState.texture) {
			case ANIMATED -> ANIMATED;
			case KAT -> KAT;
			case LORDUCKIE -> LORDUCKIE;
			case SM -> SM;
			case WYLAN -> WYLAN;
			case ZELDER -> ZELDER;
		};
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
		livingEntityRenderState.texture = livingEntity.getEntityData().get(SkinwalkerEntity.TEXTURE);
	}
}
