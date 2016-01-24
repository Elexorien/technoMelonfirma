package technofirma.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MagicAnvilManager
{
	private static final MagicAnvilManager instance = new MagicAnvilManager();
	public static final MagicAnvilManager getInstance()
	{
		return instance;
	}

	private List<MagicAnvilRecipe> recipes;
	private List<MagicAnvilRecipe> recipesWeld;
	private Map<String, MagicPlanRecipe> plans;

	private MagicAnvilManager()
	{
		recipes = new ArrayList<MagicAnvilRecipe>();
		recipesWeld = new ArrayList<MagicAnvilRecipe>();
		plans = new HashMap<String, MagicPlanRecipe>();
	}

	public void addRecipe(MagicAnvilRecipe recipe)
	{
		recipes.add(recipe);
	}

	public void addWeldRecipe(MagicAnvilRecipe recipe)
	{
		recipe.flux = true;
		recipesWeld.add(recipe);
	}

	public void clearRecipes()
	{
		recipes.clear();
		recipesWeld.clear();
		plans.clear();
	}

	/**
	 * Adds a name for a plan type to the plans list. If it already exists then it will not be added. All types are not case sensative as it
	 * autoconverts to lowercase when adding to prevent bugs due to case.
	 */
	public void addPlan(String s, MagicPlanRecipe r)
	{
		s = s.toLowerCase();
		if(!plans.containsKey(s))
			plans.put(s, r);
	}

	public MagicPlanRecipe getPlan(String s)
	{
		return (MagicPlanRecipe) plans.get(s);
	}

	public MagicAnvilRecipe findMatchingRecipe(MagicAnvilRecipe recipe)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			MagicAnvilRecipe irecipe = (MagicAnvilRecipe)recipes.get(k);
			if (irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public MagicAnvilRecipe findMatchingWeldRecipe(MagicAnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			MagicAnvilRecipe irecipe = (MagicAnvilRecipe)recipesWeld.get(k);
			if (irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public Object[] findCompleteRecipe(MagicAnvilRecipe recipe, int[] rules)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			MagicAnvilRecipe irecipe = (MagicAnvilRecipe)recipes.get(k);
			if (irecipe.isComplete(instance, recipe, rules))
				return new Object[] {irecipe, irecipe.getCraftingResult(recipe.input1)};
		}

		return null;
	}

	public ItemStack findCompleteWeldRecipe(MagicAnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			MagicAnvilRecipe irecipe = (MagicAnvilRecipe)recipesWeld.get(k);
			if (irecipe.matches(recipe))
				return irecipe.getCraftingResult(recipe.input1);
		}

		return null;
	}

	public List<MagicAnvilRecipe> getRecipeList()
	{
		return recipes;
	}

	public List<MagicAnvilRecipe> getWeldRecipeList()
	{
		return recipesWeld;
	}

	public Map<String, MagicPlanRecipe> getPlans()
	{
		return plans;
	}

	public static NBTTagCompound getCraftTag(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("craftingTag"))
		{
			return (NBTTagCompound) is.getTagCompound().getTag("craftingTag");
		}
		else
			return new NBTTagCompound();
	}

	public static void setCraftTag(ItemStack is, NBTTagCompound nbt)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setTag("craftingTag", nbt);
	}

	public static float getDurabilityBuff(ItemStack is)
	{
		NBTTagCompound nbt = getCraftTag(is);
		return nbt.getFloat("durabuff");
	}

	public static void setDurabilityBuff(ItemStack is, float value)
	{
		NBTTagCompound nbt = getCraftTag(is);
		nbt.setFloat("durabuff", value);
		setCraftTag(is, nbt);
	}

	public static float getDamageBuff(ItemStack is)
	{
		NBTTagCompound nbt = getCraftTag(is);
		return nbt.getFloat("damagebuff");
	}

	public static void setDamageBuff(ItemStack is, float value)
	{
		NBTTagCompound nbt = getCraftTag(is);
		nbt.setFloat("damagebuff", value);
		setCraftTag(is, nbt);
	}
}
