package awildgoose.wylan.mixin;

import awildgoose.wylan.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Structure.class)
public class StructureMixin {
	@Inject(at = @At("HEAD"), method = "afterPlace")
	public void afterPlace(
			WorldGenLevel worldGenLevel,
			StructureManager structureManager,
			ChunkGenerator chunkGenerator,
			RandomSource randomSource,
			BoundingBox boundingBox,
			ChunkPos chunkPos,
			PiecesContainer piecesContainer,
			CallbackInfo ci
	) {
		//noinspection DataFlowIssue,ConstantValue
		if ((Structure) (Object) this instanceof IglooStructure) {
			StructurePiece surface = null;

			for (var piece : piecesContainer.pieces()) {
				if (surface == null || piece.getBoundingBox().maxY() > surface.getBoundingBox().maxY()) {
					surface = piece;
				}
			}

			if (surface != null) {
				BoundingBox box = surface.getBoundingBox();
				BlockPos pivot = new BlockPos(
						(box.minX() + box.maxX()) / 2,
						box.minY() + 1,
						(box.minZ() + box.maxZ()) / 2
				);

				var plushie = randomSource.nextBoolean() ? ModBlocks.ANIMATED_PLUSHIE.get().defaultBlockState() :
						ModBlocks.ANIMATED_PLUSHIE.get().defaultBlockState(); // TODO HUMMUS_PLUSHIE
				worldGenLevel.setBlock(pivot, plushie, 3);
			}
		}
	}
}
