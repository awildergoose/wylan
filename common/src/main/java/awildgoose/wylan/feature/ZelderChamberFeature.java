package awildgoose.wylan.feature;

import awildgoose.wylan.WylanMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZelderChamberFeature extends Feature<NoneFeatureConfiguration> {
	public ZelderChamberFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public static void placeStructure(WorldGenLevel level, BlockPos origin, CompoundTag nbt) {
		ListTag palette = nbt.getList("palette").orElseThrow();
		ListTag blocks = nbt.getList("blocks").orElseThrow();

		HolderGetter<Block> blockLookup =
				level.registryAccess()
						.lookupOrThrow(Registries.BLOCK);

		List<BlockState> states = new ArrayList<>();

		for (int i = 0; i < palette.size(); i++) {
			CompoundTag tag = palette.getCompound(i).orElseThrow();
			states.add(NbtUtils.readBlockState(blockLookup, tag));
		}

		for (int i = 0; i < blocks.size(); i++) {
			CompoundTag b = blocks.getCompound(i).orElseThrow();
			ListTag pos = b.getList("pos").orElseThrow();

			BlockPos p = origin.offset(
					pos.getInt(0).orElseThrow(),
					pos.getInt(1).orElseThrow(),
					pos.getInt(2).orElseThrow()
			);

			BlockState state = states.get(b.getInt("state").orElseThrow());

			level.setBlock(p, state, Block.UPDATE_ALL);
		}
	}


	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
				WylanMod.MOD_ID,
				"structure/zelder_chamber.nbt"
		);

		CompoundTag nbt;

		try (InputStream in =
					 ctx.level()
							 .getLevel()
							 .getServer()
							 .getResourceManager()
							 .open(id)) {

			nbt = NbtIo.readCompressed(in, NbtAccounter.unlimitedHeap());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		placeStructure(ctx.level(), ctx.origin(), nbt);

		return true;
	}
}
