package technofirma.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import technofirma.core.TechnofirmaCore;
import technofirma.tileentities.TEStakeClaim;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;

public class BlockStakeClaim extends BlockTerraContainer
{
	public BlockStakeClaim()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			if((TEStakeClaim)world.getTileEntity(i, j, k)!=null)
			{
				entityplayer.openGui(TechnofirmaCore.instance, 2, world, i, j, k);
			}
			return true;
		}
	}
	
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEStakeClaim();
	}
		
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
