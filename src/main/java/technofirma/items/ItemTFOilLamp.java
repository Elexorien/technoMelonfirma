package technofirma.items;

import java.util.List;

import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import technofirma.lamp.LampFluid;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.Util.Helper;

public class ItemTFOilLamp extends ItemTerraBlock implements ISmeltable, IFluidContainerItem
{
	public ItemTFOilLamp(Block par1)
	{
		super(par1);
		this.metaNames = new String[]{"Gold", "Platinum", "RoseGold", "Silver", "SterlingSilver", "WroughtIron"};
	}

	@Override
	public int getDisplayDamage(ItemStack is)
	{
		FluidStack fuel = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
		int amt = fuel != null ? fuel.amount : 0;
		return getMaxDamage(is) - amt;
	}

	@Override
	public boolean isDamaged(ItemStack is)
	{
		if (is.hasTagCompound())
			return true;
		else
			return false;
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return 250;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}
	
	@Override
    public EnumItemReach getReach(ItemStack is)
    {
        return EnumItemReach.SHORT;
    }

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		return 1;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is) {

		return 100;
	}

	@Override
	public boolean isSmeltable(ItemStack is) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		int meta = is.getItemDamage();
		switch(meta)
		{
		case 1: return Global.GOLD;
		case 2: return Global.PLATINUM;
		case 3: return Global.ROSEGOLD;
		case 4: return Global.SILVER;
		case 5: return Global.STERLINGSILVER;
		case 6: return Global.WROUGHTIRON;
		default : return Global.UNKNOWN;
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		
		if(stack.hasTagCompound())
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
			LampFluid lampfluid = LampFluid.GetLampFluid(fs.getFluid());
			if(fs == null || lampfluid == null)
			{
				metadata += 8;
			}
		}
		else
		{
			metadata += 8;
		}

		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if(is.hasTagCompound())
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
			LampFluid lampfluid = LampFluid.GetLampFluid(fs.getFluid());
			if(fs != null && lampfluid != null)
			{
				arraylist.add(fs.getFluid().getLocalizedName(fs));
				arraylist.add(((fs.amount) * (TFCOptions.oilLampFuelMult * lampfluid.GetDurationModifier())) + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((fs.amount / 250f) * 100f, 10) + "%)");
			}
		}
	}

	public static ItemStack GetFullLamp(int meta, Fluid fluid)
	{
		ItemStack is = new ItemStack(TechnoItems.OilLamp, 1, meta);
		if (fluid != null)
		{
			FluidStack fs = new FluidStack(fluid, 250);
			is.setTagCompound(fs.writeToNBT(new NBTTagCompound()));
		}
		return is;
	}

	@Override
	public FluidStack getFluid(ItemStack container) 
	{
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
	}

	@Override
	public int getCapacity(ItemStack container) 
	{
		return 250;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doSim) 
	{
		FluidStack fs = getFluid(container);
		int inAmt = 0;
		if(fs != null)
		{
			int max = getCapacity(container) - fs.amount;
			if(max > 0 && fs.isFluidEqual(resource))
			{
				inAmt = Math.min(max, resource.amount);
				if(doSim)
				{
					fs.amount += inAmt;
					if(container.getTagCompound() == null)
						container.setTagCompound(new NBTTagCompound());
					fs.writeToNBT(container.getTagCompound());
				}
			}
		}
		else 
		{
			inAmt = Math.min(getCapacity(container), resource.amount);
			if(doSim)
			{
				fs = resource.copy();
				fs.amount = inAmt;
				if(container.getTagCompound() == null)
					container.setTagCompound(new NBTTagCompound());
				fs.writeToNBT(container.getTagCompound());
			}
		}
		return inAmt;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doSim) 
	{
		FluidStack fs = getFluid(container);
		FluidStack fsOut = fs.copy();
		if(fs != null)
			fsOut.amount = Math.min(maxDrain, fs.amount);
		if(doSim && fsOut != null)
		{
			if(fs.amount - fsOut.amount <= 0)
			{
				container.stackTagCompound = null;
			}
			else
			{
				fs.amount -= fsOut.amount;
				if(container.getTagCompound() == null)
					container.setTagCompound(new NBTTagCompound());
				fs.writeToNBT(container.getTagCompound());
			}
		}
		return fsOut;
	}
}
