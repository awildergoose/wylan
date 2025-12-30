package awildgoose.wylan;

import awildgoose.wylan.block.PlushieBlock;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class WylanMod {
    public static final String MOD_ID = "wylan";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = TABS.register("wylan", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + MOD_ID + ".wylan"),
                                       () -> new ItemStack(WylanMod.WYLAN_COOKIE.get())));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> WYLAN_COOKIE = registerItem("wylan_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));
    public static final RegistrySupplier<Item> ANIMATED_COOKIE = registerItem("animated_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));
    public static final RegistrySupplier<Item> KAT_COOKIE = registerItem("kat_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));
    public static final RegistrySupplier<Item> LORDUCKIE_COOKIE = registerItem("lorduckie_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));
    public static final RegistrySupplier<Item> SM_COOKIE = registerItem("sm_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));
    public static final RegistrySupplier<Item> ZELDER_COOKIE = registerItem("zelder_cookie", Item::new,
                                                                           new Item.Properties().food(alwaysEdibleCookie()));

    private static RegistrySupplier<Item> registerItem(String path, Function<Item.Properties, Item> factory,
                                                       Item.Properties properties) {
        //noinspection UnstableApiUsage
        return ITEMS.register(path, () -> factory.apply(properties.setId(ResourceKey.create(Registries.ITEM,
                                                                                   ResourceLocation.fromNamespaceAndPath(MOD_ID, path))).arch$tab(CREATIVE_TAB)));
    }

    private static FoodProperties alwaysEdibleCookie() {
        return (new FoodProperties.Builder()).alwaysEdible().nutrition(2).saturationModifier(0.1F).build();
    }

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(WylanMod.MOD_ID, Registries.BLOCK);


    public static final RegistrySupplier<Block> WYLAN_PLUSHIE =
            registerBlockWithItem("wylan_plushie", PlushieBlock::new, BlockBehaviour.Properties.of()
                                                                           .strength(3.0f)
                                                                           .sound(SoundType.STONE),
                                  (p) -> p.component(DataComponents.EQUIPPABLE,
                                                     Equippable.builder(EquipmentSlot.HEAD).build()));

    private static RegistrySupplier<Block> registerBlockWithItem(@SuppressWarnings("SameParameterValue") String path,
                                                                 Function<BlockBehaviour.Properties, Block> factory,
                                                                 BlockBehaviour.Properties properties,
                                                                 Function<Item.Properties, Item.Properties> itemFactory) {
        RegistrySupplier<Block> block = BLOCKS.register(path,
                                                        () -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK,
                                                                                                                        ResourceLocation.fromNamespaceAndPath(MOD_ID, path)))));

        WylanMod.ITEMS.register(path, () -> new BlockItem(
                block.get(),
                itemFactory.apply(new Item.Properties()
                        .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, path)))
                        .arch$tab(WylanMod.CREATIVE_TAB)
                )
        ));

        return block;
    }


    public static void init() {
        TABS.register();
        BLOCKS.register();
        ITEMS.register();
    }
}
