package technofirma.containers.slots;

import thaumcraft.api.ItemApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAnvilWand extends Slot
{
	public SlotAnvilWand(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		ItemStack thaumcraftis = ItemApi.getItem("itemWandCasting", 0);
		
		if(itemstack.getItem() == thaumcraftis.getItem()) {
			return true;
		} else {
			return false;
		}
	}
}
