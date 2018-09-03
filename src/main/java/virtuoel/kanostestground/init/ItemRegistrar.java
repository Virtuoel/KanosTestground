package virtuoel.kanostestground.init;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
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
	
	public static final Item GENERIC_SWORD = Items.AIR;
	public static final Item GENERIC_PICKAXE = Items.AIR;
	public static final Item GENERIC_AXE = Items.AIR;
	public static final Item GENERIC_SHOVEL = Items.AIR;
	public static final Item GENERIC_HOE = Items.AIR;
	
	public static final Item GENERIC_HELMET = Items.AIR;
	public static final Item GENERIC_CHESTPLATE = Items.AIR;
	public static final Item GENERIC_LEGGINGS = Items.AIR;
	public static final Item GENERIC_BOOTS = Items.AIR;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		Stream.of(
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
						"tiny_generic_dust"),
				
				setRegistryNameAndTranslationKey(
						new ItemSword(ToolMaterials.GENERIC)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_sword"),
				setRegistryNameAndTranslationKey(
						new ItemPickaxe(ToolMaterials.GENERIC) // AT'd <init>
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_pickaxe"),
				setRegistryNameAndTranslationKey(
						new ItemAxe(ToolMaterials.GENERIC, 8.0F, -3.1F){} // anonymous class to bypass protected Forge-added constructor instead of an entire subclass
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_axe"),
				setRegistryNameAndTranslationKey(
						new ItemSpade(ToolMaterials.GENERIC)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_shovel"),
				setRegistryNameAndTranslationKey(
						new ItemHoe(ToolMaterials.GENERIC)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_hoe"),
				
				setRegistryNameAndTranslationKey(
						new ItemArmor(ArmorMaterials.GENERIC, 0, EntityEquipmentSlot.HEAD)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_helmet"),
				setRegistryNameAndTranslationKey(
						new ItemArmor(ArmorMaterials.GENERIC, 0, EntityEquipmentSlot.CHEST)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_chestplate"),
				setRegistryNameAndTranslationKey(
						new ItemArmor(ArmorMaterials.GENERIC, 0, EntityEquipmentSlot.LEGS)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_leggings"),
				setRegistryNameAndTranslationKey(
						new ItemArmor(ArmorMaterials.GENERIC, 0, EntityEquipmentSlot.FEET)
						.setCreativeTab(KanosTestground.CREATIVE_TAB),
						"generic_boots"),
		null).filter(Objects::nonNull)
		.forEach(event.getRegistry()::register);
	}
	
	public static void init()
	{
		ToolMaterials.GENERIC.setRepairItem(new ItemStack(GENERIC_INGOT)); // Set repair item after ObjectHolders are applied
		ArmorMaterials.GENERIC.setRepairItem(new ItemStack(GENERIC_INGOT));
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
					TINY_GENERIC_DUST,
					
					GENERIC_SWORD,
					GENERIC_PICKAXE,
					GENERIC_AXE,
					GENERIC_SHOVEL,
					GENERIC_HOE,
					
					GENERIC_HELMET,
					GENERIC_CHESTPLATE,
					GENERIC_LEGGINGS,
					GENERIC_BOOTS,
			null).filter(i -> i != null && i != Items.AIR)
			.forEach(setItemModel);
		}
	}
	
	public static class ToolMaterials // inner classes can prevent class-level ObjectHolder from trying to populate other constants
	{
		public static final Item.ToolMaterial GENERIC =
				EnumHelper.addToolMaterial(
						KanosTestground.MOD_ID + ":generic",
						3,
						500,
						6.5F,
						2.5F,
						8
				);
	}
	
	public static class ArmorMaterials
	{
		public static final ItemArmor.ArmorMaterial GENERIC =
				EnumHelper.addArmorMaterial(
						KanosTestground.MOD_ID + ":generic",
						KanosTestground.MOD_ID + ":generic",
						22,
						new int[] { 2, 5, 6, 2 }, // Boots, Legs, Chest, Helm
						8,
						SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
						0.5F
				);
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
