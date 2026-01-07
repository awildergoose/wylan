package awildgoose.wylan.mixin.client.lava;

import awildgoose.wylan.client.init.ModRendering;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.SequencedMap;

@Environment(EnvType.CLIENT)
@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {
	@Shadow @Final private SectionBufferBuilderPack fixedBufferPack;

	@Inject(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;",
					shift = At.Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void injectLavaLayerBeforeBufferSource(int i, CallbackInfo ci, SequencedMap<RenderType, ByteBufferBuilder> sequencedMap) {
		ChunkSectionLayer layer = ChunkSectionLayer.valueOf("LAVA");
		sequencedMap.put(ModRendering.LAVA, this.fixedBufferPack.buffer(layer));
	}
}
