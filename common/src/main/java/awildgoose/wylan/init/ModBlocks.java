package awildgoose.wylan.init;

import awildgoose.wylan.WylanMod;
import awildgoose.wylan.block.PlushieBlock;
import awildgoose.wylan.item.ItemFactory;
import awildgoose.wylan.item.PlushieItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModBlocks {
	private static final BlockBehaviour.Properties PLUSHIE_PROPERTIES = BlockBehaviour.Properties.of()
			.strength(0.8f)
			.sound(SoundType.WOOL);

	private static Item.Properties applyPlushieProperties(Item.Properties p) {
		return p.component(
				DataComponents.EQUIPPABLE,
				Equippable.builder(EquipmentSlot.HEAD).build());
	}

	public static final DeferredRegister<Block> BLOCKS =
			DeferredRegister.create(WylanMod.MOD_ID, Registries.BLOCK);

	public static final RegistrySupplier<Block> WYLAN_PLUSHIE =
			registerBlockWithItem("wylan_plushie", PlushieBlock::new, PLUSHIE_PROPERTIES,
								  PlushieItem::new, ModBlocks::applyPlushieProperties);
	public static final RegistrySupplier<Block> ANIMATED_PLUSHIE =
			registerBlockWithItem("animated_plushie", PlushieBlock::new, PLUSHIE_PROPERTIES,
								  PlushieItem::new, ModBlocks::applyPlushieProperties);
	public static final RegistrySupplier<Block> KAT_PLUSHIE =
			registerBlockWithItem("kat_plushie", PlushieBlock::new, PLUSHIE_PROPERTIES,
								  PlushieItem::new, ModBlocks::applyPlushieProperties);

	@SuppressWarnings("SameParameterValue")
	private static <T extends Item> RegistrySupplier<Block> registerBlockWithItem(String path,
																 Function<BlockBehaviour.Properties, Block> factory,
																 BlockBehaviour.Properties properties,
																 ItemFactory<T> itemFactory,
																 Function<Item.Properties, Item.Properties> itemPropFactory) {
		RegistrySupplier<Block> block = BLOCKS.register(path,
														() -> factory.apply(properties.setId(ResourceKey.create(
																Registries.BLOCK,
																ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID,
																									  path)))));

		ModItems.ITEMS.register(path, () -> itemFactory.create(
				block.get(),
				itemPropFactory.apply(new Item.Properties()
										  .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(WylanMod.MOD_ID, path)))
										  .arch$tab(ModCreativeTabs.CREATIVE_TAB)
				)
		));

		return block;
	}

	public static void init() {
		BLOCKS.register();
	}
}
