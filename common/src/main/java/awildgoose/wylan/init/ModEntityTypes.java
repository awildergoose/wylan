package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.HenryEntity;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
			WylanMod.MOD_ID,
			Registries.ENTITY_TYPE);

	// TODO register function
	public static final RegistrySupplier<EntityType<HenryEntity>> HENRY = ENTITY_TYPES.register("henry",
																									   () ->
																										   EntityType.Builder.of(HenryEntity::new, MobCategory.MONSTER).sized(0.75f, 1.75f).build(
																												   ResourceKey.create(
																														   Registries.ENTITY_TYPE,
																														   ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, "henry")
																												   ))
																									   );


	public static void init() {
		ENTITY_TYPES.register();

		SpawnPlacementsRegistry.register(HENRY, SpawnPlacementTypes.ON_GROUND,
										 Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HenryEntity::canSpawnHere);
		BiomeModifications.addProperties((p) -> p.hasTag(BiomeTags.IS_FOREST), (biomeContext, mutable) -> mutable.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HENRY.get(),
																																																  0, 1), 30));
	}
}
