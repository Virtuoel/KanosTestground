package virtuoel.kanostestground.init;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import virtuoel.kanostestground.KanosTestground;

@Mod.EventBusSubscriber(modid = KanosTestground.MOD_ID)
public class FluidRegistrar
{
	public static final Fluid MOLTEN_GENERIC =
			registerFluidWithMaterial(
					createFluid("molten_generic")
					.setDensity(2000)
					.setViscosity(10000)
					.setTemperature(1000)
					.setLuminosity(10)
					.setRarity(EnumRarity.UNCOMMON),
					Material.LAVA);
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		Stream.of(
				MOLTEN_GENERIC,
		null).filter(Objects::nonNull)
		.map(Fluid::getBlock)
		.filter(b -> b != null && b != Blocks.AIR)
		.forEach(event.getRegistry()::register);
	}
	
	@Mod.EventBusSubscriber(modid = KanosTestground.MOD_ID, value = Side.CLIENT)
	private static class Client
	{
		@SubscribeEvent
		public static void registerBlockModels(ModelRegistryEvent event)
		{
			final Consumer<Block> setFluidBlockStateMapper = block ->
			{
				ModelLoader.setCustomStateMapper(block, new StateMapperBase()
				{
					@Override
					protected ModelResourceLocation getModelResourceLocation(IBlockState state)
					{
						return new ModelResourceLocation(state.getBlock().getRegistryName(), "fluid");
					}
				});
			};
			
			Stream.of(
					MOLTEN_GENERIC,
			null).filter(Objects::nonNull)
			.map(Fluid::getBlock)
			.filter(b -> b != null && b != Blocks.AIR)
			.forEach(setFluidBlockStateMapper);
		}
	}
	
	// Static utility methods
	
	public static Fluid registerFluidWithBlock(Fluid fluid, boolean addBucket, @Nullable Function<Fluid, Block> blockGetter)
	{
		if(!FluidRegistry.registerFluid(fluid))
		{
			fluid = FluidRegistry.getFluid(fluid.getName());
		}
		
		if(blockGetter != null && fluid.getBlock() == null)
		{
			fluid.setBlock(blockGetter.apply(fluid));
		}
		
		if(addBucket)
		{
			FluidRegistry.addBucketForFluid(fluid);
		}
		
		return fluid;
	}
	
	public static Fluid registerFluidWithBlock(Fluid fluid, @Nullable Function<Fluid, Block> blockGetter)
	{
		return registerFluidWithBlock(fluid, true, blockGetter);
	}
	
	public static Fluid registerFluidWithMaterial(Fluid fluid, boolean addBucket, Material material)
	{
		return registerFluidWithBlock(fluid, addBucket, f -> BlockRegistrar.setRegistryNameAndTranslationKey(new BlockFluidClassic(f, material), f.getName()));
	}
	
	public static Fluid registerFluidWithMaterial(Fluid fluid, Material material)
	{
		return registerFluidWithMaterial(fluid, true, material);
	}
	
	public static Fluid registerFluid(Fluid fluid, boolean addBucket)
	{
		return registerFluidWithMaterial(fluid, addBucket, Material.WATER);
	}
	
	public static Fluid registerFluid(Fluid fluid)
	{
		return registerFluidWithMaterial(fluid, Material.WATER);
	}
	
	public static Fluid createFluid(String fluidName, ResourceLocation still, ResourceLocation flowing)
	{
		return new Fluid(fluidName, still, flowing)
				.setUnlocalizedName(getActiveModID() + "." + fluidName);
	}
	
	public static Fluid createFluid(String fluidName)
	{
		final String mod_id = getActiveModID();
		return createFluid(fluidName,
				new ResourceLocation(mod_id, "blocks/" + fluidName + "_still"),
				new ResourceLocation(mod_id, "blocks/" + fluidName + "_flow")
		);
	}
	
	/**
	 * TODO Move this method some place better.
	 */
	public static String getActiveModID()
	{
		ModContainer mc = Loader.instance().activeModContainer();
		return mc == null || (mc instanceof InjectedModContainer && ((InjectedModContainer)mc).wrappedContainer instanceof FMLContainer) ? "minecraft" : mc.getModId().toLowerCase(Locale.ROOT);
	}
}
