package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.SkinwalkerEntity;
import awildgoose.wylan.client.init.ModEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SkinwalkerEntityRenderer extends LivingEntityRenderer<SkinwalkerEntity, SkinwalkerEntityRenderState,
		SkinwalkerEntityModel> {
	private static ResourceLocation getSkinwalkerTexture(String texture) {
		return ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
												"textures/entity/skinwalker/%s.png".formatted(texture));
	}

	private static final ResourceLocation ANIMATED = getSkinwalkerTexture("animated");
	private static final ResourceLocation GUAC = getSkinwalkerTexture("guac");
	private static final ResourceLocation HUMMUS = getSkinwalkerTexture("hummus");
	private static final ResourceLocation KAT = getSkinwalkerTexture("kat");
	private static final ResourceLocation LETTUCE = getSkinwalkerTexture("lettuce");
	private static final ResourceLocation LORDUCKIE = getSkinwalkerTexture("lorduckie");
	private static final ResourceLocation SM = getSkinwalkerTexture("sm");
	private static final ResourceLocation WYLAN = getSkinwalkerTexture("wylan");
	private static final ResourceLocation ZELDER = getSkinwalkerTexture("zelder");
	private static final ResourceLocation ZELDER_OILED = getSkinwalkerTexture("zelder_oiled");

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
			case HUMMUS -> HUMMUS;
			case LETTUCE -> LETTUCE;
			case GUAC -> GUAC;
			case ZELDER_OILED -> ZELDER_OILED;
		};
	}

	@Override
	public @NotNull SkinwalkerEntityRenderState createRenderState() {
		return new SkinwalkerEntityRenderState();
	}

	@Override
	public void extractRenderState(SkinwalkerEntity entity, SkinwalkerEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.isCrouching = entity.tickCount % 20 >= 10;
		state.nameTag = null;
		state.texture = entity.getEntityData().get(SkinwalkerEntity.TEXTURE);
		state.attackTime = entity.getAttackAnim(partialTicks);
	}
}
