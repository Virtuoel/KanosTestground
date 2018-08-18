package virtuoel.kanostestground.init;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import virtuoel.kanostestground.KanosTestground;

@EventBusSubscriber(modid = KanosTestground.MOD_ID)
@ObjectHolder(KanosTestground.MOD_ID)
public class BlockRegistrar
{
	public static final Block GENERIC_ORE = Blocks.AIR; // Variable name matches registry name because of class-level ObjectHolder. See: http://cazzar.net/tutorials/fml/objectholder-a-simple-run-through/
	public static final Block GENERIC_BLOCK = Blocks.AIR;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(
				setRegistryNameAndTranslationKey(setHarvestLevel(
						new Block(Material.ROCK, MapColor.STONE)
						.setHardness(3.0F)
						.setResistance(5.0F)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"pickaxe", 2),
						"generic_ore"),
				setRegistryNameAndTranslationKey(setHarvestLevel(
						new Block(Material.IRON, MapColor.PURPLE)
						.setHardness(5.0F)
						.setResistance(10.0F)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"pickaxe", 2),
						"generic_block")
		);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
		final Function<Block, Item> makeItemBlock = block -> new ItemBlock(block).setRegistryName(block.getRegistryName());
		
		Stream.of(
			GENERIC_ORE,
			GENERIC_BLOCK
		).filter(b -> b != null && b != Blocks.AIR)
		.map(makeItemBlock)
		.forEach(event.getRegistry()::register);
	}
	
	@EventBusSubscriber(modid = KanosTestground.MOD_ID, value = Side.CLIENT)
	public static class Client
	{
		@SubscribeEvent
		public static void registerItemBlockModels(ModelRegistryEvent event)
		{
			final Consumer<Item> setItemModel = item ->
			{
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			};
			
			Stream.of(
				GENERIC_ORE,
				GENERIC_BLOCK
			).filter(b -> b != null && b != Blocks.AIR)
			.map(Item::getItemFromBlock)
			.filter(i -> i != null && i != Items.AIR)
			.forEach(setItemModel);
		}
	}
	
	// Static utility methods
	
	public static <T extends Block> T setRegistryNameAndTranslationKey(T entry, String name, String key)
	{
		entry.setRegistryName(name).setTranslationKey(entry.getRegistryName().getNamespace() + "." + key);
		return entry;
	}
	
	public static <T extends Block> T setRegistryNameAndTranslationKey(T entry, String name)
	{
		return setRegistryNameAndTranslationKey(entry, name, name);
	}
	
	public static <T extends Block> T setHarvestLevel(T block, String toolClass, int level)
	{
		block.setHarvestLevel(toolClass, level);
		return block;
	}
}
