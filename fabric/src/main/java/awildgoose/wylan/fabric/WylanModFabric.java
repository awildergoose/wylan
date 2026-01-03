package awildgoose.wylan.fabric;

import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.init.ModEntityTypes;
import net.fabricmc.api.ModInitializer;

import awildgoose.wylan.WylanMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public final class WylanModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WylanMod.init();
        FabricDefaultAttributeRegistry.register(ModEntityTypes.HENRY.get(), HenryEntity.createCubeAttributes());
    }
}
