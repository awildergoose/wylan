package awildgoose.wylan;

import awildgoose.wylan.init.*;

public final class WylanMod {
    public static final String MOD_ID = "wylan";

    public static void init() {
        ModCreativeTabs.init();
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        ModSounds.init();
    }
}
