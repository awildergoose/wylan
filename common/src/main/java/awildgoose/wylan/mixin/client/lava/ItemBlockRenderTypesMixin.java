package awildgoose.wylan.mixin.client.lava;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = ItemBlockRenderTypes.class, priority = 500)
public class ItemBlockRenderTypesMixin {
	@Shadow @Final private static Map<Fluid, ChunkSectionLayer> LAYER_BY_FLUID;

	/**
	 * @author awildergoose
	 * @reason use our custom layer for lava
	 */
	@Overwrite
	public static ChunkSectionLayer getRenderLayer(FluidState fluidState) {
		return ChunkSectionLayer.valueOf("LAVA");
//		ChunkSectionLayer chunkSectionLayer = LAYER_BY_FLUID.get(fluidState.getType());
//		if (fluidState.is(FluidTags.LAVA))
//			return ChunkSectionLayer.valueOf("LAVA");
//		return chunkSectionLayer != null ? chunkSectionLayer : ChunkSectionLayer.SOLID;
	}
}
