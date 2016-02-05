package technofirma.integration.Forestry;

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Blocks.Terrain.BlockFungi;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomReed;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import forestry.api.farming.Farmables;
import forestry.farming.logic.*;
import forestry.plugins.PluginFarming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class FarmingOverrides extends PluginFarming
{
    public static void init()
    {
        Farmables.farmables.put("farmOrchard", new ArrayList());
        ((Collection) Farmables.farmables.get("farmOrchard")).add(new FarmableBasicFruit(BlockCrop.getBlockFromName("wheat"), 8));
        ((Collection) Farmables.farmables.get("farmOrchard")).add(new FarmableBasicFruit(BlockCrop.getBlockFromName("potato"), 7));
        ((Collection) Farmables.farmables.get("farmOrchard")).add(new FarmableBasicFruit(BlockCrop.getBlockFromName("carrot"), 5));
        Farmables.farmables.put("farmShroom", new ArrayList());
        ((Collection) Farmables.farmables.get("farmShroom")).add(new FarmableVanillaShroom(BlockFungi.getBlockFromName("mushroom_brown"), 0));
        ((Collection) Farmables.farmables.get("farmShroom")).add(new FarmableVanillaShroom(BlockFungi.getBlockFromName("mushroom_red"), 0));
        Farmables.farmables.put("farmWheat", new ArrayList());
        ((Collection) Farmables.farmables.get("farmWheat")).add(new FarmableGenericCrop(new ItemStack(TFCItems.seedsWheat), BlockCrop.getBlockFromName("wheat"), 8, new ItemStack[0]));
        //Farmables.farmables.put("farmGourd", new ArrayList());
        //((Collection) Farmables.farmables.get("farmGourd")).add(new FarmableGourd(new ItemStack(Items.pumpkin_seeds), new ItemStack(Blocks.pumpkin_stem), new ItemStack(Blocks.pumpkin)));
        //((Collection) Farmables.farmables.get("farmGourd")).add(new FarmableGourd(new ItemStack(Items.melon_seeds), new ItemStack(Blocks.melon_stem), new ItemStack(Blocks.melon_block)));
        //Farmables.farmables.put("farmInfernal", new ArrayList());
        //((Collection) Farmables.farmables.get("farmInfernal")).add(new FarmableGenericCrop(new ItemStack(Items.nether_wart), Blocks.nether_wart, 3, new ItemStack[0]));
        Farmables.farmables.put("farmPoales", new ArrayList());
        ((Collection) Farmables.farmables.get("farmPoales")).add(new FarmableStacked(TFCBlocks.reeds, 3, 0));
        Farmables.farmables.put("farmSucculentes", new ArrayList());
        ((Collection) Farmables.farmables.get("farmSucculentes")).add(new FarmableStacked(TFCBlocks.cactus, 3, 0));
        Farmables.farmables.put("farmVegetables", new ArrayList());
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.tomato), BlockCrop.getBlockFromName("tomato"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.potato), BlockCrop.getBlockFromName("potato"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.onion), BlockCrop.getBlockFromName("onion"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.cabbage), BlockCrop.getBlockFromName("cabbage"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.garlic), BlockCrop.getBlockFromName("garlic"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.carrot), BlockCrop.getBlockFromName("carrot"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.yellowBellPepper), BlockCrop.getBlockFromName("yellowbellpepper"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.redBellPepper), BlockCrop.getBlockFromName("redbellpepper"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.soybean), BlockCrop.getBlockFromName("soybean"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.greenbeans), BlockCrop.getBlockFromName("greenbean"), 7, new ItemStack[0]));
        ((Collection) Farmables.farmables.get("farmVegetables")).add(new FarmableGenericCrop(new ItemStack(TFCItems.squash), BlockCrop.getBlockFromName("squash"), 7, new ItemStack[0]));
    }
}
