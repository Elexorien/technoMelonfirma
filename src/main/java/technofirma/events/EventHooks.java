package technofirma.events;

import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import sladki.tfc.ModManager;
import technofirma.core.TechnofirmaCore;
import sladki.tfc.Items.Tools.ItemIceSaw;


public class EventHooks
{
	public boolean IsALeafBlock(Block block)
	{
		return TechnofirmaCore.GetThaumcraftBlock("blockMagicalLeaves") == block;
	}

	public boolean IsAnIceBlock(Block block)
	{
		return TFCBlocks.ice == block || ModManager.IceBlock == block;
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		Block block = event.block;
		World world = event.world;
        ItemStack playerItem = event.getPlayer().getCurrentEquippedItem();
		int x = event.x;
		int y = event.y;
		int z = event.z;
		if(IsALeafBlock(block))
		{
			if(world.rand.nextInt(100) < 14)
				world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(TFCItems.stick, 1)));
		}
		if(IsAnIceBlock(block))
		{
			ItemStack itemstack = playerItem;
			if(!world.isRemote && itemstack != null && !(itemstack.getItem() instanceof ItemIceSaw))
				event.setCanceled(true);
		}
	}

}
