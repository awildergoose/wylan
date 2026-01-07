package awildgoose.wylan.mixin.client.lava;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(ChunkSectionLayerGroup.class)
public class ChunkSectionLayerGroupMixin {
	@Mutable
	@Shadow
	@Final
	private static ChunkSectionLayerGroup[] $VALUES;

	@SuppressWarnings("SameParameterValue")
	@Invoker(value="<init>")
	private static ChunkSectionLayerGroup create(String name, int ordinal, final ChunkSectionLayer... chunkSectionLayers) {
		throw new IllegalStateException("Unreachable");
	}

	static {
		var entry = create("LAVA", $VALUES.length, ChunkSectionLayer.valueOf("LAVA"));

		$VALUES = ArrayUtils.add($VALUES, entry);
	}
}
