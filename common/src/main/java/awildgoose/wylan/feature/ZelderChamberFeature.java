package awildgoose.wylan.feature;

import awildgoose.wylan.WylanMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.io.IOException;
import java.io.InputStream;

public class ZelderChamberFeature extends Feature<NoneFeatureConfiguration> {
	public ZelderChamberFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "structure/zelder_chamber.nbt");

		CompoundTag nbt;
		try (InputStream in = ctx.level().getLevel().getServer().getResourceManager().open(id)) {
			nbt = NbtIo.readCompressed(in, NbtAccounter.unlimitedHeap());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		StructureTemplate template = new StructureTemplate();
		HolderGetter<Block> blockRegistry = ctx.level().registryAccess().lookupOrThrow(Registries.BLOCK);
		template.load(blockRegistry, nbt);

		StructurePlaceSettings settings = new StructurePlaceSettings()
				.setRotation(Rotation.NONE)
				.setMirror(Mirror.NONE)
				.setIgnoreEntities(false)
				.setFinalizeEntities(true)
				.setKnownShape(false);

		return template.placeInWorld(
				ctx.level(),
				ctx.origin(),
				ctx.origin(),
				settings,
				ctx.random(),
				2
		);
	}
}
