package awildgoose.wylan.mixin.client;

import awildgoose.wylan.item.PlushieItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
	@Unique private static long squishStartMS = -1L;

	@Unique private static final int SQUISH_DURATION_MS = 100;
	@Unique private static final float AMPLITUDE = 0.30f;

	@Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
	private void wylan$renderArmWithItemPre(
			AbstractClientPlayer player,
			float tickDelta,
			float pitch,
			InteractionHand hand,
			float swingProgress,
			ItemStack stack,
			float equipProgress,
			PoseStack matrices,
			MultiBufferSource buffer,
			int light,
			CallbackInfo ci
	) {
		if (stack.getItem() instanceof PlushieItem) {
			if (PlushieItem.wasUsingLastFrame) {
				squishStartMS = System.currentTimeMillis();
				PlushieItem.wasUsingLastFrame = false;
			}

			float scaleX = 1.0f;
			float scaleY = 1.0f;

			if (squishStartMS >= 0) {
				long elapsed = System.currentTimeMillis() - squishStartMS;

				if (elapsed < SQUISH_DURATION_MS) {
					float progress = 1.0f - ((float) elapsed / (float) SQUISH_DURATION_MS);
					scaleX = 1.0f - AMPLITUDE * progress;
					scaleY = 1.0f + AMPLITUDE * progress;
				} else {
					squishStartMS = -1L;
				}
			}

			matrices.pushPose();
			matrices.translate(0.0, 0.0, -0.2);
			matrices.scale(scaleX, scaleY, scaleX);
		}
	}

	@Inject(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
	private void wylan$renderArmWithItemPost(
			AbstractClientPlayer player,
			float tickDelta,
			float pitch,
			InteractionHand hand,
			float swingProgress,
			ItemStack stack,
			float equipProgress,
			PoseStack matrices,
			MultiBufferSource buffer,
			int light,
			CallbackInfo ci
	) {
		if (stack.getItem() instanceof PlushieItem) {
			matrices.popPose();
		}
	}
}
