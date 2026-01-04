package awildgoose.wylan.block.entity;

import awildgoose.wylan.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushieBlockEntity extends BlockEntity {
	public @NotNull ItemStack stack = ItemStack.EMPTY;

	public PlushieBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.PLUSHIE_BLOCK_ENTITY.get(), pos, state);
	}

	public void setStack(Level level, @NotNull ItemStack stack) {
		this.stack = stack;
		level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 0);
	}

	public @NotNull ItemStack getStack() {
		return this.stack;
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		stack = valueInput.read("Item", ItemStack.CODEC)
				.orElse(ItemStack.EMPTY);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		valueOutput.storeNullable("Item", ItemStack.CODEC, stack);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
}
