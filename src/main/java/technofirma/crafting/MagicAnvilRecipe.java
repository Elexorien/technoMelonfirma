package technofirma.crafting;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class MagicAnvilRecipe 
{
	ItemStack result;
	String plan = "";
	ItemStack input1;
	ItemStack input2;
	boolean flux;
	int craftingValue;
	boolean inheritsDamage;
	public int craftingXP = 1;
	public ArrayList<String> skillsList = new ArrayList<String>();
	private static int craftingBoundDefault = 50;

	public MagicAnvilRecipe(ItemStack in, ItemStack in2, String p, boolean flux, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 70 + new Random((in != null ? Item.getIdFromItem(in.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(craftingBoundDefault), flux, result);
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack in2, String p, ItemStack result)
	{
		this(in, in2, p.toLowerCase(), 70 + new Random((in != null ? Item.getIdFromItem(in.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(craftingBoundDefault), false, result);
	}

	public MagicAnvilRecipe SetCraftingBound(int max)
	{
		craftingValue = 70 + new Random((input1 != null ? Item.getIdFromItem(input1.getItem()) : 0) + (result != null ? Item.getIdFromItem(result.getItem()) : 0)).nextInt(max);
		return this;
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack in2, String p, int cv, boolean flux, ItemStack result)
	{
		input1 = in;
		input2 = in2;
		this.flux = flux;
		this.craftingValue = cv;
		this.result = result;
		inheritsDamage = false;
		this.plan = p;
		skillsList.add(Global.SKILL_GENERAL_SMITHING);
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack p, boolean flux)
	{
		input1 = in;
		input2 = p;
		this.flux = flux;
		inheritsDamage = false;
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack p, String s, boolean flux)
	{
		this(in, p, flux);
		this.plan = s;
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack p, boolean flux, ItemStack res)
	{
		this(in, p, res);
		this.flux = flux;
	}

	public MagicAnvilRecipe(ItemStack in, ItemStack p, ItemStack res)
	{
		input1 = in;
		input2 = p;
		this.result = res;
		inheritsDamage = false;
	}

	public MagicAnvilRecipe clearRecipeSkills()
	{
		skillsList.clear();
		return this;
	}

	public MagicAnvilRecipe setCraftingXP(int xp)
	{
		this.craftingXP = xp;
		return this;
	}

	public MagicAnvilRecipe setInheritsDamage()
	{
		inheritsDamage = true;
		return this;
	}

	public MagicAnvilRecipe addRecipeSkill(String s)
	{
		this.skillsList.add(s);
		return this;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */    
	public boolean matches(MagicAnvilRecipe A)
	{   
		if(     areItemStacksEqual(input1, A.input1) && 
				areItemStacksEqual(input2, A.input2) &&
				plan.equals(A.plan))
		{
			if(this.flux && !A.flux)
				return false;
			return true;
		}
		return false;
	}

	public boolean isComplete(MagicAnvilManager am, MagicAnvilRecipe A, int[] rules)
	{
		MagicPlanRecipe pr = am.getPlan(A.plan);
	
		if(     areItemStacksEqual(input1, A.input1) && 
				areItemStacksEqual(input2, A.input2) &&
				plan.equals(A.plan) &&
				pr.rules[0].matches(rules, 0) && pr.rules[1].matches(rules, 1) && pr.rules[2].matches(rules, 2) && 
				craftingValue == A.craftingValue)
			if(this.flux && A.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	public boolean isComplete(MagicAnvilRecipe A)
	{
		if(A.input1 == this.input1 && A.input2 == input2 && 
				craftingValue == A.craftingValue && plan.equals(A.plan))
			if(this.flux && A.flux)
				return true;
			else if (!this.flux)
				return true;
		return false;
	}

	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if(is1 == null && is2 == null)
			return true;

		if((is1 == null && is2 != null) || (is1 != null && is2 == null)) 
			return false;

		if(is1.getItem() != is2.getItem())
			return false;

		if(is1.getItemDamage() != 32767 && is1.getItemDamage() != is2.getItemDamage())
			return false;

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult()
	{
		return result;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(ItemStack input)
	{
		ItemStack is = result.copy();
		if(this.inheritsDamage)
			is.setItemDamage(input.getItemDamage());
		return is;
	}



	public int getCraftingValue()
	{
		return craftingValue;
	}

	public float getSkillMult(EntityPlayer p)
	{
		float skill = 0;
		float total = 0;
		for (String s : skillsList)
		{
			total++;
			skill+=TFC_Core.getSkillStats(p).getSkillMultiplier(s);
		}
		if(total > 0)
			return skill/total;
		return 0;
	}

    public String getPlan()
    {
        return plan;
    }

    public ItemStack getInput1()
    {
        return input1;
    }

    public ItemStack getInput2()
    {
        return input2;
    }

    public boolean isFlux()
    {
        return flux;
    }

    public boolean isInheritsDamage()
    {
        return inheritsDamage;
    }

    public int getCraftingXP()
    {
        return craftingXP;
    }

    public ArrayList<String> getSkillsList()
    {
        return skillsList;
    }
}
