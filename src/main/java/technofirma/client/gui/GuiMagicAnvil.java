package technofirma.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import technofirma.containers.ContainerMagicAnvil;
import technofirma.core.Reference;
import technofirma.core.TechnofirmaCore;
import technofirma.crafting.MagicAnvilManager;
import technofirma.crafting.MagicPlanRecipe;
import technofirma.tileentities.TEMagicAnvil;
import thaumcraft.api.aspects.Aspect;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.GUI.GuiContainerTFC;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Enums.RuleEnum;

public class GuiMagicAnvil extends GuiContainerTFC
{
	TEMagicAnvil anvilTE;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_anvil.png");
	EntityPlayer player;
	int x, y, z;
	String plan = "";
	ItemStack input = null;
	ItemStack input2 = null;
	ItemStack wandinput = null;
	
	public GuiMagicAnvil(InventoryPlayer inventoryplayer, TEMagicAnvil te, World world, int x, int y, int z)
	{
		super(new ContainerMagicAnvil(inventoryplayer, te, world, x, y, z), 208, 117);
		anvilTE = te;
		player = inventoryplayer.player;
		anvilTE.lastWorker = player;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		buttonList.clear();
		buttonList.add(new GuiMagicAnvilButton(7, guiLeft + 123, guiTop + 82, 16, 16, TFC_Textures.anvilShrink, 208, 17, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.Shrink")));
		buttonList.add(new GuiMagicAnvilButton(6, guiLeft + 105, guiTop + 82, 16, 16, TFC_Textures.anvilUpset, 208, 17, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.Upset")));
		buttonList.add(new GuiMagicAnvilButton(5, guiLeft + 123, guiTop + 64, 16, 16, TFC_Textures.anvilBend, 208, 17, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.Bend")));
		buttonList.add(new GuiMagicAnvilButton(4, guiLeft + 105, guiTop + 64, 16, 16, TFC_Textures.anvilPunch, 208, 17, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.Punch")));
		buttonList.add(new GuiMagicAnvilButton(3, guiLeft + 87, guiTop + 82, 16, 16, TFC_Textures.anvilDraw, 208, 33, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.Draw")));
		buttonList.add(new GuiMagicAnvilButton(2, guiLeft + 69, guiTop + 82, 16, 16, TFC_Textures.anvilHitHeavy, 208, 33, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.HeavyHit")));
		buttonList.add(new GuiMagicAnvilButton(1, guiLeft + 87, guiTop + 64, 16, 16, TFC_Textures.anvilHitMedium, 208, 33, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.MediumHit")));
		buttonList.add(new GuiMagicAnvilButton(0, guiLeft + 69, guiTop + 64, 16, 16, TFC_Textures.anvilHitLight, 208, 33, 16, 16, this, StatCollector.translateToLocal("gui.Anvil.LightHit")));
		buttonList.add(new GuiButton(8, guiLeft + 13, guiTop + 53, 36, 20, StatCollector.translateToLocal("gui.Anvil.Weld")));
		buttonList.add(new GuiMagicAnvilButton(9, guiLeft + 113, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 2, TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2]));
		buttonList.add(new GuiMagicAnvilButton(10, guiLeft + 94, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 1, TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2]));
		buttonList.add(new GuiMagicAnvilButton(11, guiLeft + 75, guiTop + 7, 19, 21, 208, 49, 19, 21, this, 0, TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2]));
		buttonList.add(new GuiMagicAnvilPlanButton(12, guiLeft + 122, guiTop + 45, 18, 18, this, StatCollector.translateToLocal("gui.Anvil.Plans")));
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		String craftingPlan = this.anvilTE.craftingPlan;
		ItemStack stack1 = this.anvilTE.anvilItemStacks[TEMagicAnvil.INPUT1_SLOT];
		ItemStack stack2 = this.anvilTE.anvilItemStacks[TEMagicAnvil.INPUT2_SLOT];
		ItemStack wanditem = this.anvilTE.anvilItemStacks[TEMagicAnvil.WAND_SLOT];

		if ((craftingPlan != null && craftingPlan != plan) || (stack1 != null && stack1 != input) || (stack2 != null && stack2 != input2) || (wanditem != null && wanditem != wandinput)) // Fixes NPE
		{
			plan = this.anvilTE.craftingPlan;
			this.anvilTE.updateRecipe();
			input = this.anvilTE.anvilItemStacks[TEMagicAnvil.INPUT1_SLOT];
			input2 = this.anvilTE.anvilItemStacks[TEMagicAnvil.INPUT2_SLOT];
			wanditem = wandinput;
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0)
			anvilTE.actionLightHammer();
		else if (guibutton.id == 2)
			anvilTE.actionHeavyHammer();
		else if (guibutton.id == 1)
			anvilTE.actionHammer();
		else if (guibutton.id == 3)
			anvilTE.actionDraw();
		else if (guibutton.id == 4)
			anvilTE.actionPunch();
		else if (guibutton.id == 5)
			anvilTE.actionBend();
		else if (guibutton.id == 6)
			anvilTE.actionUpset();
		else if (guibutton.id == 7)
			anvilTE.actionShrink();
		else if (guibutton.id == 8)
			anvilTE.actionWeld();
		else if (guibutton.id == 12 && this.anvilTE.anvilItemStacks[TEMagicAnvil.INPUT1_SLOT] != null)
			player.openGui(TechnofirmaCore.instance, 1, player.worldObj, x, y, z);
		this.inventorySlots.detectAndSendChanges();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		if (anvilTE != null)
		{			
			int i1 = anvilTE.getCraftingValue();
			drawTexturedModalRect(guiLeft + 27 + i1, guiTop + 103, 213, 10, 5, 5);
			
			i1 = anvilTE.getItemCraftingValue();
			drawTexturedModalRect(guiLeft + 27 + i1, guiTop + 108, 208, 10, 5, 6);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			//Round to 1 decimal place XX.X%
			if (anvilTE.workRecipe != null)
			{				
				MagicAnvilManager manager = MagicAnvilManager.getInstance();
				MagicPlanRecipe recipeplan = manager.getPlans().get(anvilTE.workRecipe.getPlan());
				
				int posy = guiTop + 8;
				if (recipeplan.aspects.size() > 0)
				{
					for(Aspect aspect : recipeplan.aspects.getAspects())
					{
						fontRendererObj.drawString(aspect.getName() + ": " + (recipeplan.aspects.getAmount(aspect)), guiLeft + 150, posy, 0xff6000);
						posy += 8;
					}
				}
			}

			drawItemRulesImages(guiLeft, guiTop);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			drawRulesImages(guiLeft, guiTop);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		}
	}

	@Override
	public void drawTooltip(int mx, int my, String text)
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		this.drawHoveringText(list, mx, my + 15, this.fontRendererObj);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void drawItemRulesImages(int w, int h)
	{
		if (anvilTE != null && anvilTE.itemCraftingRules != null)
		{
			MagicPlanRecipe p = MagicAnvilManager.getInstance().getPlan(anvilTE.craftingPlan);
			if (p == null)
				return;
			RuleEnum[] Rules = anvilTE.workRecipe != null ? p.rules : null;
			int[] ItemRules = anvilTE.getItemRules();

			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 31, getIconFromRule(ItemRules[0]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 31, getIconFromRule(ItemRules[1]), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 31, getIconFromRule(ItemRules[2]), 10, 10);

			this.mc.getTextureManager().bindTexture(texture);

			if (Rules != null && Rules[0].matches(ItemRules, 0))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 77, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (Rules != null && Rules[1].matches(ItemRules, 1))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 96, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (Rules != null && Rules[2].matches(ItemRules, 2))
				GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
			drawTexturedModalRect(w + 115, h + 28, 210, 115, 15, 15);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public void drawRulesImages(int w, int h)
	{
		if (anvilTE.workRecipe != null)
		{
			MagicPlanRecipe p = MagicAnvilManager.getInstance().getPlan(anvilTE.craftingPlan);
			if (p == null)
				return;
			RuleEnum[] Rules = p.rules;
			//int[] ItemRules = anvilTE.getItemRules();

			TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
			this.drawTexturedModelRectFromIcon(w + 80, h + 10, getIconFromRule(Rules[0].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 99, h + 10, getIconFromRule(Rules[1].Action), 10, 10);
			this.drawTexturedModelRectFromIcon(w + 118, h + 10, getIconFromRule(Rules[2].Action), 10, 10);

			TFC_Core.bindTexture(texture);
			//Bottom Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor0[0], TFCOptions.anvilRuleColor0[1], TFCOptions.anvilRuleColor0[2], (byte) 255);
			if (Rules[0].Min == 0)
				drawTexturedModalRect(w + 75, h + 26, 228, 68, 19, 3);
			if (Rules[0].Max > 0 && (Rules[0].Min <= 1 || Rules[0].Max == 1))
				drawTexturedModalRect(w + 94, h + 26, 228, 68, 19, 3);
			if (Rules[0].Max > 1 && (Rules[0].Min <= 2 || Rules[0].Max == 2))
				drawTexturedModalRect(w + 113, h + 26, 228, 68, 19, 3);
			//Middle Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor1[0], TFCOptions.anvilRuleColor1[1], TFCOptions.anvilRuleColor1[2], (byte) 255);
			if (Rules[1].Min == 0)
				drawTexturedModalRect(w + 75, h + 24, 228, 68, 19, 3);
			if (Rules[1].Max > 0 && (Rules[1].Min <= 1 || Rules[1].Max == 1))
				drawTexturedModalRect(w + 94, h + 24, 228, 68, 19, 3);
			if (Rules[1].Max > 1 && (Rules[1].Min <= 2 || Rules[1].Max == 2))
				drawTexturedModalRect(w + 113, h + 24, 228, 68, 19, 3);
			//Top Row
			GL11.glColor4ub(TFCOptions.anvilRuleColor2[0], TFCOptions.anvilRuleColor2[1], TFCOptions.anvilRuleColor2[2], (byte) 255);
			if (Rules[2].Min == 0)
				drawTexturedModalRect(w + 75, h + 22, 228, 68, 19, 3);
			if (Rules[2].Max > 0 && (Rules[2].Min <= 1 || Rules[2].Max == 1))
				drawTexturedModalRect(w + 94, h + 22, 228, 68, 19, 3);
			if (Rules[2].Max > 1 && (Rules[2].Min <= 2 || Rules[2].Max == 2))
				drawTexturedModalRect(w + 113, h + 22, 228, 68, 19, 3);
		}

		//GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		//drawTexturedModalRect(w + 75, h + 7, 208, 49, 19, 21);
	}

	public IIcon getIconFromRule(int Action)
	{
		switch (Action)
		{
		case 0:
			return TFC_Textures.anvilHit;
		case 1:
			return TFC_Textures.anvilDraw;
		case 3:
			return TFC_Textures.anvilPunch;
		case 4:
			return TFC_Textures.anvilBend;
		case 5:
			return TFC_Textures.anvilUpset;
		case 6:
			return TFC_Textures.anvilShrink;
		default:
			return TFC_Textures.invisibleTexture;
		}
	}

	@Override
	protected boolean func_146978_c/*isPointInRegion*/(int slotX, int slotY, int sizeX, int sizeY, int clickX, int clickY)
	{
		int k1 = this.guiLeft;
		int l1 = this.guiTop;
		clickX -= k1;
		clickY -= l1;
		return clickX >= slotX - 1 && clickX < slotX + sizeX + 1 && clickY >= slotY - 1 && clickY < slotY + sizeY + 1;
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(int drawX, int drawY, int drawWidth, int drawHeight, int u, int v, int width, int height)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(drawX + 0, drawY + drawHeight, this.zLevel, (u + 0) * f, (v + height) * f1);
		tessellator.addVertexWithUV(drawX + drawWidth, drawY + drawHeight, this.zLevel, (u + width) * f, (v + height) * f1);
		tessellator.addVertexWithUV(drawX + drawWidth, drawY + 0, this.zLevel, (u + width) * f, (v + 0) * f1);
		tessellator.addVertexWithUV(drawX + 0, drawY + 0, this.zLevel, (u + 0) * f, (v + 0) * f1);
		tessellator.draw();
	}

	@Override
	public void drawTexturedModelRectFromIcon(int x, int y, IIcon par3Icon, int width, int height)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, this.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + height, this.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
		tessellator.addVertexWithUV(x + width, y + 0, this.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
		tessellator.draw();
	}
}
