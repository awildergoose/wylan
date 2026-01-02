package awildgoose.wylan.block;

import awildgoose.wylan.block.entity.PlushieBlockEntity;
import awildgoose.wylan.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlushieBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

	public PlushieBlock(Properties properties) {
		super(properties.noOcclusion().lightLevel(s -> 0));
		((FireBlock) Blocks.FIRE).setFlammable(this, 30, 60);
	}

	@Override
	protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!level.isClientSide) {
			level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.PLUSHIE_INTERACT.get()
					, SoundSource.BLOCKS);
		}
	}

	@Override
	protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
		BlockState above = level.getBlockState(blockPos.above());

		// destruct upon wrath of an anvil or lava
		if (above.getBlock() instanceof AnvilBlock || (above.getBlock() instanceof LiquidBlock liquid && liquid.arch$getFluid().isSame(
				Fluids.LAVA))) {
			level.destroyBlock(blockPos, true);
		}
	}

	@Override
	protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		if (!level.isClientSide) {
			if (player.isShiftKeyDown()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.PLUSHIE_INTERACT.get()
						, SoundSource.PLAYERS);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	protected @NotNull InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (!level.isClientSide) {
			if (!player.isShiftKeyDown()) {
				if (level.getBlockEntity(blockPos) instanceof PlushieBlockEntity be) {
					be.setStack(level, player.getItemInHand(interactionHand));
				}

				return InteractionResult.SUCCESS;
			}
		}

		return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
	}

// Block state

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite());
	}

	@Override
	protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
	}

	@Override
	protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
	}

	// Culling

	@Override
	protected @NotNull VoxelShape getOcclusionShape(BlockState blockState) {
		return Shapes.empty();
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState blockState) {
		return true;
	}

	@Override
	protected boolean useShapeForLightOcclusion(BlockState blockState) {
		return true;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PlushieBlockEntity(blockPos, blockState);
	}
}
