package awildgoose.wylan.fabric.mixin.client.sodium;

import awildgoose.wylan.fabric.sodium.SodiumCompat;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Arrays;

@Mixin(DefaultTerrainRenderPasses.class)
public class SodiumDefaultTerrainRenderPassesMixin {
	@Mutable
	@Accessor(value = "ALL", remap = false)
	private static void setAll(TerrainRenderPass[] all) {
		throw new AssertionError();
	}

	@Inject(
			method = "<clinit>",
			at = @At("TAIL"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void onStaticInit(CallbackInfo ci) {
		TerrainRenderPass[] current = DefaultTerrainRenderPasses.ALL;
		TerrainRenderPass[] expanded = Arrays.copyOf(current, current.length + 1);
		expanded[expanded.length - 1] = (TerrainRenderPass) SodiumCompat.LAVA_PASS;
		setAll(expanded);
	}
}
