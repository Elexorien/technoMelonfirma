package technofirma.client.handlers;

import technofirma.client.gui.GuiMagicAnvil;
import technofirma.client.gui.GuiMagicPlanSelection;
import technofirma.client.gui.GuiStakeClaim;
import technofirma.tileentities.TEMagicAnvil;
import technofirma.tileentities.TEStakeClaim;

import com.bioxx.tfc.GUI.GuiInventoryTFC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler extends technofirma.handlers.GuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te;
		try
		{
			te= world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			te = null;
		}

		switch(ID)
		{
			case 0:
				return new GuiMagicAnvil(player.inventory, (TEMagicAnvil) te, world, x, y, z);
			case 1:
				return new GuiMagicPlanSelection(player, (TEMagicAnvil) te, world, x, y, z);
			case 2:
				return new GuiStakeClaim(player, (TEStakeClaim) te, world, x, y, z);
			default:
				return null;
		}
	}

	@SubscribeEvent
	public void openGuiHandler(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof GuiInventoryTFC))
			event.gui = new GuiInventoryTFC(Minecraft.getMinecraft().thePlayer);
	}
}
