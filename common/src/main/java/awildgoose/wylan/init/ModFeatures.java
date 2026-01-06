package awildgoose.wylan.init;

import awildgoose.wylan.feature.ZelderChamberFeature;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Supplier;

import static awildgoose.wylan.WylanMod.MOD_ID;

public class ModFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(MOD_ID, Registries.FEATURE);

	public static final RegistrySupplier<ZelderChamberFeature> ZELDER_CHAMBER_FEATURE = register("zelder_chamber",
																								 () -> new ZelderChamberFeature(
																			   NoneFeatureConfiguration.CODEC
																	   ));

	@SuppressWarnings("SameParameterValue")
	private static <T extends Feature<C>, C extends FeatureConfiguration> RegistrySupplier<T> register(String name,
																						  Supplier<T> feature) {
		return FEATURES.register(name, feature);
	}

	public static void init() {
		FEATURES.register();
	}
}
