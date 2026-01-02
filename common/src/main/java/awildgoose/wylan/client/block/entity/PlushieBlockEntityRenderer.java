package awildgoose.wylan.client.block.entity;

import awildgoose.wylan.block.entity.PlushieBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

// TODO client only this, how?
public class PlushieBlockEntityRenderer implements BlockEntityRenderer<PlushieBlockEntity> {
	public PlushieBlockEntityRenderer(BlockEntityRendererProvider.Context ignoredContext) {
	}

	@Override
	public void render(PlushieBlockEntity be, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i
			, int j, Vec3 vec3) {
		ItemStack stack = be.stack;
		if (stack == null || (stack instanceof ItemStack && stack.isEmpty())) return;

		poseStack.pushPose();

		poseStack.translate(
				0.5,
				1.75,
				0.5
		);

		poseStack.scale(0.5f, 0.5f, 0.5f);

		float time = (Objects.requireNonNull(be.getLevel())
				.getGameTime() + f) % 360;
		poseStack.mulPose(Axis.YP.rotationDegrees(time));

		Minecraft.getInstance().getItemRenderer().renderStatic(
				stack,
				ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
				i,
				j,
				poseStack,
				multiBufferSource,
				be.getLevel(),
				0
		);

		poseStack.popPose();
	}
}
