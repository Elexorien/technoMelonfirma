package technofirma.NEI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import technofirma.client.gui.GuiMagicAnvil;
import technofirma.crafting.MagicAnvilManager;
import technofirma.crafting.MagicAnvilRecipe;
import thaumcraft.api.ItemApi;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import static technofirma.NEI.MagicAnvilNEIHandler.TYPE.*;


public class MagicAnvilNEIHandler extends TemplateRecipeHandler
{
    private static List<MagicAnvilRecipe> recipeList, weldRecipeList;
    private static ItemStack[] hammers;
    private static ItemStack[] wands;
    
    public static boolean ItemStacksEqualWithOreDic(ItemStack inputStack, ItemStack recipeStack)
    {
        return inputStack == recipeStack || OreDictionary.itemMatches(recipeStack, inputStack, false);
    }
        
    @Override
    public String getGuiTexture()
    {
        return GuiMagicAnvil.texture.toString();
    }

    @Override
    public String getRecipeName()
    {
        return "Thaumic Anvil";
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "thaumicanvil";
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(13 - 21, 53 + 5, 36, 20), "thaumicanvil"));
        transferRects.add(new RecipeTransferRect(new Rectangle(72 - 21, 8 + 5, 64, 36), "thaumicanvil"));
        transferRects.add(new RecipeTransferRect(new Rectangle(69 - 21, 64 + 5, 70, 34), "thaumicanvil"));
        transferRects.add(new RecipeTransferRect(new Rectangle(148 - 21, 6 + 5, 54, 72), "thaumicanvil"));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("thaumicanvil") && getClass() == MagicAnvilNEIHandler.class)
        {
            for (MagicAnvilRecipe recipe : recipeList) arecipes.add(new CachedAnvilRecipe(NORMAL, recipe));
            for (MagicAnvilRecipe recipe : weldRecipeList) arecipes.add(new CachedAnvilRecipe(WELD, recipe));
        }
        else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (MagicAnvilRecipe recipe : recipeList) if (ItemStacksEqualWithOreDic(result, recipe.getCraftingResult(result))) arecipes.add(new CachedAnvilRecipe(NORMAL, recipe));
        for (MagicAnvilRecipe recipe : weldRecipeList) if (ItemStacksEqualWithOreDic(result, recipe.getCraftingResult(result))) arecipes.add(new CachedAnvilRecipe(WELD, recipe));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        if (ingredient.getItem() instanceof ItemHammer)
        {
            for (MagicAnvilRecipe recipe : recipeList) arecipes.add(new CachedAnvilRecipe(HAMMER_NORMAL, recipe));
            for (MagicAnvilRecipe recipe : weldRecipeList) arecipes.add(new CachedAnvilRecipe(HAMMER_WELD, recipe));
        }
        else if (ingredient.getItem() == TFCItems.powder && ingredient.getItemDamage() == 0)
        {
            for (MagicAnvilRecipe recipe : weldRecipeList) arecipes.add(new CachedAnvilRecipe(WELD, recipe));
        }
        else
        {
            for (MagicAnvilRecipe recipe : recipeList)
            {
                if (ItemStacksEqualWithOreDic(ingredient, recipe.getInput1()) || ItemStacksEqualWithOreDic(ingredient, recipe.getInput2()))
                    arecipes.add(new CachedAnvilRecipe(NORMAL, recipe.getCraftingResult(), recipe.getInput1(), recipe.getInput2()));
            }

            for (MagicAnvilRecipe recipe : weldRecipeList)
            {
                if (ItemStacksEqualWithOreDic(ingredient, recipe.getInput1()) || ItemStacksEqualWithOreDic(ingredient, recipe.getInput2()))
                    arecipes.add(new CachedAnvilRecipe(WELD, recipe.getCraftingResult(), recipe.getInput1(), recipe.getInput2()));
            }
        }
    }
    
    @Override
    public TemplateRecipeHandler newInstance()
    {
        if (recipeList == null || weldRecipeList == null || hammers == null || wands == null)
        {
            recipeList = MagicAnvilManager.getInstance().getRecipeList();
            weldRecipeList = MagicAnvilManager.getInstance().getWeldRecipeList();

            hammers = new ItemStack[Recipes.hammers.length];
            for (int i = 0; i < hammers.length; i++)
                hammers[i] = new ItemStack(Recipes.hammers[i]);
            
            wands = new ItemStack[] { ItemApi.getItem("itemWandCasting", 0), ItemApi.getItem("itemWandCasting", 1), ItemApi.getItem("itemWandCasting", 2) };
        }
        return super.newInstance();
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(-21, 5, 0, 0, 208, 198);
    }

    @Override
    public void drawForeground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        changeTexture(getGuiTexture());
        drawExtras(recipe);
    }

    @Override
    public void drawExtras(int recipe)
    {
        super.drawExtras(recipe);
    }

    public static enum TYPE
    {
        NORMAL, WELD, HAMMER_NORMAL, HAMMER_WELD;

        public boolean isWeld()
        {
            return this == WELD || this == HAMMER_NORMAL;
        }
    }

    public class CachedAnvilRecipe extends CachedRecipe
    {
        PositionedStack i1, i2, out;
        TYPE type;
        public String anvilReq;

        public CachedAnvilRecipe(TYPE type, MagicAnvilRecipe recipe)
        {
            this(type, recipe.getCraftingResult(), recipe.getInput1(), recipe.getInput2());
        }

        public CachedAnvilRecipe(TYPE type, ItemStack out, ItemStack i1, ItemStack i2)
        {
            this.type = type;
            if (i1 != null) this.i1 = new PositionedStack(i1, type.isWeld() ? -7 : 66, type.isWeld() ? 17 : 51);
            if (i2 != null) this.i2 = new PositionedStack(i2, type.isWeld() ? 11 : 84, type.isWeld() ? 17 : 51);
            this.out = new PositionedStack(out, type.isWeld() ? 2 : 103, type.isWeld() ? 39 : 51);
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            if (i1 != null)
                stacks.add(i1);
            if (i2 != null)
                stacks.add(i2);
            return stacks;
        }

        @Override
        public PositionedStack getResult()
        {
            return out;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> stacks = new ArrayList<PositionedStack>();

            stacks.add(new PositionedStack(hammers, -14, 100, false));
            stacks.add(new PositionedStack(wands, 17, 90, false));
            if (type.isWeld()) stacks.add(new PositionedStack(new ItemStack(TFCItems.powder), 164, 100));
            return stacks;
        }
    }
}
