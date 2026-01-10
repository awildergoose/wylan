package awildgoose.wylan.neoforge;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.entity.*;
import awildgoose.wylan.init.ModEntityTypes;
import awildgoose.wylan.neoforge.sodium.SodiumCompat;
import dev.architectury.platform.Platform;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@SuppressWarnings("unused")
@Mod(WylanMod.MOD_ID)
@EventBusSubscriber(modid = WylanMod.MOD_ID)
public final class WylanModNeoForge {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(
            NeoForgeRegistries.ENTITY_DATA_SERIALIZERS,
            WylanMod.MOD_ID
    );
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<SkinwalkerTexture>> SKINWALKER_TEXTURE = ENTITY_DATA_SERIALIZERS.register(
            "skinwalker_texture",
            SkinwalkerEntity.TEXTURE::serializer
    );

    public WylanModNeoForge(IEventBus bus) {
        WylanMod.init();
        ENTITY_DATA_SERIALIZERS.register(bus);

        if (Platform.isModLoaded("sodium")) {
            //noinspection InstantiationOfUtilityClass
            new SodiumCompat();
        }
    }

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntityTypes.HENRY.get(),
                HenryEntity.createDefaultAttributes().build()
        );
        event.put(
                ModEntityTypes.SKINWALKER.get(),
                SkinwalkerEntity.createDefaultAttributes().build()
        );
        event.put(
                ModEntityTypes.ZELDER_BOSS.get(),
                ZelderBossEntity.createDefaultAttributes().build()
        );
        event.put(
                ModEntityTypes.GUMBALL_PELLET.get(),
                GumballPelletEntity.createDefaultAttributes().build()
        );
    }
}
