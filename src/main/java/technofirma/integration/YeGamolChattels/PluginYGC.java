package technofirma.integration.YeGamolChattels;

import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.farming.Farmables;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.core.config.Constants;
import forestry.core.config.GameMode;
import forestry.core.fluids.Fluids;
import forestry.core.utils.ModUtil;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import technofirma.integration.Forestry.FarmableBasicTFC;

@Plugin(pluginID = "YGC", name = "YGC", author = "Elexorien", url = Constants.URL, unlocalizedDescription = "for.plugin.ygc.description")
public class PluginYGC extends ForestryPlugin
{

    private static final String YGC = "yegamolchattels";

    @Override
    public boolean isAvailable() {
        return ModUtil.isModLoaded(YGC);
    }

    @Override
    public String getFailMessage() {
        return "YGC not found";
    }

    protected void registerRecipes() {

        int seedamount = GameMode.getGameMode().getIntegerSetting("squeezer.liquid.seed");

        ItemStack flaxSeed = GameRegistry.findItemStack(YGC, "flax_seeds", 1);

        if (flaxSeed != null) {
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{flaxSeed}, Fluids.SEEDOIL.getFluid(seedamount));
            BackpackManager.backpackItems[2].add(flaxSeed);
        }

        Block flaxBlock = GameRegistry.findBlock(YGC, "flax_plant");
        if (flaxBlock != null) {
            Farmables.farmables.get("farmOrchard").add(new FarmableBasicYGC(flaxBlock, 0));
        }
    }
}
