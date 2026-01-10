package awildgoose.wylan.fabric;

import awildgoose.wylan.entity.GumballPelletEntity;
import awildgoose.wylan.entity.HenryEntity;
import awildgoose.wylan.entity.SkinwalkerEntity;
import awildgoose.wylan.entity.ZelderBossEntity;
import awildgoose.wylan.init.ModEntityTypes;
import awildgoose.wylan.payloads.ScreenshakeS2CPayload;
import net.fabricmc.api.ModInitializer;

import awildgoose.wylan.WylanMod;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.resources.ResourceLocation;

public final class WylanModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        WylanMod.init();

        FabricDefaultAttributeRegistry.register(ModEntityTypes.HENRY.get(), HenryEntity.createDefaultAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.SKINWALKER.get(), SkinwalkerEntity.createDefaultAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.ZELDER_BOSS.get(), ZelderBossEntity.createDefaultAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.GUMBALL_PELLET.get(), GumballPelletEntity.createDefaultAttributes());

        FabricTrackedDataRegistry.register(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
                                                                                 "skinwalker_texture"), SkinwalkerEntity.TEXTURE.serializer());
        PayloadTypeRegistry.playS2C().register(ScreenshakeS2CPayload.ID, ScreenshakeS2CPayload.CODEC);
    }
}
