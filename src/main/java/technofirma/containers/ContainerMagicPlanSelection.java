package technofirma.containers;

import technofirma.tileentities.TEMagicAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerTFC;

public class ContainerMagicPlanSelection extends ContainerTFC
{
	TEMagicAnvil anvil;
	World world;
	EntityPlayer player;
	String plan = "";
	
	public ContainerMagicPlanSelection(EntityPlayer p, TEMagicAnvil a, World w, int x, int y, int z)
	{
		anvil = a;
		world = w;
		player = p;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(anvil.craftingPlan != plan)
			plan = anvil.craftingPlan;
	}
}
