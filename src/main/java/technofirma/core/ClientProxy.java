package technofirma.core;

import scala.collection.parallel.ParIterableLike;
import technofirma.client.handlers.GuiHandler;
import technofirma.client.renderer.RenderDarkOre;
import technofirma.client.renderer.RenderLantern;
import technofirma.client.renderer.RenderMagicAnvil;
import technofirma.client.renderer.tesr.TESRMagicAnvil;
import technofirma.client.renderer.RenderTFIngotPile;
import technofirma.client.renderer.tesr.TESRTFIngotPile;
import technofirma.events.EventHooks;
import technofirma.events.ThaumcraftTreeFell;
import technofirma.handlers.NEIHandler;
import technofirma.tileentities.TEMagicAnvil;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import technofirma.tileentities.TETFIngotPile;

public class ClientProxy extends CommonProxy
{
	@Override
	public void RegisterRenderers()
	{
		// Our Magic Anvil ID
		RenderingRegistry.registerBlockHandler(TechnoItems.OilLanternRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderLantern());
	
		if (TechnofirmaCore.foundThaumcraft)
		{
			// Our Dark Ore render ID
			RenderingRegistry.registerBlockHandler(TechnoItems.DarkOreRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderDarkOre());
		
			// Our Magic Anvil ID
			RenderingRegistry.registerBlockHandler(TechnoItems.DarkMagicAnvilRenderID = RenderingRegistry.getNextAvailableRenderId(), new RenderMagicAnvil());

   		}
	}
	
	@Override
	public void RegisterMagicTileEntities()
	{
		ClientRegistry.registerTileEntity(TEMagicAnvil.class, "MagicAnvil", new TESRMagicAnvil());
        ClientRegistry.registerTileEntity(TETFIngotPile.class, "IngotPile", new TESRTFIngotPile());
	}
	
	@Override
	public void RegisterGuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TechnofirmaCore.instance, new GuiHandler());
	}
	
	@Override
	public void RegisterEvents()
	{
		// TreeCap Handler
		MinecraftForge.EVENT_BUS.register(new ThaumcraftTreeFell());
		
		// NEI Handler
		MinecraftForge.EVENT_BUS.register(new NEIHandler());
		
		// GUI Handler
		MinecraftForge.EVENT_BUS.register(new GuiHandler());

        MinecraftForge.EVENT_BUS.register(new EventHooks());
	}
}
