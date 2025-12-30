package awildgoose.wylan;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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

    public static void init() {
        TABS.register();
        ITEMS.register();
    }
}
