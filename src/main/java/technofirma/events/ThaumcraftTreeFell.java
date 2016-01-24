package technofirma.events;

import technofirma.core.TechnofirmaCore;
import thaumcraft.api.ItemApi;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ThaumcraftTreeFell 
{
	int searchDist = 10;
	boolean isStone = false;
	static int damage = 0;
	
	public boolean IsATreeBlock(Block block)
	{
		return TechnofirmaCore.GetThaumcraftBlock("blockMagicalLog") == block;
	}
	
	@SubscribeEvent
	public void OnBlockHarvest(BlockEvent.BreakEvent event)
	{
		if (IsATreeBlock(event.block))
		{			
			ItemStack equip = event.getPlayer().getCurrentEquippedItem();
			
			if (equip == null) return;
			
			boolean isAxeorSaw = false;
			boolean isHammer = false;
			for(int cnt = 0; cnt < Recipes.axes.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.axes[cnt])
				{
					isAxeorSaw = true;
					if(cnt < 4)
						isStone = true;
				}
			}
			
			for(int cnt = 0; cnt < Recipes.hammers.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.hammers[cnt])
					isHammer = true;
			}
			
			if (isAxeorSaw)
			{
				event.setCanceled(true);
				BreakTFCLog(event);
			}
			else if(isHammer)
			{
				EntityItem item = new EntityItem(event.world, event.x + 0.5, event.y + 0.5, event.z + 0.5, new ItemStack(TFCItems.stick, 1 + event.world.rand.nextInt(3)));
				event.world.spawnEntityInWorld(item);
			}
			else
				event.world.setBlock(event.x, event.y, event.z, event.block, event.blockMetadata, 0x2);
		}
	}
	
	public void BreakTFCLog(BlockEvent.BreakEvent event)
	{
		World world = event.world;
		EntityPlayer player = event.getPlayer();
		Block block = event.block;
		int blockx = event.x;
		int blocky = event.y;
		int blockz = event.z;
		int blockmeta = event.blockMetadata;
		
        ItemStack stack = player.getCurrentEquippedItem();
		
		damage = -1;
		boolean[][][] checkArray = new boolean[searchDist * 2 + 1][256][searchDist * 2 + 1];
		scanLogs(world, blockx, blocky, blockz, blockmeta, checkArray, (byte)0, (byte)0, (byte)0, stack, player);
		
		if(damage + stack.getItemDamage() > stack.getMaxDamage())
		{
			int ind = player.inventory.currentItem;
			player.inventory.setInventorySlotContents(ind, null);
			world.setBlock(blockx, blocky, blockz, block, blockmeta, 0x2);
		}
		else
			stack.damageItem(damage, player);
	}
		
	
	// From minecraft block code
    protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack is)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, is);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
	
	// From TFC's block
	private void scanLogs(World world, int i, int j, int k, int l, boolean[][][] checkArray, byte x, byte y, byte z, ItemStack stack, EntityPlayer player)
	{
		Block block = world.getBlock(i, j, k);
		if(y >= 0 && j + y < 256)
		{
			int offsetX = 0;int offsetY = 0;int offsetZ = 0;
			checkArray[x + searchDist][y][z + searchDist] = true;

			for (offsetX = -3; offsetX <= 3; offsetX++)
			{
				for (offsetZ = -3; offsetZ <= 3; offsetZ++)
				{
					for (offsetY = 0; offsetY <= 2; offsetY++)
					{
						if(Math.abs(x + offsetX) <= searchDist && j + y + offsetY < 256 && Math.abs(z + offsetZ) <= searchDist)
						{
							if(checkOut(world, i + x + offsetX, j + y + offsetY, k + z + offsetZ, l)
									&& !(offsetX == 0 && offsetY == 0 && offsetZ == 0)
									&& !checkArray[x + offsetX + searchDist][y + offsetY][z + offsetZ + searchDist])
								scanLogs(world,i, j, k, l, checkArray, (byte)(x + offsetX),(byte)(y + offsetY),(byte)(z + offsetZ), stack, player);
						}
					}
				}
			}
			
			damage++;
			if(stack != null)
			{
				if(damage+stack.getItemDamage() <= stack.getMaxDamage())
				{
					world.setBlock(i + x, j + y, k + z, Blocks.air, 0, 0x2);
					if((isStone && world.rand.nextInt(10) != 0) || !isStone)
						dropBlockAsItem(world, i + x, j + y, k + z, ItemApi.getBlock("blockMagicalLog", block.damageDropped(l)));
				}
			}
			else
			{
				world.setBlockToAir(i, j, k);
				dropBlockAsItem(world, i, j, k, ItemApi.getBlock("blockMagicalLog", block.damageDropped(l)));
			}
		}
	}

	private boolean checkOut(World world, int x, int y, int z, int meta)
	{
		if(IsATreeBlock(world.getBlock(x, y, z)))
			return true;
		return false;
	}
}
