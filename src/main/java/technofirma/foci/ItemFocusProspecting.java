package technofirma.foci;

import java.util.HashMap;
import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;

public class ItemFocusProspecting extends ItemFocusBasic
{
	private class ProspectResult
	{
		public ItemStack ItemStack;
		public int Count;

		public ProspectResult(ItemStack itemStack, int count)
		{
			ItemStack = itemStack;
			Count = count;
		}
	}
	
	HashMap<String, ProspectResult> results = new HashMap<String, ProspectResult>();
	Random random = null;
	
	public IIcon DepthLayerIcon;
	
	public ItemFocusProspecting()
    {
        super();
    }
		
	/**
	 * How much vis does this focus consume per activation. 
	 */
	@Override
	public AspectList getVisCost(ItemStack focusstack) 
	{
		AspectList aspect = new AspectList();
		aspect.add(Aspect.EARTH, 1);
		
		return aspect;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack focusstack)
    {
        return EnumRarity.uncommon;
    }	
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegisterer)
    {
		icon = iconRegisterer.registerIcon("thaumcraft:focus_pech");
		DepthLayerIcon = iconRegisterer.registerIcon("thaumcraft:focus_pech_depth");
    }
	
	/**
	 * An icon to be rendered inside the focus itself
	 */
	public IIcon getFocusDepthLayerIcon(ItemStack focusstack)
	{
		// TODO Auto-generated method stub
		return DepthLayerIcon;
	}
	
	@Override
	public ItemStack onFocusRightClick(ItemStack wandstack, World world,EntityPlayer player, MovingObjectPosition movingobjectposition) 
	{		
		// None simulated version of consume to check if we can do the focus's action
		boolean canpreform = ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, getVisCost(null), false, false);
		
		if (!canpreform || movingobjectposition == null || movingobjectposition.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
			return wandstack;
		
		Block block = world.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
		if (!world.isRemote)
		{
			// Getting the meta data only when we actually need it.
			int meta = world.getBlockMetadata(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);

			// If an ore block is targeted directly, it'll tell you what it is.
			if (block == TechnoItems.DarkOre)
			{
				ItemStack thaumcraftitem = ItemApi.getBlock("blockCustomOre", meta + 1);
				if (thaumcraftitem != null)
					TellResult(player, thaumcraftitem);
				return wandstack;
			}

			random = new Random(movingobjectposition.blockX * movingobjectposition.blockZ + movingobjectposition.blockY);

			SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_PROSPECTING);
			int chance = 60 + ((rank.ordinal()+1)*10);

			results.clear();
			// If random(100) is less than 60, it used to succeed. we don't need to
			// gather the blocks in a 25x25 area if it doesn't.
			if (random.nextInt(100) >= chance && rank != SkillRank.Master)
			{
				TellNothingFound(player);
				return wandstack;
			}

			results.clear();
			
			// Check all blocks in the 25x25 area, centered on the targeted block.
			for (int i = -12; i < 12; i++)
			{
				for (int j = -12; j < 12; j++)
				{
					for(int k = -12; k < 12; k++)
					{
						int blockX = movingobjectposition.blockX + i;
						int blockY = movingobjectposition.blockY + j;
						int blockZ = movingobjectposition.blockZ + k;

						block = world.getBlock(blockX, blockY, blockZ);
						meta = world.getBlockMetadata(blockX, blockY, blockZ);
						ItemStack ore;
						if (block == TechnoItems.DarkOre)
						{
							ItemStack thaumcraftitem = ItemApi.getBlock("blockCustomOre", meta + 1);
							if (thaumcraftitem != null)
								ore = thaumcraftitem;
							else
								continue;
						}
						else
							continue;

						String oreName = ore.getDisplayName();

						if (results.containsKey(oreName))
							results.get(oreName).Count++;
						else
							results.put(oreName, new ProspectResult(ore, 1));

						ore = null;
						oreName = null;
					}
				}
			}
			
			// Tell the player what was found.
			if (results.isEmpty()) {
				TellNothingFound(player);
			} else {
				TellResult(player);
			}

			results.clear();
			random = null;
			
			// Consume our vis in our wand we are holding
			ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, getVisCost(null), true, false);
		}

		return wandstack;
	}
	
	/*
	 * Tells the player nothing was found.
	 */
	private void TellNothingFound(EntityPlayer player)
	{
		player.addChatMessage(new ChatComponentTranslation("gui.ProPick.FoundNothing"));
	}
	
	/*
	 * Tells the player what block of ore he found, when directly targeting an ore block.
	 */
	private void TellResult(EntityPlayer player, ItemStack ore)
	{
		String oreName = ore.getUnlocalizedName() + ".name";
		player.addChatMessage(
				new ChatComponentTranslation("gui.ProPick.Found")
				.appendText(" ")
				.appendSibling(new ChatComponentTranslation(oreName)));

	}
	
	/*
	 * Tells the player what ore has been found, randomly picked off the HashMap.
	 */
	private void TellResult(EntityPlayer player)
	{
		TFC_Core.getSkillStats(player).increaseSkill(Global.SKILL_PROSPECTING, 1);
		int index = random.nextInt(results.size());
		ProspectResult result = results.values().toArray(new ProspectResult[0])[index];
		String oreName = result.ItemStack.getUnlocalizedName() + ".name";

		String quantityMsg;
		if (result.Count < 10)
			quantityMsg = "gui.ProPick.FoundTraces";
		else if(result.Count < 20)
			quantityMsg = "gui.ProPick.FoundSmall";
		else if (result.Count < 40)
			quantityMsg = "gui.ProPick.FoundMedium";
		else if (result.Count < 80)
			quantityMsg = "gui.ProPick.FoundLarge";
		else
			quantityMsg = "gui.ProPick.FoundVeryLarge";
		
		player.addChatMessage(
				new ChatComponentTranslation(quantityMsg)
				.appendText(" ")
				.appendSibling(new ChatComponentTranslation(oreName)));
				
		oreName = null;
		result = null;
	}
}
