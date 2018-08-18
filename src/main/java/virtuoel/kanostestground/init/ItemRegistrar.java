package virtuoel.kanostestground.init;

import java.util.function.Consumer;
import java.util.stream.Stream;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
public class ItemRegistrar
{
	public static final Item GENERIC_INGOT = Items.AIR; // Variable name matches registry name because of class-level ObjectHolder. See: http://cazzar.net/tutorials/fml/objectholder-a-simple-run-through/
	public static final Item GENERIC_NUGGET = Items.AIR;
	public static final Item GENERIC_DUST = Items.AIR;
	public static final Item SMALL_GENERIC_DUST = Items.AIR;
	public static final Item TINY_GENERIC_DUST = Items.AIR;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				setRegistryNameAndTranslationKey(
						new Item()
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_ingot"),
				setRegistryNameAndTranslationKey(
						new Item()
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_nugget"),
				setRegistryNameAndTranslationKey(
						new Item()
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_dust"),
				setRegistryNameAndTranslationKey(
						new Item()
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"small_generic_dust"),
				setRegistryNameAndTranslationKey(
						new Item()
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"tiny_generic_dust")
		);
	}
	
	@EventBusSubscriber(modid = KanosTestground.MOD_ID, value = Side.CLIENT)
	public static class Client
	{
		@SubscribeEvent
		public static void registerItemModels(ModelRegistryEvent event)
		{
			final Consumer<Item> setItemModel = item ->
			{
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			};
			
			Stream.of(
					GENERIC_INGOT,
					GENERIC_NUGGET,
					GENERIC_DUST,
					SMALL_GENERIC_DUST,
					TINY_GENERIC_DUST
			).filter(i -> i != null && i != Items.AIR)
			.forEach(setItemModel);
		}
	}
	
	// Static utility methods
	
	public static <T extends Item> T setRegistryNameAndTranslationKey(T entry, String name, String key)
	{
		entry.setRegistryName(name).setTranslationKey(entry.getRegistryName().getNamespace() + "." + key);
		return entry;
	}
	
	public static <T extends Item> T setRegistryNameAndTranslationKey(T entry, String name)
	{
		return setRegistryNameAndTranslationKey(entry, name, name);
	}
}
