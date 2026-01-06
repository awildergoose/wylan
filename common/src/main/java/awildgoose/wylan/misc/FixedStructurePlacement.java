package awildgoose.wylan.misc;

import awildgoose.wylan.init.ModMisc;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FixedStructurePlacement extends StructurePlacement {
	public static final MapCodec<FixedStructurePlacement> CODEC =
			RecordCodecBuilder.mapCodec(instance ->
												placementCodec(instance)
														.and(
																instance.group(
																		Codec.INT.fieldOf("chunk_x")
																				.forGetter(FixedStructurePlacement::getChunkX),
																		Codec.INT.fieldOf("chunk_z")
																				.forGetter(FixedStructurePlacement::getChunkZ)
																)
														)
														.apply(instance, FixedStructurePlacement::new)
			);

	private final int chunkX;
	private final int chunkZ;

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public FixedStructurePlacement(
			Vec3i offset,
			FrequencyReductionMethod method,
			float frequency,
			int salt,
			@SuppressWarnings("deprecation") Optional<ExclusionZone> exclusion,
			int chunkX,
			int chunkZ
	) {
		super(offset, method, frequency, salt, exclusion);
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	@Override
	protected boolean isPlacementChunk(
			ChunkGeneratorStructureState state,
			int x,
			int z
	) {
		return x == chunkX && z == chunkZ;
	}

	@Override
	public @NotNull StructurePlacementType<?> type() {
		return ModMisc.FIXED_STRUCTURE_PLACEMENT.get();
	}
}
