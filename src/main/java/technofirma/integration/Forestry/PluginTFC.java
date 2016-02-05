package technofirma.integration.Forestry;

import com.bioxx.tfc.api.TFCOptions;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.farming.Farmables;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.core.config.Constants;
import forestry.core.config.GameMode;
import forestry.core.fluids.Fluids;
import forestry.core.recipes.RecipeUtil;
import forestry.core.utils.ModUtil;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@Plugin(pluginID = "TFC", name = "TFC", author = "Elexorien", url = Constants.URL, unlocalizedDescription = "for.plugin.tfc.description")
public class PluginTFC extends ForestryPlugin
    {
        private static final String TFC = "terrafirmacraft";

        @Override
        public boolean isAvailable() {
            return ModUtil.isModLoaded(TFC);
        }

        @Override
        public String getFailMessage() {
            return "TFC not found";
        }

        protected void registerRecipes() {

            int saplingYield = GameMode.getGameMode().getIntegerSetting("fermenter.yield.sapling");
            int juiceAmount = GameMode.getGameMode().getIntegerSetting("squeezer.liquid.apple");
            int seedamount = GameMode.getGameMode().getIntegerSetting("squeezer.liquid.seed");

            ItemStack barleySeed = GameRegistry.findItemStack(TFC, "Seeds Barley", 1);
            ItemStack oatSeed = GameRegistry.findItemStack(TFC, "Seeds Oat", 1);
            ItemStack ryeSeed = GameRegistry.findItemStack(TFC, "Seeds Rye", 1);
            ItemStack wheatSeed = GameRegistry.findItemStack(TFC, "Seeds Wheat", 1);
            ItemStack maizeSeed = GameRegistry.findItemStack(TFC, "Seeds Maize", 1);
            ItemStack tomatoSeed = GameRegistry.findItemStack(TFC, "Seeds Tomato", 1);

            ItemStack riceSeed = GameRegistry.findItemStack(TFC, "Seeds Rice", 1);
            ItemStack cabbageSeed = GameRegistry.findItemStack(TFC, "Seeds Cabbage", 1);
            ItemStack greenbeanSeed = GameRegistry.findItemStack(TFC, "Seeds Greenbean", 1);
            ItemStack onionSeed = GameRegistry.findItemStack(TFC, TFCOptions.onionsAreGross?"Seeds Rutabaga":"Seeds Onion", 1);
            ItemStack soybeanSeed = GameRegistry.findItemStack(TFC, "Seeds Soybean", 1);
            ItemStack juteSeed = GameRegistry.findItemStack(TFC, "Seeds Jute", 1);

            ItemStack rbpepperSeed = GameRegistry.findItemStack(TFC, "Seeds Red Bell Pepper", 1);
            ItemStack ybpepperSeed = GameRegistry.findItemStack(TFC, "Seeds Yellow Bell Pepper", 1);
            ItemStack carrotSeed = GameRegistry.findItemStack(TFC, "Seeds Carrot", 1);
            ItemStack garlicSeed = GameRegistry.findItemStack(TFC, "Seeds Garlic", 1);
            ItemStack potatoSeed = GameRegistry.findItemStack(TFC, "Seeds Potato", 1);
            ItemStack squashSeed = GameRegistry.findItemStack(TFC, "Seeds Squash", 1);

            ItemStack sugarcaneSeed = GameRegistry.findItemStack(TFC, "Seeds Sugarcane", 1);

            if (barleySeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{barleySeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(barleySeed);
            }
            if (oatSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{oatSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(oatSeed);
            }
            if (ryeSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{ryeSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(ryeSeed);
            }
            if (wheatSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{wheatSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(wheatSeed);
            }
            if (maizeSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{maizeSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(maizeSeed);
            }
            if (tomatoSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{tomatoSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(tomatoSeed);
            }
            if (riceSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{riceSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(riceSeed);
            }
            if (cabbageSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{cabbageSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(cabbageSeed);
            }
            if (greenbeanSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{greenbeanSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(greenbeanSeed);
            }
            if (onionSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{onionSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(onionSeed);
            }
            if (soybeanSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{soybeanSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(soybeanSeed);
            }
            if (juteSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{juteSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(juteSeed);
            }
            if (rbpepperSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{rbpepperSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(rbpepperSeed);
            }
            if (ybpepperSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{ybpepperSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(ybpepperSeed);
            }
            if (carrotSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{carrotSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(carrotSeed);
            }
            if (garlicSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{garlicSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(garlicSeed);
            }
            if (potatoSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{potatoSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(potatoSeed);
            }
            if (squashSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{squashSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(squashSeed);
            }
            if (sugarcaneSeed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{sugarcaneSeed}, Fluids.SEEDOIL.getFluid(seedamount));
                BackpackManager.backpackItems[2].add(sugarcaneSeed);
            }

            ItemStack reeds = GameRegistry.findItemStack(TFC, "Reeds", 1);
            ItemStack jute = GameRegistry.findItemStack(TFC, "Jute", 1);
            ItemStack straw = GameRegistry.findItemStack(TFC, "Straw", 1);

            if (reeds != null) {
                RecipeUtil.addFermenterRecipes(reeds, saplingYield, Fluids.BIOMASS);
                BackpackManager.backpackItems[2].add(reeds);
            }
            if (jute != null) {
                RecipeUtil.addFermenterRecipes(jute, saplingYield, Fluids.BIOMASS);
                BackpackManager.backpackItems[2].add(jute);
            }
            if (straw != null) {
                RecipeUtil.addFermenterRecipes(straw, saplingYield, Fluids.BIOMASS);
                BackpackManager.backpackItems[2].add(straw);
            }

            ItemStack appleRed = GameRegistry.findItemStack(TFC, "Red Apple", 1);
            ItemStack appleGreen = GameRegistry.findItemStack(TFC, "Green Apple", 1);
            ItemStack banana = GameRegistry.findItemStack(TFC, "Banana", 1);
            ItemStack plum = GameRegistry.findItemStack(TFC, "Plum", 1);
            ItemStack orange = GameRegistry.findItemStack(TFC, "Orange", 1);
            ItemStack cherry = GameRegistry.findItemStack(TFC, "Cherry", 1);
            ItemStack lemon = GameRegistry.findItemStack(TFC, "Lemon", 1);
            ItemStack peach = GameRegistry.findItemStack(TFC, "Peach", 1);
            ItemStack wintergreen = GameRegistry.findItemStack(TFC, "Wintergreen Berry", 1);
            ItemStack blueberries = GameRegistry.findItemStack(TFC, "Blueberry", 1);
            ItemStack raspberries = GameRegistry.findItemStack(TFC, "Raspberry", 1);
            ItemStack strawberries = GameRegistry.findItemStack(TFC, "Strawberry", 1);
            ItemStack blackberries = GameRegistry.findItemStack(TFC, "Blackberry", 1);
            ItemStack bunchberries = GameRegistry.findItemStack(TFC, "Bunchberry", 1);
            ItemStack cranberries = GameRegistry.findItemStack(TFC, "Cranberry", 1);
            ItemStack snowberries = GameRegistry.findItemStack(TFC, "Snowberry", 1);
            ItemStack elderberries = GameRegistry.findItemStack(TFC, "Elderberry", 1);
            ItemStack gooseberries = GameRegistry.findItemStack(TFC, "Gooseberry", 1);
            ItemStack cloudberries = GameRegistry.findItemStack(TFC, "Cloudberry", 1);

            if (appleRed != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{appleRed}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(appleRed);
            }
            if (appleGreen != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{appleGreen}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(appleGreen);
            }
            if (banana != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{banana}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(banana);
            }
            if (plum != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{plum}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(plum);
            }
            if (orange != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{orange}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(orange);
            }
            if (cherry != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{cherry}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(cherry);
            }
            if (lemon != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{lemon}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(lemon);
            }
            if (peach != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{peach}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(peach);
            }
            if (wintergreen != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{wintergreen}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(wintergreen);
            }
            if (blueberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{blueberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(blueberries);
            }
            if (raspberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{raspberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(raspberries);
            }
            if (strawberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{strawberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(strawberries);
            }
            if (blackberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{blackberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(blackberries);
            }
            if (bunchberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{bunchberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(bunchberries);
            }
            if (cranberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{cranberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(cranberries);
            }
            if (snowberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{snowberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(snowberries);
            }
            if (elderberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{elderberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(elderberries);
            }
            if (gooseberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{gooseberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(gooseberries);
            }
            if (cloudberries != null) {
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{cloudberries}, Fluids.JUICE.getFluid(juiceAmount));
                BackpackManager.backpackItems[2].add(cloudberries);
            }

            Block berryBlock = GameRegistry.findBlock(TFC, "BerryBush");
            if (berryBlock != null) {
                Farmables.farmables.get("farmOrchard").add(new FarmableBasicTFC(berryBlock, 0));
            }
            Block sapling = GameRegistry.findBlock(TFC, "sapling");
            if (sapling != null) {
                Farmables.farmables.get("farmOrchard").add(new FarmableBasicTFC(sapling, 0));
            }
            Block FruitSapling = GameRegistry.findBlock(TFC, "grc.riceBlock");
            if (FruitSapling != null) {
                Farmables.farmables.get("farmOrchard").add(new FarmableBasicTFC(FruitSapling, 0));
            }
        }
    }