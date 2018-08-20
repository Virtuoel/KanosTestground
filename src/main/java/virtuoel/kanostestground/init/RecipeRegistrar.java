package virtuoel.kanostestground.init;

import java.util.stream.Stream;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import virtuoel.kanostestground.KanosTestground;

@EventBusSubscriber(modid = KanosTestground.MOD_ID)
public class RecipeRegistrar
{
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		GameRegistry.addSmelting(new ItemStack(BlockRegistrar.GENERIC_ORE), new ItemStack(ItemRegistrar.GENERIC_INGOT), 0.1F);
		
		GameRegistry.addSmelting(new ItemStack(ItemRegistrar.GENERIC_DUST), new ItemStack(ItemRegistrar.GENERIC_INGOT), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ItemRegistrar.TINY_GENERIC_DUST), new ItemStack(ItemRegistrar.GENERIC_NUGGET), 0.1F);
		
		Stream.of(
				ItemRegistrar.GENERIC_SWORD,
				ItemRegistrar.GENERIC_PICKAXE,
				ItemRegistrar.GENERIC_AXE,
				ItemRegistrar.GENERIC_SHOVEL,
				ItemRegistrar.GENERIC_HOE,
				
				ItemRegistrar.GENERIC_HELMET,
				ItemRegistrar.GENERIC_CHESTPLATE,
				ItemRegistrar.GENERIC_LEGGINGS,
				ItemRegistrar.GENERIC_BOOTS
		).filter(i -> i != null && i != Items.AIR)
		.forEach(item ->
			GameRegistry.addSmelting(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ItemRegistrar.GENERIC_NUGGET), 0.1F)
		);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST) // Register OreDict after Item and Block ObjectHolders are applied
	public static void registerOreDict(RegistryEvent.Register<IRecipe> event)
	{
		OreDictionary.registerOre("oreGeneric", BlockRegistrar.GENERIC_ORE);
		OreDictionary.registerOre("blockGeneric", BlockRegistrar.GENERIC_BLOCK);
		
		OreDictionary.registerOre("ingotGeneric", ItemRegistrar.GENERIC_INGOT);
		OreDictionary.registerOre("nuggetGeneric", ItemRegistrar.GENERIC_NUGGET);
		OreDictionary.registerOre("dustGeneric", ItemRegistrar.GENERIC_DUST);
		OreDictionary.registerOre("dustSmallGeneric", ItemRegistrar.SMALL_GENERIC_DUST);
		OreDictionary.registerOre("dustTinyGeneric", ItemRegistrar.TINY_GENERIC_DUST);
	}
}
