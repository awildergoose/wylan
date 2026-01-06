package awildgoose.wylan.mixin.client;

import awildgoose.wylan.init.ModSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public class BossMusicMixin {
	@Inject(at = @At("HEAD"), method = "getSituationalMusic", cancellable = true)
	public void getSituationalMusic(CallbackInfoReturnable<MusicInfo> cir) {
		Minecraft self = Minecraft.getInstance();
		Music music = Optionull.map(self.screen, Screen::getBackgroundMusic);

		// if: no music & player is present & dimension is overworld &
		// the boss overlay says we should play music
		if (music == null && self.player != null && self.player.level()
				.dimension() == Level.OVERWORLD &&
				self.gui.getBossOverlay()
						.shouldPlayMusic())
			cir.setReturnValue(new MusicInfo(ModSounds.ZELDER_BOSS_MUSIC));
	}
}
