package technofirma.core;

import cpw.mods.fml.common.*;
import net.minecraftforge.fluids.FluidRegistry;
import technofirma.events.AntiTFCEvents;
import technofirma.events.EventHooks;
import thaumcraft.api.ItemApi;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        dependencies = Reference.MOD_DEPENDENCIES
    )

public class TechnofirmaCore
{
		@Instance(Reference.MOD_ID)
	public static TechnofirmaCore instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

    public static boolean foundBuildCraft;
    public static boolean foundForestry;
    public static boolean foundMekanism;
    public static boolean foundThaumcraft;

    public static ToolMaterial ThaumicSteelMaterial;
    
	public TechnofirmaCore() {}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{				
		FMLCommonHandler.instance().bus().register(new AntiTFCEvents());
		FMLCommonHandler.instance().bus().register(new EventHooks());
	}
	
	@EventHandler
	public void preload(FMLPreInitializationEvent event)
	{
        foundBuildCraft = Loader.isModLoaded("BuildCraft|Energy");
        foundForestry = Loader.isModLoaded("Forestry");
        foundMekanism = Loader.isModLoaded("Mekanism");
        foundThaumcraft = Loader.isModLoaded("Thaumcraft");

        proxy.RegisterGuiHandler();
		
		proxy.SetupNormalBlocks();
		proxy.RegisterNormalBlocks();

		proxy.RegisterTE();

        if (foundBuildCraft)
        {
            proxy.SetupBCItems();
            proxy.RegisterBCItems();
        }

        if (foundForestry)
        {
            proxy.SetupForestryItems();
            proxy.RegisterForestryItems();
        }

        if (foundMekanism)
        {
            proxy.SetupMekanismItems();
            proxy.RegisterMekanismItems();
        }

        if (foundThaumcraft)
		{
			proxy.RegisterMagicTileEntities();
			
			// Temp handling for metal
			HeatRaw ThaumicSteelRaw = new HeatRaw(0.35, 1540);
			
			// Create our ThaumicSteel material
			ThaumicSteelMaterial = EnumHelper.addToolMaterial("ThaumicSteel", 3, TFCItems.steelUses + 500, TFCItems.steelEff, 200, 10);
			
			proxy.SetupMagicBlocks();
			proxy.SetupMagicItems();
			
			proxy.RegisterMagicBlocks();
			proxy.RegisterMagicItems();
			
			// Heat registry
			HeatRegistry.getInstance().addIndex(new HeatIndex(new ItemStack(TechnoItems.ThaumicSteelIngot,1), ThaumicSteelRaw, new ItemStack(TechnoItems.ThaumicSteelUnshaped, 1)));
			
			MetalRegistry.instance.addMetal(TechnoItems.ThaumicSteel, Alloy.EnumTier.TierIV);

		}
	}
			
	@EventHandler
	public void load(FMLInitializationEvent event)
	{					
		if (foundThaumcraft)
		{
			proxy.RegisterAnvilPlans();
			proxy.RegisterAnvilRecipes();
			
			proxy.RegisterMagicAnvilPlans();
			proxy.RegisterMagicAnvilRecipes();

			proxy.RegisterMagicToolsClasses();
			
			proxy.RegisterRecipes();
		}
		
		proxy.SetupLampFuels();
		proxy.RegisterRenderers();
		proxy.RegisterWailaMessages();
		proxy.RegisterEvents();
	}	
	
	@EventHandler
	public void postload(FMLPostInitializationEvent  event)
	{				
		
		if (foundThaumcraft)
		{
			Item[] newaxes = new Item[] { TechnoItems.ThaumicSteelAxe };
			System.arraycopy(newaxes, 0, Recipes.axes, Recipes.axes.length - 1, newaxes.length);
			
			TechnofirmaCore.GetThaumcraftBlock("blockMagicalLog").setHardness(60);
		}
	}

	
	public static Block GetThaumcraftBlock(String itemString) {
		Block block = null;

		try {
			String itemClass = "thaumcraft.common.config.ConfigBlocks";
			Object obj = Class.forName(itemClass).getField(itemString).get(null);
			if (obj instanceof Block) {
				block = (Block)obj;
			}
		} catch (Exception ex) {
			FMLLog.warning("[Thaumcraft] Could not retrieve block identified by: " + itemString);
		}

		return block;
	}
}
