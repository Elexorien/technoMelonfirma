package technofirma.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import technofirma.core.Global;
import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import technofirma.items.ItemBlocks.ItemBlockDarkOre;
import thaumcraft.api.ItemApi;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDarkOre extends BlockOre
{
	protected IIcon ThaumcraftShard;

	public BlockDarkOre(Material mat)
	{
		super(mat);
		//blockNames = TechnofirmaCore.DARKORENAMES;
        this.setCreativeTab(null);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 1 + random.nextInt(2);
	}
	
	@Override
	public int getRenderType()
	{
		return TechnoItems.DarkOreRenderID;
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);

			if(player != null)
			{
				TFC_Core.addPlayerExhaustion(player, 0.001f);
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			}

			ItemStack itemstack = null;

			ItemStack thaumcraftitem = ItemApi.getItem("itemShard", meta);
			if (thaumcraftitem != null)
				itemstack = new ItemStack(thaumcraftitem.getItem(), 1 + world.rand.nextInt(2), meta);

			if (itemstack != null)
				dropBlockAsItem(world, x, y, z, itemstack);
		}
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack = null;

			ItemStack thaumcraftitem = ItemApi.getItem("itemShard", metadata);
			if (thaumcraftitem != null)
				itemstack = new ItemStack(thaumcraftitem.getItem(), 1 + world.rand.nextInt(2), metadata);

			if (itemstack != null)
			{
				ret.add(itemstack);
			}
		}
		return ret;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		ThaumcraftShard = iconRegisterer.registerIcon("thaumcraft:infusedore");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return ThaumcraftShard;
	}
	
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
    	int metadata = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
    	
    	if (metadata >= 0 && metadata <= Global.DARKORECOLORS.length)
    		return Global.DARKORECOLORS[metadata];
    	
        return 16777215;
    }

}
