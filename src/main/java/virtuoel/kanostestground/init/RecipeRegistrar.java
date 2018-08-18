package virtuoel.kanostestground.init;

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
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST) // Register OreDict after Item and Block ObjectHolders are applied
	public static void registerOreDict(RegistryEvent.Register<IRecipe> event)
	{
		OreDictionary.registerOre("oreGeneric", BlockRegistrar.GENERIC_ORE);
		
		OreDictionary.registerOre("ingotGeneric", ItemRegistrar.GENERIC_INGOT);
	}
}
