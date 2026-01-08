package awildgoose.wylan.client.entity;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.client.init.ModEntityModelLayers;
import awildgoose.wylan.entity.GumballPelletEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GumballPelletEntityRenderer extends LivingEntityRenderer<GumballPelletEntity, GumballPelletEntityRenderState, GumballPelletEntityModel> {
	public GumballPelletEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new GumballPelletEntityModel(context.bakeLayer(ModEntityModelLayers.GUMBALL_PELLET)), 0.35f);
	}

	@Override
	public @NotNull GumballPelletEntityRenderState createRenderState() {
		return new GumballPelletEntityRenderState();
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(GumballPelletEntityRenderState livingEntityRenderState) {
		return ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "textures/entity/gumball_pellet.png");
	}
}
