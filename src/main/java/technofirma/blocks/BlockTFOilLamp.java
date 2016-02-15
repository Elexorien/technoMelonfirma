package technofirma.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import technofirma.items.ItemTFOilLamp;
import technofirma.lamp.LampFluid;
import technofirma.tileentities.TETFOilLamp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TEOilLamp;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFOilLamp extends BlockTerraContainer
{
	IIcon[] icons;
	public BlockTFOilLamp()
	{
		super(Material.circuits);
		this.setTickRandomly(true);
		setHardness(4.0F);
        setResistance(10.0F);
        setBlockBounds(0.25F, 0.125F, 0.25F, 0.75F, 0.75F, 0.75F);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
		setStepSound(Block.soundTypeMetal);
		setLightLevel(0.9F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 8)
			return 0;

		return getLightValue();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 6; i++)
		{
			for(Fluid fluid : LampFluid.GetAllForgeFluids())
				list.add(ItemTFOilLamp.GetFullLamp(i, fluid));
			
			list.add(ItemTFOilLamp.GetFullLamp(i, null));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int m = meta & 7;
		if (side <= 1)
			return icons[(m * 2) + 1];
		else
			return icons[(m * 2)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int side)
	{
		return getIcon(side, access.getBlockMetadata(i, j, k));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons = new IIcon[12];
		icons[0] = registerer.registerIcon("technofirma:LanternSideGold");
		icons[1] = registerer.registerIcon("technofirma:LanternTopGold");
		
		icons[2] = registerer.registerIcon("technofirma:LanternSidePlatinum");
		icons[3] = registerer.registerIcon("technofirma:LanternTopPlatinum");
		
		icons[4] = registerer.registerIcon("technofirma:LanternSideRoseGold");
		icons[5] = registerer.registerIcon("technofirma:LanternTopRoseGold");
		
		icons[6] = registerer.registerIcon("technofirma:LanternSideSilver");
		icons[7] = registerer.registerIcon("technofirma:LanternTopSilver");
		
		icons[8] = registerer.registerIcon("technofirma:LanternSideSterlingSilver");
		icons[9] = registerer.registerIcon("technofirma:LanternTopSterlingSilver");
		
		icons[10] = registerer.registerIcon("technofirma:LanternSideWroughtIron");
		icons[11] = registerer.registerIcon("technofirma:LanternTopWroughtIron");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			ItemStack helditem = player.getHeldItem();
			
			int meta = world.getBlockMetadata(x, y, z);
			if(!isLampLit(meta))
			{
				TETFOilLamp te = (TETFOilLamp) world.getTileEntity(x, y, z);
				if (te != null)
				{
					te.updateLampFuel();
					if(te.isFuelValid())
						if(te.getFuelTimeLeft() > 0)
						{
							if (helditem != null)
							{
								FluidStack fsitem = FluidContainerRegistry.getFluidForFilledItem(helditem);
								
								if (fsitem != null)
								{
									FluidContainerRegistry.drainFluidContainer(helditem);
									te.setFuelFromStack(new FluidStack(fsitem.getFluid(), 1000));			
								}
							}
							else
								world.setBlockMetadataWithNotify(x, y, z, meta-8, 3);
						}
				}
			}
			else
			{
				TETFOilLamp te = (TETFOilLamp) world.getTileEntity(x, y, z);
				if (te != null)
				{
					te.updateLampFuel();
				}
				
				if (helditem != null)
				{
					FluidStack fsitem = FluidContainerRegistry.getFluidForFilledItem(helditem);
					
					if (fsitem != null)
					{
						FluidContainerRegistry.drainFluidContainer(helditem);
						te.setFuelFromStack(new FluidStack(fsitem.getFluid(), 1000));			
					}
				}
				else
					world.setBlockMetadataWithNotify(x, y, z, meta+8, 3);
			}
		}
		return true;
	}

	public static boolean isLampLit(int meta)
	{
		if((meta & 8) > 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TETFOilLamp();
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
	  
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	  
    public boolean isOpaqueCube()
    {
        return false;
    }
    
	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TechnoItems.OilLanternRenderID;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < 8)
		{
			TETFOilLamp te = (TETFOilLamp) world.getTileEntity(x, y, z);
			if (te != null)
			{
				te.updateLampFuel();
				if(te.getFuelTimeLeft() == 0)
					world.setBlockMetadataWithNotify(x, y, z, meta+8, 3);
			}
		}
	}

	@Override
	public int tickRate(World world)
	{
		return 20;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		this.func_150109_e(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,  EntityLivingBase entity, ItemStack is) 
	{
		TileEntity _t =  world.getTileEntity(x, y, z);
		if(_t != null && _t instanceof TETFOilLamp)
		{
			((TETFOilLamp)_t).create();
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.getTagCompound());
			if(fs != null)
			{
				((TETFOilLamp)_t).setFuelFromStack(fs);
			}
			else
			{
				//world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z)+8, 0x3);
			}
			((TETFOilLamp)_t).hourPlaced = (int)TFC_Time.getTotalHours();

		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!World.doesBlockHaveSolidTopSurface(world, x, y-1, z))
			TFC_Core.setBlockToAirWithDrops(world, x, y, z);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	protected boolean func_150109_e(World world, int x, int y, int z)
	{
		if (!this.canPlaceBlockAt(world, x, y, z))
		{
			if (world.getBlock(x, y, z) == this)
			{
				//this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				world.setBlockToAir(x, y, z);
			}

			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) 
	{
		if (!world.isRemote)
		{
			TETFOilLamp te = (TETFOilLamp)world.getTileEntity(x, y, z);
			if((meta & 8) != 0)
				meta -=8;
			if (te != null)
			{
				te.updateLampFuel();
				
				if(te.getFuel() != null)
				{
					ItemStack is = new ItemStack(this, 1, meta);
					NBTTagCompound nbt = te.getFuel().writeToNBT(new NBTTagCompound());
					is.setTagCompound(nbt);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
				else
				{
					ItemStack is = new ItemStack(this, 1, meta);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec)
	{
		this.setBlockBounds(0.25F, 0.0F, 0.25f, 0.75f, 0.5F, 0.75f);
		return super.collisionRayTrace(world, x, y, z, startVec, endVec);
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        int meta = world.getBlockMetadata(x, y, z);
        double centerX = x + 0.5;
        double centerY = y + 0.5;
        double centerZ = z + 0.5;
	    
        if (isLampLit(meta))
            world.spawnParticle("flame", centerX, centerY, centerZ, 0.0D, 0.0D, 0.0D);
    }

}
