package awildgoose.wylan.mixin.client.lava;

import awildgoose.wylan.client.render.GooseUniform;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Unique private final GooseUniform gooseUniform = new GooseUniform();

	@Inject(at = @At("HEAD"), method = "close")
	public void close(CallbackInfo ci) {
		this.gooseUniform.close();
	}

	@Inject(at = @At("HEAD"), method = "render")
	public void render(DeltaTracker deltaTracker, boolean bl, CallbackInfo ci) {
		if (!Minecraft.getInstance().noRender) {
			gooseUniform.update();
		}
	}
}
