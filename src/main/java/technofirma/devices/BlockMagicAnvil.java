package technofirma.devices;

import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import technofirma.tileentities.TEMagicAnvil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagicAnvil  extends BlockTerraContainer
{
	IIcon AnvilIconTop;
	IIcon AnvilIconSide;
	
	public BlockMagicAnvil()
	{
		super(Material.iron);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
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
			if((TEMagicAnvil)world.getTileEntity(i, j, k)!=null)
			{
				/*TEAnvil TEAnvil;
				TEAnvil = (TEAnvil)world.getTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();*/
				entityplayer.openGui(TechnofirmaCore.instance, 0, world, i, j, k);
			}
			return true;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		
		if(meta == 0) 
			return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
		else
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 0)
		{
			this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
			return AxisAlignedBB.getBoundingBox(x + 0.2, (double)y + 0, (double)z + 0, x + 0.8, y + 0.6, (double)z + 1);
		}
		else
		{
			this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
			return AxisAlignedBB.getBoundingBox((double)x + 0, (double)y + 0, z + 0.2, (double)x + 1, y + 0.6, z + 0.8);
		}
	}
	
	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		
		if(meta == 0)
			this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
		else
			this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		AnvilIconTop = registerer.registerIcon("thaumcraft:pedestal_top");
		AnvilIconSide = registerer.registerIcon("thaumcraft:pedestal_top");
	}
	
	@Override
	public IIcon getIcon(int i, int j)
	{
		if(i == 0 || i == 1)
			return AnvilIconTop;
		else
			return AnvilIconSide;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public int getRenderType()
	{
		return TechnoItems.DarkMagicAnvilRenderID;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
			byte0 = 1;
		if(l == 1)//-x
			byte0 = 0;
		if(l == 2)//-z
			byte0 = 1;
		if(l == 3)//+x
			byte0 = 0;
		byte0 += meta;

		world.setBlockMetadataWithNotify(i, j, k, byte0, 3);
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TEMagicAnvil var5 = (TEMagicAnvil)world.getTileEntity(x, y, z);

		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
					float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = world.rand.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; world.spawnEntityInWorld(var12))
					{
						int var11 = world.rand.nextInt(21) + 10;

						if (var11 > var7.stackSize)
							var11 = var7.stackSize;
						var7.stackSize -= var11;
						var12 = new EntityItem(world, x + var8, y + var9, z + var10, new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)world.rand.nextGaussian() * var13;
						var12.motionY = (float)world.rand.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)world.rand.nextGaussian() * var13;
						if (var7.hasTagCompound())
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEMagicAnvil();
	}
		
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}
}
