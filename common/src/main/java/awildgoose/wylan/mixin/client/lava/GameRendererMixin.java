package awildgoose.wylan.mixin.client.lava;

import awildgoose.wylan.client.init.ModRendering;
import awildgoose.wylan.client.render.GooseUniform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Unique private final GooseUniform wylan$gooseUniform = new GooseUniform();

	@Inject(at = @At("HEAD"), method = "close")
	public void close(CallbackInfo ci) {
		this.wylan$gooseUniform.close();
	}

	@Inject(at = @At("HEAD"), method = "render")
	public void render(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
		if (!Minecraft.getInstance().noRender) {
			wylan$gooseUniform.update();
		}

		ModRendering.preRender(deltaTracker);
	}
}
