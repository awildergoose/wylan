package awildgoose.wylan.fabric;

import net.fabricmc.api.ModInitializer;

import awildgoose.wylan.ExampleMod;

public final class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExampleMod.init();
    }
}
