package awildgoose.wylan.mixin.client;

import awildgoose.wylan.client.init.ModRendering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(SectionCompiler.class)
public class SectionCompilerMixin {
	@Redirect(
			method = "compile",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderLiquid(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V"
			)
	)
	private void wylan$redirectRenderLiquid(
			BlockRenderDispatcher dispatcher,
			BlockPos pos,
			BlockAndTintGetter region,
			VertexConsumer originalBufferBuilder,
			BlockState blockState,
			FluidState fluidState
	) {
		if (!RenderSystem.isOnRenderThread()) {
			dispatcher.renderLiquid(pos, region, originalBufferBuilder, blockState, fluidState);
			return;
		}

		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.level == null) {
			dispatcher.renderLiquid(pos, region, originalBufferBuilder, blockState, fluidState);
			return;
		}

		if (fluidState.is(FluidTags.LAVA)) {
			RenderBuffers renderBuffers = mc.renderBuffers();
			VertexConsumer vc = renderBuffers.bufferSource()
					.getBuffer(ModRendering.LAVA);
			dispatcher.renderLiquid(pos, region, vc, blockState, fluidState);
			renderBuffers.bufferSource().endBatch(ModRendering.LAVA);
			return;
		}

		dispatcher.renderLiquid(pos, region, originalBufferBuilder, blockState, fluidState);
	}
}
