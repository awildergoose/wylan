package awildgoose.wylan;

import awildgoose.wylan.init.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class WylanMod {
    public static final String MOD_ID = "wylan";
    public static final ResourceKey<Level> ZELDER_ARENA =  ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MOD_ID, "zelder_arena"));

    public static void init() {
        ModCreativeTabs.init();
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModEntityTypes.init();
        ModSounds.init();
        ModFeatures.init();
        // TODO(NeoForge): register packets
    }
}
