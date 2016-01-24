package technofirma.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Items.Tools.ItemCustomAxe;

public class ItemTechnofirmaAxe extends ItemCustomAxe
{
	public String Icon;
	
	public ItemTechnofirmaAxe(ToolMaterial e, float damage)
	{
		super(e, damage);
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
