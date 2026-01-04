package awildgoose.wylan.neoforge;

import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.init.ModEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import awildgoose.wylan.WylanMod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(WylanMod.MOD_ID)
@EventBusSubscriber(modid = WylanMod.MOD_ID)
public final class WylanModNeoForge {
    public WylanModNeoForge() {
        WylanMod.init();
    }

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntityTypes.HENRY.get(),
                HenryEntity.createCubeAttributes()
                        .build()
        );
    }
}
