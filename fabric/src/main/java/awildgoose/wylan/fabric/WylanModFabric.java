package awildgoose.wylan.fabric;

import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.entity.SkinwalkerEntity;
import awildgoose.wylan.init.ModEntityTypes;
import net.fabricmc.api.ModInitializer;

import awildgoose.wylan.WylanMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.resources.ResourceLocation;

public final class WylanModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WylanMod.init();
        FabricDefaultAttributeRegistry.register(ModEntityTypes.HENRY.get(), HenryEntity.createCubeAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.SKINWALKER.get(),
                                                SkinwalkerEntity.createCubeAttributes());
        FabricTrackedDataRegistry.register(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
                                                                                 "skinwalker_texture"), SkinwalkerEntity.TEXTURE.serializer());
    }
}
