package technofirma.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemTechnofirmaToolHead extends ItemTerra
{
	public String Icon;
	
	public ItemTechnofirmaToolHead()
	{
		super();
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFC_MISC);
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
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
}
