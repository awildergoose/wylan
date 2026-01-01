package awildgoose.wylan;

import awildgoose.wylan.init.ModBlocks;
import awildgoose.wylan.init.ModCreativeTabs;
import awildgoose.wylan.init.ModItems;
import awildgoose.wylan.init.ModSounds;

public final class WylanMod {
    public static final String MOD_ID = "wylan";

    public static void init() {
        ModCreativeTabs.init();
        ModBlocks.init();
        ModItems.init();
        ModSounds.init();
    }
}
