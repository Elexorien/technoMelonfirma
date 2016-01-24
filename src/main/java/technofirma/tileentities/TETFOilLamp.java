package technofirma.tileentities;

import technofirma.lamp.LampFluid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TELightEmitter;
import com.bioxx.tfc.api.TFCOptions;

public class TETFOilLamp extends TELightEmitter
{
	private FluidStack fuel;

	public TETFOilLamp()
	{
	}
	
	public FluidStack getFuel()
	{
		if(fuel == null)
			return null;
		int multiplier = TFCOptions.oilLampFuelMult;
		
		LampFluid lampfluid = LampFluid.GetLampFluid(fuel.getFluid());
		
		if (lampfluid != null)
			multiplier *= lampfluid.GetDurationModifier();
		
		FluidStack f = fuel.copy();
		f.amount /= multiplier;
		return f;
	}

	public void updateLampFuel()
	{
		if(fuel != null)
		{
			LampFluid lampfluid = LampFluid.GetLampFluid(fuel.getFluid());
			
			if (lampfluid != null)
			{
				if((int)TFC_Time.getTotalHours() - (TFCOptions.oilLampFuelMult * lampfluid.GetDurationModifier()) >= hourPlaced)
				{
					int diff = (int)TFC_Time.getTotalHours() - this.hourPlaced;
					this.hourPlaced = (int)TFC_Time.getTotalHours();
		
					if(this.getFuelAmount() > 0)
					{
						fuel.amount -= diff;
						if(fuel.amount <= 0)
							fuel = null;
					}
				}
			}
		}
	}

	public void setFuelFromStack(FluidStack fs)
	{
		int multiplier = TFCOptions.oilLampFuelMult;
		
		LampFluid lampfluid = LampFluid.GetLampFluid(fs.getFluid());
		
		if (lampfluid != null)
			multiplier *= lampfluid.GetDurationModifier();
		
		fuel = fs;
		fuel.amount *= multiplier;
	}

	public boolean isFuelValid()
	{
		if(getFuel() != null)
		{
			LampFluid lampfluid = LampFluid.GetLampFluid(getFuel().getFluid());
			if (lampfluid != null)
				return true;
		}
		return false;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("Fuel"))
			this.fuel = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("Fuel"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(fuel != null)
			nbt.setTag("Fuel", fuel.writeToNBT(new NBTTagCompound()));
	}

	public int getFuelAmount()
	{
		if(fuel == null)
			return 0;
		else return fuel.amount;
	}

	public int getFuelTimeLeft()
	{
		int f = getFuelAmount();
		float perc = f/250f;
		return (int)((TFC_Time.daysInYear*24)*perc);
	}
}