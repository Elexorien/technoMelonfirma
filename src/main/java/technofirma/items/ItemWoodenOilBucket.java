package technofirma.items;

import buildcraft.BuildCraftEnergy;
import buildcraft.energy.ItemBucketBuildcraft;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import technofirma.core.Reference;

import java.util.List;

public class ItemWoodenOilBucket extends ItemBucket implements ISize
{
    private final ItemStack container = new ItemStack(TFCItems.woodenBucketEmpty);

    public ItemWoodenOilBucket(Block block)
    {
        super(block);
    }

    //public Item setUnlocalizedName(String name)
    //{
    //    return super.setUnlocalizedName(name);
    //}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(Reference.MOD_ID + "WoodenOilBucket");
    }

    //public ItemStack getContainerItem(ItemStack stack)
    //{
    //    return this.container.copy();
    //}

    //public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    //{
    //    return stack;
    //}

    @Override
    public EnumSize getSize(ItemStack is)
    {
        return EnumSize.MEDIUM;
    }

    @Override
    public EnumWeight getWeight(ItemStack is)
    {
        return EnumWeight.LIGHT;
    }

    @Override
    public EnumItemReach getReach(ItemStack is)
    {
        return EnumItemReach.SHORT;
    }

    @Override
    public boolean canStack()
    {
        return false;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
    {
        ItemTerra.addSizeInformation(is, arraylist);
    }
}
