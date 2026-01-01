package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(WylanMod.MOD_ID,
																						 Registries.CREATIVE_MODE_TAB);
	public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = TABS.register("wylan", () ->
			CreativeTabRegistry.create(
					Component.translatable("itemGroup." + WylanMod.MOD_ID + ".wylan"),
					() -> new ItemStack(ModItems.WYLAN_COOKIE.get())));


	public static void init() {
		TABS.register();
	}
}
