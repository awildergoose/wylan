package awildgoose.wylan.client.block.entity;

import awildgoose.wylan.block.PlushieBlock;
import awildgoose.wylan.block.entity.PlushieBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class PlushieBlockEntityRenderer implements BlockEntityRenderer<PlushieBlockEntity> {
	public PlushieBlockEntityRenderer(BlockEntityRendererProvider.Context ignoredContext) {
	}

	@Override
	public void render(PlushieBlockEntity be, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i
			, int j, Vec3 vec3) {
		ItemStack stack = be.stack;
		if (stack instanceof ItemStack && stack.isEmpty()) return;

		poseStack.pushPose();

		var facing = be.getBlockState().getValue(PlushieBlock.FACING);
		var inverse = facing.getOpposite().getRotation();
		if (facing == Direction.SOUTH || facing == Direction.NORTH) {
			inverse.rotateLocalX((float) Math.toRadians(90.0 * (facing == Direction.NORTH ? -1 : 1)));
		} else if (facing == Direction.UP || facing == Direction.DOWN) {
			if (facing == Direction.UP) {
				inverse.rotateLocalX((float) Math.toRadians(90.0));
				inverse.rotateLocalY((float) Math.toRadians(180.0));
				inverse.rotateLocalZ((float) Math.toRadians(180.0));
				poseStack.translate(0.0, -0.4, 0.0);
			} else {
				inverse.rotateLocalX((float) Math.toRadians(-90.0));
				poseStack.translate(0.0, -1.0, 0.0);
			}
		} else {
			inverse.rotateLocalZ((float) Math.toRadians(90.0 * (facing == Direction.EAST ? -1 : 1)));
		}

		poseStack.translate(
				0.5,
				1.2,
				0.5
		);

		poseStack.mulPose(inverse);

		Minecraft.getInstance().getItemRenderer().renderStatic(
				stack,
				ItemDisplayContext.FIXED,
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
