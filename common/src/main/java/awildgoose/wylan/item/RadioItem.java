package awildgoose.wylan.item;

import awildgoose.wylan.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RadioItem extends Item {
	private static final int STATIC_LENGTH = 20 * 5; // 5 seconds
	private static final int INTERACT_LENGTH = 20; // 1 second

	private long nextSoundTick = 0;

	public RadioItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
		player.startUsingItem(interactionHand);

		return InteractionResult.SUCCESS;
	}

	@Override
	public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemStack) {
		return ItemUseAnimation.BLOCK;
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int i) {
		if (!level.isClientSide) return;

		long now = level.getGameTime();
		if (now < nextSoundTick) return;

		if (level.getDayTime() >= 21000 && level.getDayTime() <= 22000) {
			level.playLocalSound(entity, ModSounds.PLUSHIE_INTERACT.get(),
								 SoundSource.VOICE, 1.0f, 1.0f);
			nextSoundTick = now + INTERACT_LENGTH;
		} else {
			level.playLocalSound(entity, ModSounds.RADIO_STATIC.get(),
								 SoundSource.VOICE, 1.0f, 1.0f);
			nextSoundTick = now + STATIC_LENGTH;
		}
	}
}
