package awildgoose.wylan.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(BlockEntityType.class)
public interface BlockEntityTypeConstructorAccessor {
	@Invoker("<init>")
	static <T extends BlockEntity> BlockEntityType<T> invokeInit(
			BlockEntityType.BlockEntitySupplier<? extends T> supplier,
			Set<Block> blocks
	) {
		throw new AssertionError();
	}
}
