package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.entity.SkinwalkerEntity;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
			WylanMod.MOD_ID,
			Registries.ENTITY_TYPE);

	public static final RegistrySupplier<EntityType<HenryEntity>> HENRY = register("henry", () -> EntityType.Builder.of(HenryEntity::new, MobCategory.MONSTER).sized(0.75f, 1.75f));
	public static final RegistrySupplier<EntityType<SkinwalkerEntity>> SKINWALKER = register("skinwalker",
																							 () -> EntityType.Builder.of(SkinwalkerEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F)
																									 .eyeHeight(1.62F).updateInterval(2).clientTrackingRange(32));

	public static <T extends Entity> RegistrySupplier<EntityType<T>> register(String name,
																			  Supplier<EntityType.Builder<T>> builder) {
		return ENTITY_TYPES.register(name, () -> builder.get().build(ResourceKey.create(
				Registries.ENTITY_TYPE,
				ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, name)
		)));
	}

	public static void init() {
		ENTITY_TYPES.register();

		SpawnPlacementsRegistry.register(HENRY, SpawnPlacementTypes.ON_GROUND,
										 Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HenryEntity::canSpawnHere);
		BiomeModifications.addProperties((p) -> p.hasTag(BiomeTags.IS_FOREST), (biomeContext, mutable) -> mutable.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HENRY.get(),
																																																	  0, 1), 10));
		SpawnPlacementsRegistry.register(SKINWALKER, SpawnPlacementTypes.ON_GROUND,
										 Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkinwalkerEntity::canSpawnHere);
		BiomeModifications.addProperties((p) -> p.hasTag(BiomeTags.IS_FOREST),
										 (biomeContext, mutable) -> mutable.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(SKINWALKER.get(),
																																																	  0, 1), 50));

	}
}
