package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.init.ModEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HenryEntityRenderer extends MobRenderer<HenryEntity, HenryEntityRenderState, HenryEntityModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "textures" +
			"/entity/henry.png");

	public HenryEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new HenryEntityModel(context.bakeLayer(ModEntityModelLayers.HENRY)), 0.375f);
	}

	@Override
	public @NotNull HenryEntityRenderState createRenderState() {
		return new HenryEntityRenderState();
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(HenryEntityRenderState state) {
		return TEXTURE;
	}
}