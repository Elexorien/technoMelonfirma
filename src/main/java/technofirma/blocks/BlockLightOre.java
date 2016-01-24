package technofirma.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import thaumcraft.api.ItemApi;
import com.bioxx.tfc.Core.TFC_Core;

import com.bioxx.tfc.Blocks.Terrain.BlockOre2;
import technofirma.core.TechnofirmaCore;

public class BlockLightOre extends BlockOre2
{
    public BlockLightOre(Material mat)
    {
        super(mat);
        //blockNames = "Amber";
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 1 + random.nextInt(2);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++)
        {
            ItemStack itemstack = null;

            ItemStack thaumcraftitem = ItemApi.getItem("itemResource", metadata);
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
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            int meta = 6;//world.getBlockMetadata(x, y, z);

            if(player != null)
            {
                TFC_Core.addPlayerExhaustion(player, 0.001f);
                player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
            }

            ItemStack itemstack = null;

            ItemStack thaumcraftitem = ItemApi.getItem("itemResource", meta);
            if (thaumcraftitem != null)
                itemstack = new ItemStack(thaumcraftitem.getItem(), 1 + world.rand.nextInt(2), meta);

            if (itemstack != null)
                dropBlockAsItem(world, x, y, z, itemstack);
        }
        return world.setBlockToAir(x, y, z);
    }




/*
    @Override
    public int getRenderType()
    {
        return TechnofirmaCore.LightOreRenderID;
    }




*/


    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer)
    {
        this.blockIcon = iconRegisterer.registerIcon("technofirma:amber");
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return blockIcon;
    }

}
