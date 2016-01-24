package technofirma.handlers;

import technofirma.containers.ContainerMagicAnvil;
import technofirma.containers.ContainerMagicPlanSelection;
import technofirma.containers.ContainerStakeClaim;
import technofirma.tileentities.TEMagicAnvil;
import technofirma.tileentities.TEStakeClaim;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getTileEntity(x, y, z);

		switch(ID)
		{
			case 0:
				return new ContainerMagicAnvil(player.inventory, (TEMagicAnvil)te, world, x, y, z);
			case 1:
				return new ContainerMagicPlanSelection(player, (TEMagicAnvil) te, world, x, y, z);
			case 2:
				return new ContainerStakeClaim(player.inventory, (TEStakeClaim) te, world, x, y, z);
			default:
			{
				return null;
			}
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
