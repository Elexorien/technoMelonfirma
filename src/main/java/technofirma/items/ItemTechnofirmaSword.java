package technofirma.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Items.Tools.ItemCustomSword;
import com.bioxx.tfc.api.Enums.EnumDamageType;

public class ItemTechnofirmaSword extends ItemCustomSword
{
	public String Icon;
	
	public ItemTechnofirmaSword(ToolMaterial e, float damage, EnumDamageType dt)
	{
		super(e, damage, dt);
	}
	
	public ItemTechnofirmaSword(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(par2EnumToolMaterial, damage);
		//this.weaponDamage = 150 + par2EnumToolMaterial.getDamageVsEntity();
		this.damageType = EnumDamageType.SLASHING;
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
