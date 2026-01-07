package awildgoose.wylan.mixin.client;

import awildgoose.wylan.client.init.ModRendering;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChunkSectionLayer.class)
public class ChunkSectionLayerMixin {
	@Mutable
	@Shadow
	@Final
	private static ChunkSectionLayer[] $VALUES;

	@SuppressWarnings("SameParameterValue")
	@Invoker(value="<init>")
	private static ChunkSectionLayer create(String name, int ordinal, RenderPipeline renderPipeline, int j, boolean bl,
											boolean bl2) {
		throw new IllegalStateException("Unreachable");
	}

	static {
		var entry = create("LAVA", $VALUES.length, ModRendering.LAVA_PIPELINE, 786432, true, true);

		$VALUES = ArrayUtils.add($VALUES, entry);
	}
}