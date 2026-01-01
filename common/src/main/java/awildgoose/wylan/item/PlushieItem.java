package awildgoose.wylan.item;

import awildgoose.wylan.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlushieItem extends BlockItem {
	public PlushieItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
		if (!level.isClientSide) {
			if (player.isShiftKeyDown()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.PLUSHIE_INTERACT.get()
						, SoundSource.PLAYERS);
				return InteractionResult.SUCCESS;
			}
		}

		return super.use(level, player, interactionHand);
	}
}
