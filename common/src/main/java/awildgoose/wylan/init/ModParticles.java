package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.mixin.SimpleParticleTypeAccessor;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ModParticles {
	public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(WylanMod.MOD_ID,
																						Registries.PARTICLE_TYPE);
	public static final RegistrySupplier<SimpleParticleType> BLOOD =
			PARTICLES.register("blood", () -> SimpleParticleTypeAccessor.invokeInit(false));

	public static void init() {
		PARTICLES.register();
	}
}
