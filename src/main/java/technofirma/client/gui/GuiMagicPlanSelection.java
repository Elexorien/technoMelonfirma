package technofirma.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.GUI.GuiContainerTFC;

import technofirma.containers.ContainerMagicPlanSelection;
import technofirma.crafting.MagicAnvilManager;
import technofirma.crafting.MagicAnvilRecipe;
import technofirma.crafting.MagicPlanRecipe;
import technofirma.tileentities.TEMagicAnvil;
import thaumcraft.api.ThaumcraftApiHelper;

public class GuiMagicPlanSelection extends GuiContainerTFC
{
	TEMagicAnvil anvilTE;
	EntityPlayer player;
	World world;
	ArrayList<Object[]> plans;
	int x, y, z;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_plan.png");

	public GuiMagicPlanSelection(EntityPlayer p, TEMagicAnvil te, World w, int x, int y, int z)
	{
		super(new ContainerMagicPlanSelection(p, te, w, x, y, z), 176, 130);
		anvilTE = te;
		player = p;
		world = w;
		this.drawInventory = false;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		plans = getRecipes();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		int xOffset = 5;
		int yOffset = 14;
		int index = plans.size() - 1;
		for (Object[] o : plans)
		{
			String p = (String) o[0];
			MagicAnvilRecipe a = (MagicAnvilRecipe) o[1];
			buttonList.add(0, new GuiMagicPlanButton(plans.size() - 1 - index, guiLeft + xOffset, guiTop + yOffset, 16, 16, a.getCraftingResult(), this, StatCollector.translateToLocal("gui.plans." + p)));
			index--;
			if (xOffset + 36 < xSize)
				xOffset += 18;
			else
			{
				xOffset = 5;
				yOffset += 18;
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		Object[] p = (Object[]) plans.toArray()[guibutton.id];
		anvilTE.setPlan((String) p[0]);
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
		if (anvilTE.getStackInSlot(TEMagicAnvil.INPUT1_SLOT) != null)
			drawCenteredString(this.fontRendererObj, "Plans: " + anvilTE.getStackInSlot(TEMagicAnvil.INPUT1_SLOT).getDisplayName(), guiLeft + xSize / 2, guiTop + 5, 0x000000);
	}

	private ArrayList<Object[]> getRecipes()
	{
		MagicAnvilManager manager = MagicAnvilManager.getInstance();
		Object[] plans = manager.getPlans().keySet().toArray();
		ArrayList planList = new ArrayList();
		for (Object p : plans)
		{
			MagicPlanRecipe recipeplan = manager.getPlans().get(p);
					
			MagicAnvilRecipe ar = manager.findMatchingRecipe(new MagicAnvilRecipe(anvilTE.anvilItemStacks[TEMagicAnvil.INPUT1_SLOT], anvilTE.anvilItemStacks[TEMagicAnvil.INPUT2_SLOT], (String) p, null));
			
			ar = handleMatchingRecipe(ar, recipeplan);
			if (ar != null)
				planList.add(new Object[]
				{ (String) p, ar });
		}
		return planList;

	}

	MagicAnvilRecipe handleMatchingRecipe(MagicAnvilRecipe ar, MagicPlanRecipe plan)
	{
		if (ar != null)
		{
			boolean researchcomplete = ThaumcraftApiHelper.isResearchComplete(Minecraft.getMinecraft().thePlayer.getCommandSenderName(), plan.research);
			if (!researchcomplete)
				return null;
		}
		return ar;
	}

	@Override
	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringTextZLevel(list, mx, my + 15, this.fontRendererObj, 400);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
}
