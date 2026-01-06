package awildgoose.wylan.mixin.client;


import awildgoose.wylan.init.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ItemBlockRenderTypes.class)
public class TranslucentBlocksMixin {
	@Shadow @Final private static Map<Block, ChunkSectionLayer> TYPE_BY_BLOCK;

	@Inject(method = "<clinit>*", at = @At("RETURN"))
	private static void onInitialize(CallbackInfo ci) {
		TYPE_BY_BLOCK.put(ModBlocks.KAT_PLUSHIE.get(), ChunkSectionLayer.CUTOUT);
	}
}
