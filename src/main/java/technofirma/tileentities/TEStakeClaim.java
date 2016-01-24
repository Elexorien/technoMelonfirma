package technofirma.tileentities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.TileEntities.NetworkTileEntity;

public class TEStakeClaim extends NetworkTileEntity
{
	public ArrayList<String> whitelistplayers;
	
	public int minx, miny, maxx, maxy;
	
	public TEStakeClaim()
	{
		whitelistplayers = new ArrayList<String>();
		minx = miny = -4;
		maxx = maxy = 4;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList playerNBT = new NBTTagList();
		for (int i = 0; i < whitelistplayers.size(); i++)
		{
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("player", whitelistplayers.get(i));
			playerNBT.appendTag(nbttagcompound1);
		}
		nbt.setTag("players", playerNBT);
		
		NBTTagCompound regionNBT = new NBTTagCompound();
		regionNBT.setInteger("minx", -4);
		regionNBT.setInteger("miny", -4);
		regionNBT.setInteger("maxx", 4);
		regionNBT.setInteger("maxy", 4);
		nbt.setTag("region", regionNBT);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("players"))
		{
			NBTTagList nbttaglist = nbt.getTagList("players", 10);
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				String player = nbttagcompound1.getString("player");
				if (player != null)
					whitelistplayers.add(player);
			}
		}
		
		if (nbt.hasKey("region"))
		{
			minx = nbt.getCompoundTag("region").getInteger("minx");
			miny = nbt.getCompoundTag("region").getInteger("miny");
			maxx = nbt.getCompoundTag("region").getInteger("maxx");
			maxy = nbt.getCompoundTag("region").getInteger("maxy");
		}
	}
	
	@Override
	public void createInitNBT(NBTTagCompound nbt) 
	{
		NBTTagCompound regionNBT = new NBTTagCompound();
		regionNBT.setInteger("minx", -4);
		regionNBT.setInteger("miny", -4);
		regionNBT.setInteger("maxx", 4);
		regionNBT.setInteger("maxy", 4);
		nbt.setTag("region", regionNBT);
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) 
	{
		if (nbt.hasKey("region"))
		{
			minx = nbt.getCompoundTag("region").getInteger("minx");
			miny = nbt.getCompoundTag("region").getInteger("miny");
			maxx = nbt.getCompoundTag("region").getInteger("maxx");
			maxy = nbt.getCompoundTag("region").getInteger("maxy");
		}
	}

}
