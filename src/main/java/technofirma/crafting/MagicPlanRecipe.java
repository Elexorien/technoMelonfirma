package technofirma.crafting;

import thaumcraft.api.aspects.AspectList;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.api.Enums.RuleEnum;

public class MagicPlanRecipe 
{
	public RuleEnum[] rules;
	public AspectList aspects;
	public String research;
	public IIcon icon;

	public MagicPlanRecipe(RuleEnum[] r, IIcon i)
	{
		rules = r;
		icon = i;
	}

	public MagicPlanRecipe(String re, AspectList a, RuleEnum[] r)
	{
		research = re;
		aspects = a;
		rules = r;
	}
}
