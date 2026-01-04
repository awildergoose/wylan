package awildgoose.wylan.mixin;

import awildgoose.wylan.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Structure.class)
public class StructureMixin {
	@Unique
	private static BlockPos chunkToBlockPos(ChunkPos pos) {
		return new BlockPos(
				pos.x << 4,
				0,
				pos.z << 4
		);
	}

	@Unique
	private static ChunkPos blockToChunkPos(BlockPos pos) {
		return new ChunkPos(pos);
	}

	@Unique
	private static @Nullable BlockPos getPivot(PiecesContainer piecesContainer) {
		StructurePiece surface = null;

		for (var piece : piecesContainer.pieces()) {
			if (surface == null || piece.getBoundingBox().maxY() > surface.getBoundingBox().maxY()) {
				surface = piece;
			}
		}

		if (surface != null) {
			BoundingBox box = surface.getBoundingBox();

			return new BlockPos(
					(box.minX() + box.maxX()) / 2,
					box.minY() + 1,
					(box.minZ() + box.maxZ()) / 2
			);
		}

		return null;
	}

	@Unique
	private static void safeSetBlock(WorldGenLevel level, Block block, BlockPos pos, BoundingBox bb, ChunkPos chunkPos) {
		if (bb.isInside(pos) && blockToChunkPos(pos).equals(chunkPos)) {
			level.setBlock(pos, block.defaultBlockState(), 2);
		}
	}

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
		//noinspection DataFlowIssue
		Structure self = (Structure) (Object) this;
		BlockPos blockPos = chunkToBlockPos(chunkPos);

		if (worldGenLevel.getBiome(blockPos).is(BiomeTags.IS_MOUNTAIN)) {
			BlockPos pivot = getPivot(piecesContainer);

			if (pivot != null)
				// TODO LETTUCE_PLUSHIE
				safeSetBlock(worldGenLevel, ModBlocks.ZELDER_PLUSHIE.get(), pivot, boundingBox, chunkPos);
		}

		if (self instanceof JigsawStructure jigsaw) {
			if (jigsaw.getStartPool().getRegisteredName().startsWith("minecraft:trail_ruins")) {
				BlockPos pivot = getPivot(piecesContainer);

				if (pivot != null)
					safeSetBlock(worldGenLevel, ModBlocks.KAT_PLUSHIE.get(), pivot, boundingBox, chunkPos);
			}
		}

		if (self instanceof IglooStructure) {
			BlockPos pivot = getPivot(piecesContainer);

			if (pivot != null)
				safeSetBlock(worldGenLevel, ModBlocks.ANIMATED_PLUSHIE.get(), pivot, boundingBox, chunkPos);
		}
	}
}
