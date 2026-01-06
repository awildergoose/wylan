package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
			WylanMod.MOD_ID,
			Registries.SOUND_EVENT);

	public static final RegistrySupplier<SoundEvent> PLUSHIE_INTERACT = registerSound("plushie_interact");
	public static final RegistrySupplier<SoundEvent> RADIO_STATIC = registerSound("radio_static");
	public static final RegistrySupplier<SoundEvent> WYLAN_GROWL = registerSound("wylan_growl");

	private static RegistrySupplier<SoundEvent> registerSound(String name) {
		return SOUNDS.register(name,
							   () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, name)));
	}

	public static void init() {
		SOUNDS.register();
	}
}
