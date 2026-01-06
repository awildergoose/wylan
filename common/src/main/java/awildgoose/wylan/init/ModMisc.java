package awildgoose.wylan.init;

import awildgoose.wylan.misc.FixedStructurePlacement;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import static awildgoose.wylan.WylanMod.MOD_ID;

public class ModMisc {

	public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENTS =
			DeferredRegister.create(MOD_ID, Registries.STRUCTURE_PLACEMENT);

	public static final RegistrySupplier<StructurePlacementType<FixedStructurePlacement>>
			FIXED_STRUCTURE_PLACEMENT =
			STRUCTURE_PLACEMENTS.register("fixed_placement",
										  () -> () -> FixedStructurePlacement.CODEC);

	public static void init() {
		STRUCTURE_PLACEMENTS.register();
	}
}
