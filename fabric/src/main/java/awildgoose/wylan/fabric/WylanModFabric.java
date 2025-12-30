package awildgoose.wylan.fabric;

import net.fabricmc.api.ModInitializer;

import awildgoose.wylan.WylanMod;

public final class WylanModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WylanMod.init();
    }
}
