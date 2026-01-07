package awildgoose.wylan.mixin.client.lava;

import awildgoose.wylan.client.init.ModRendering;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
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

@Environment(EnvType.CLIENT)
@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {
	@Shadow @Final private SectionBufferBuilderPack fixedBufferPack;

    @Inject(method = "<init>", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;",
			shift = At.Shift.BEFORE
    ))
    private void injectLavaLayerBeforeBufferSource(int i, CallbackInfo ci) {
		Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map = new Object2ObjectLinkedOpenHashMap<>();
		ChunkSectionLayer lava = ChunkSectionLayer.valueOf("LAVA");
		map.put(ModRendering.LAVA, this.fixedBufferPack.buffer(lava));
	}
}
