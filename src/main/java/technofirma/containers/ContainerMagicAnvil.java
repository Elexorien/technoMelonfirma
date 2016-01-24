package technofirma.containers;

import technofirma.containers.slots.SlotAnvilWand;
import technofirma.tileentities.TEMagicAnvil;
import thaumcraft.api.ItemApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotAnvilFlux;
import com.bioxx.tfc.Containers.Slots.SlotAnvilHammer;
import com.bioxx.tfc.Containers.Slots.SlotAnvilIn;
import com.bioxx.tfc.Containers.Slots.SlotAnvilWeldOut;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCItems;

public class ContainerMagicAnvil extends ContainerTFC
{
	private TEMagicAnvil anvil;
	private int greenIndicator;
	private int redIndicator;

	public ContainerMagicAnvil(InventoryPlayer inventoryplayer, TEMagicAnvil anvil, World world, int x, int y, int z)
	{
		this.anvil = anvil;

		redIndicator = -1000;
		greenIndicator = -1000;
		
		//Hammer slot
		addSlotToContainer(new SlotAnvilHammer(inventoryplayer.player, anvil, 0, 7, 95));
		//input item slot
		addSlotToContainer(new SlotAnvilIn(anvil, 1, 87, 46));

		//Weld slots
		addSlotToContainer(new SlotAnvilIn(anvil,  2, 14, 12));
		addSlotToContainer(new SlotAnvilIn(anvil,  3, 32, 12));
		addSlotToContainer(new SlotAnvilWeldOut(anvil, 4, 23, 34));
		//blueprint slot
		addSlotToContainer(new SlotAnvilIn(anvil, 5, 105, 46));
		//flux slot
		addSlotToContainer(new SlotAnvilFlux(anvil, 6, 185, 95));
		
		//wand slot
		addSlotToContainer(new SlotAnvilWand(inventoryplayer.player, anvil, 7, 38, 85));
		//plans
		/*addSlotToContainer(new SlotAnvilPlan(anvil, 7, 149, 7));
		addSlotToContainer(new SlotAnvilPlan(anvil, 8, 167, 7));
		addSlotToContainer(new SlotAnvilPlan(anvil, 9, 185, 7));
		addSlotToContainer(new SlotAnvilPlan(anvil, 10, 149, 25));
		addSlotToContainer(new SlotAnvilPlan(anvil, 11, 167, 25));
		addSlotToContainer(new SlotAnvilPlan(anvil, 12, 185, 25));
		addSlotToContainer(new SlotAnvilPlan(anvil, 13, 149, 43));
		addSlotToContainer(new SlotAnvilPlan(anvil, 14, 167, 43));
		addSlotToContainer(new SlotAnvilPlan(anvil, 15, 185, 43));
		addSlotToContainer(new SlotAnvilPlan(anvil, 16, 149, 61));
		addSlotToContainer(new SlotAnvilPlan(anvil, 17, 167, 61));
		addSlotToContainer(new SlotAnvilPlan(anvil, 18, 185, 61));*/


		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 24, 122, false, true);

	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer entityplayer, int i)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slothammer = (Slot)inventorySlots.get(0);
		Slot slotwand = (Slot)inventorySlots.get(7);
		Slot[] slotinput = {(Slot)inventorySlots.get(1), (Slot)inventorySlots.get(2), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(5)};
		//Slot slotflux = (Slot)inventorySlots.get(6);
		ItemStack thaumcraftis = ItemApi.getItem("itemWandCasting", 0);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			if(i <= 7)
			{
				if(!this.mergeItemStack(slotStack, 7, inventorySlots.size(), false))
					return null;
			}
			else if(slotStack.getItem() == TFCItems.powder && slotStack.getItemDamage() == 0)
			{
				if (!this.mergeItemStack(slotStack, 6, 7, false))
					return null;
			}
			else if(slotStack.getItem() instanceof ItemHammer)
			{
				if(slothammer.getHasStack())
					return null;
				ItemStack stack = slotStack.copy();
				stack.stackSize = 1;
				slothammer.putStack(stack);
				slotStack.stackSize--;
			}
			else if(slotStack.getItem() == thaumcraftis.getItem())
			{
				if(slotwand.getHasStack())
					return null;
				ItemStack stack = slotStack.copy();
				stack.stackSize = 1;
				slotwand.putStack(stack);
				slotStack.stackSize--;
			}
			else
			{
				int j = 0;
				while(j < slotinput.length)
				{
					if(slotinput[j].getHasStack())
						j++;
					else
					{
						ItemStack stack = slotStack.copy();
						stack.stackSize = 1;
						slotinput[j].putStack(stack);
						slotStack.stackSize--;
						break;
					}
				}
			}
			if(slotStack.stackSize <= 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}
		return origStack;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			int cv = anvil.getCraftingValue();
			int icv = anvil.getItemCraftingValueNoSet(1);

			if (this.redIndicator != cv)
				var2.sendProgressBarUpdate(this, 0, cv);
			if (this.greenIndicator != icv)
				var2.sendProgressBarUpdate(this, 1, icv);
		}

		redIndicator = anvil.craftingValue;
		greenIndicator = anvil.itemCraftingValue;
	}

	/**
	 * This is needed to make sure that something is done when 
	 * the client receives the updated progress bar
	 * */
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if(anvil != null)
		{
			if (par1 == 0)
				this.anvil.craftingValue = par2;
			else if (par1 == 1)
				this.anvil.itemCraftingValue = par2;
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		anvil.closeInventory();
	}
}
