package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.block.entity.PlushieBlockEntity;
import awildgoose.wylan.mixin.BlockEntityTypeConstructorMixin;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;
import java.util.function.Supplier;

public class ModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
			WylanMod.MOD_ID,
			Registries.BLOCK_ENTITY_TYPE);
	public static final RegistrySupplier<BlockEntityType<PlushieBlockEntity>> PLUSHIE_BLOCK_ENTITY = register(
			"plushie", PlushieBlockEntity::new, () -> Set.of(
					ModBlocks.WYLAN_PLUSHIE.get(),
				   	ModBlocks.ANIMATED_PLUSHIE.get(),
				   	ModBlocks.KAT_PLUSHIE.get(),
					ModBlocks.ZELDER_PLUSHIE.get()
			));

	@SuppressWarnings("SameParameterValue")
	private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name,
																						 BlockEntityType.BlockEntitySupplier<? extends T> supplier, Supplier<Set<Block>> blocks) {
		return BLOCK_ENTITIES.register(name,
									   () -> BlockEntityTypeConstructorMixin.invokeInit(
											   supplier,
											   blocks.get()
									   )
		);
	}

	public static void init() {
		BLOCK_ENTITIES.register();
	}
}
