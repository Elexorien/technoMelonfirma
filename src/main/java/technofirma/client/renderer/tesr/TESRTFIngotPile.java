package technofirma.client.renderer.tesr;

import com.bioxx.tfc.Render.TESR.TESRIngotPile;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import technofirma.blocks.BlockTFIngotPile;
import technofirma.core.Reference;
import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import technofirma.tileentities.TETFIngotPile;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Render.Models.ModelIngotPile;
import com.bioxx.tfc.Render.TESR.TESRBase;

public class TESRTFIngotPile extends TESRIngotPile
{
	/** The normal small chest model. */
	private final ModelIngotPile ingotModel = new ModelIngotPile();

	private String Icon;
	
	public TESRTFIngotPile SetIconTexture(String icon)
	{
		Icon = icon;
		return this;
	}
	
	public void renderTileEntityIngotPileAt(TETFIngotPile tep, double d, double d1, double d2, float f)
	{
		Block block = tep.getBlockType();
		if (tep.getWorldObj() != null && tep.getStackInSlot(0) != null && block == TechnoItems.TFIngotPile)
		{
			int i = ((BlockTFIngotPile) block).getStack(tep.getWorldObj(), tep);
			TFC_Core.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/metal/Thaumic Steel.png")); //texture

			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			ingotModel.renderIngots(i);
			GL11.glPopMatrix(); //end
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityIngotPileAt((TETFIngotPile) te, par2, par4, par6, par8);
	}
}
