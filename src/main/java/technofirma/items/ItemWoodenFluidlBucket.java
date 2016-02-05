package technofirma.items;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.TFCItems;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import java.util.List;

public class ItemWoodenFluidlBucket extends ItemBucket implements ISize
{
    private final ItemStack container = new ItemStack(TFCItems.woodenBucketEmpty);

    public String Icon;

    public ItemWoodenFluidlBucket(Block block)
    {
        super(block);
    }

    //public Item setUnlocalizedName(String name)
    //{
    //    return super.setUnlocalizedName(name);
    //}

    public Item SetIconTexture(String icon)
    {
        Icon = icon;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(Icon);
    }

    //public ItemStack getContainerItem(ItemStack stack)
    //{
    //    return this.container.copy();
    //}

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, player, true);
        FillBucketEvent event = new FillBucketEvent(player, stack, world, target);
        event.setResult(Event.Result.ALLOW);
        return stack;
    }

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
