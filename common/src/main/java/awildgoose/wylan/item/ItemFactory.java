package awildgoose.wylan.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface ItemFactory<T extends Item> {
	T create(Block block, Item.Properties properties);
}
