package awildgoose.wylan.neoforge;

import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.entity.SkinwalkerEntity;
import awildgoose.wylan.entity.SkinwalkerTexture;
import awildgoose.wylan.init.ModEntityTypes;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import awildgoose.wylan.WylanMod;
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
    }

    @SubscribeEvent
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntityTypes.HENRY.get(),
                HenryEntity.createCubeAttributes()
                        .build()
        );
        event.put(
                ModEntityTypes.SKINWALKER.get(),
                SkinwalkerEntity.createCubeAttributes().build()
        );
    }
}
