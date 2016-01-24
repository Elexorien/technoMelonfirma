package technofirma.client.gui;

import technofirma.containers.ContainerStakeClaim;
import technofirma.tileentities.TEStakeClaim;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.bioxx.tfc.GUI.GuiContainerTFC;

public class GuiStakeClaim extends GuiContainerTFC {

	public GuiStakeClaim(EntityPlayer p, TEStakeClaim te, World w, int x, int y, int z)
	{
		super(new ContainerStakeClaim(p.inventory, te, w, x, y, z), 176, 130);

	}

	
	@Override
	public void initGui() {
		super.initGui();
		
		GuiTextField textfield = new GuiTextField(Minecraft.getMinecraft().fontRenderer, 8, 8, 138, 24);
		
	}
}
