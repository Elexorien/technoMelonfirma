package technofirma.integration.Forestry;

import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FarmableBasicTFC implements IFarmable
{
    private final Block block;
    private final int matureMeta;
;

    public FarmableBasicTFC(Block block, int matureMeta)
    {
        this.block = block;
        this.matureMeta = matureMeta;
    }

    @Override
    public boolean isSaplingAt(World world, int i, int i1, int i2)
    {
        return false;
    }

    @Override
    public ICrop getCropAt(World world, int i, int i1, int i2)
    {
        return null;
    }

    @Override
    public boolean isGermling(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean isWindfall(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean plantSaplingAt(EntityPlayer entityPlayer, ItemStack itemStack, World world, int i, int i1, int i2)
    {
        return false;
    }
}
