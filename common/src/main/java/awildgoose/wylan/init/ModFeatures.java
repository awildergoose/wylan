package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.feature.ZelderChamberFeature;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.FixedPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static awildgoose.wylan.WylanMod.MOD_ID;

public class ModFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ZELDER_CHAMBER =
			ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																									"zelder_chamber"));

	public static final ResourceKey<PlacedFeature> ZELDER_CHAMBER_PLACED =
			ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																									"zelder_chamber"));


	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(MOD_ID, Registries.FEATURE);

	public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> ZELDER_CHAMBER_FEATURE = register("zelder_chamber",
																	   new ZelderChamberFeature(
																			   NoneFeatureConfiguration.CODEC
																	   ));

	@SuppressWarnings("SameParameterValue")
	private static <T extends FeatureConfiguration> RegistrySupplier<Feature<T>> register(String name,
																						  Feature<T> feature) {
		return FEATURES.register(name, () -> feature);
	}

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> arg) {
		FeatureUtils.register(arg, ZELDER_CHAMBER, ZELDER_CHAMBER_FEATURE.get());
	}

	public static void bootstrapPlacements(BootstrapContext<PlacedFeature> bootstrapContext) {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> holder = holderGetter.getOrThrow(ZELDER_CHAMBER);
		PlacementUtils.register(
				bootstrapContext, ZELDER_CHAMBER_PLACED, holder,
				FixedPlacement.of(new BlockPos(100, 50, 0)),
				BiomeFilter.biome());
	}

	public static void init() {
		FEATURES.register();
	}
}
