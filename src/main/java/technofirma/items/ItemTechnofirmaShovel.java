package technofirma.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Items.Tools.ItemCustomShovel;

public class ItemTechnofirmaShovel extends ItemCustomShovel
{
	public String Icon;
	
	public ItemTechnofirmaShovel(ToolMaterial e)
	{
		super(e);
	}
	
	public Item SetIconTexture(String icon)
	{
		Icon = icon;
		return this;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Icon);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return this.itemIcon;
	}
}
