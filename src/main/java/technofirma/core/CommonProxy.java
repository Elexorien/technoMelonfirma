package technofirma.core;

import forestry.core.fluids.Fluids;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import technofirma.blocks.BlockDarkOre;
import technofirma.blocks.BlockLightOre;
import technofirma.blocks.BlockTFIngotPile;
import technofirma.blocks.BlockTFOilLamp;
import technofirma.crafting.MagicAnvilManager;
import technofirma.crafting.MagicAnvilRecipe;
import technofirma.crafting.MagicPlanRecipe;
import technofirma.devices.BlockMagicAnvil;
import technofirma.events.EventHooks;
import technofirma.events.ThaumcraftTreeFell;
import technofirma.integration.Thaumcraft.foci.ItemFocusProspecting;
import technofirma.handlers.GuiHandler;
import technofirma.items.*;
import technofirma.lamp.LampFluid;
import technofirma.tileentities.TEMagicAnvil;
import technofirma.tileentities.TETFIngotPile;
import technofirma.tileentities.TETFOilLamp;

import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import buildcraft.BuildCraftEnergy;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy 
{
	public void RegisterRenderers() { }
	
	public void RegisterGuiHandler() 
	{ 
		NetworkRegistry.INSTANCE.registerGuiHandler(TechnofirmaCore.instance, new GuiHandler());
	}
	
	public void SetupLampFuels()
	{		
		LampFluid.CreateFluid("oliveoil", 1);
		LampFluid.CreateFluid("oil", 2);
		LampFluid.CreateFluid("fuel", 3);
	}
		
	public void SetupNormalBlocks()
	{
		TechnoItems.OilLamp = new BlockTFOilLamp().setHardness(1F).setBlockName("TFOilLamp");
	}

	public void SetupMagicBlocks()
	{
		TechnoItems.DarkOre = new BlockDarkOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("DarkOre");
		TechnoItems.LightOre = new BlockLightOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Amber");
		TechnoItems.TFIngotPile = new BlockTFIngotPile().setHardness(3F).setBlockName("ingotpile");
		TechnoItems.ThaumicAnvil = new BlockMagicAnvil().setHardness(3).setResistance(100F).setBlockName("Magic Anvil");
	}
	
	public void RegisterNormalBlocks()
	{
		GameRegistry.registerBlock(TechnoItems.OilLamp, ItemTFOilLamp.class, "TFOilLamp");
	}

	public void RegisterMagicBlocks()
	{
		GameRegistry.registerBlock(TechnoItems.DarkOre, "DarkOre");
		GameRegistry.registerBlock(TechnoItems.LightOre, "LightOre");
		GameRegistry.registerBlock(TechnoItems.TFIngotPile, "IngotPile");
		GameRegistry.registerBlock(TechnoItems.ThaumicAnvil, "Magic Anvil");
	}
	
	public void RegisterTE()
	{
		GameRegistry.registerTileEntity(TETFOilLamp.class, "TF Oil Lamp");
	}

    public void SetupBCItems()
    {
        //Block blockOil = BuildCraftEnergy.blockOil;
        //Block blockFuel = BuildCraftEnergy.blockFuel;
        TechnoItems.WoodenOilBucket = new ItemWoodenFluidlBucket(BuildCraftEnergy.blockOil).SetIconTexture("technofirma:WoodenOilBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Oil Bucket");
        TechnoItems.WoodenFuelBucket = new ItemWoodenFluidlBucket(BuildCraftEnergy.blockFuel).SetIconTexture("technofirma:WoodenFuelBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Fuel Bucket");
    }

    public void RegisterBCItems()
    {
        GameRegistry.registerItem(TechnoItems.WoodenOilBucket, "WoodenOilBucket");
        GameRegistry.registerItem(TechnoItems.WoodenFuelBucket, "WoodenFuelBucket");
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("oil"), new ItemStack(TechnoItems.WoodenOilBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("fuel"), new ItemStack(TechnoItems.WoodenFuelBucket), new ItemStack(TFCItems.woodenBucketEmpty));
    }

    public void SetupForestryItems()
    {
     //Ethanol, Biomass, Glass, Honey, Ice, Juice, Seedoil, Short_Mead
		TechnoItems.WoodenEthanolBucket = new ItemWoodenFluidlBucket(Fluids.ETHANOL.getBlock()).SetIconTexture("technofirma:WoodenEthanolBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Ethanol Bucket");
		TechnoItems.WoodenBiomassBucket = new ItemWoodenFluidlBucket(Fluids.BIOMASS.getBlock()).SetIconTexture("technofirma:WoodenBiomassBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Biomass Bucket");
		TechnoItems.WoodenGlassBucket = new ItemWoodenFluidlBucket(Fluids.GLASS.getBlock()).SetIconTexture("technofirma:WoodenGlassBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Glass Bucket");
		TechnoItems.WoodenHoneyBucket = new ItemWoodenFluidlBucket(Fluids.HONEY.getBlock()).SetIconTexture("technofirma:WoodenHoneyBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Honey Bucket");
		TechnoItems.WoodenIceBucket = new ItemWoodenFluidlBucket(Fluids.ICE.getBlock()).SetIconTexture("technofirma:WoodenIceBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Ice Bucket");
		TechnoItems.WoodenJuiceBucket = new ItemWoodenFluidlBucket(Fluids.JUICE.getBlock()).SetIconTexture("technofirma:WoodenJuiceBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Juice Bucket");
		TechnoItems.WoodenSeedOilBucket = new ItemWoodenFluidlBucket(Fluids.SEEDOIL.getBlock()).SetIconTexture("technofirma:WoodenSeedOilBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Seed Oil Bucket");
		TechnoItems.WoodenShortMeadBucket = new ItemWoodenFluidlBucket(Fluids.SHORT_MEAD.getBlock()).SetIconTexture("technofirma:WoodenShortMeadBucket").setContainerItem(Items.bucket).setUnlocalizedName("Wooden Short Mead Bucket");
    }

    public void RegisterForestryItems()
    {
        GameRegistry.registerItem(TechnoItems.WoodenEthanolBucket, "WoodenEthanolBucket");
        GameRegistry.registerItem(TechnoItems.WoodenBiomassBucket, "WoodenBiomassBucket");
        GameRegistry.registerItem(TechnoItems.WoodenGlassBucket, "WoodenGlassBucket");
        GameRegistry.registerItem(TechnoItems.WoodenHoneyBucket, "WoodenHoneyBucket");
        GameRegistry.registerItem(TechnoItems.WoodenIceBucket, "WoodenIceBucket");
        GameRegistry.registerItem(TechnoItems.WoodenJuiceBucket, "WoodenJuiceBucket");
        GameRegistry.registerItem(TechnoItems.WoodenSeedOilBucket, "WoodenSeedOilBucket");
        GameRegistry.registerItem(TechnoItems.WoodenShortMeadBucket, "WoodenShortMeadBucket");
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("bioethanol"), new ItemStack(TechnoItems.WoodenEthanolBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("biomass"), new ItemStack(TechnoItems.WoodenBiomassBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("glass"), new ItemStack(TechnoItems.WoodenGlassBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("for.honey"), new ItemStack(TechnoItems.WoodenHoneyBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("ice"), new ItemStack(TechnoItems.WoodenIceBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("juice"), new ItemStack(TechnoItems.WoodenJuiceBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("seedoil"), new ItemStack(TechnoItems.WoodenSeedOilBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("short.mead"), new ItemStack(TechnoItems.WoodenShortMeadBucket), new ItemStack(TFCItems.woodenBucketEmpty));
    }

    public void SetupMekanismItems()
    {
        //BrineBucket
        //LithiumBucket
        //HeavyWaterBucket

    }

    public void RegisterMekanismItems()
    {
        GameRegistry.registerItem(TechnoItems.WoodenBrineBucket, "WoodenBrineBucket");
        GameRegistry.registerItem(TechnoItems.WoodenLithiumBucket, "WoodenLithiumBucket");
        GameRegistry.registerItem(TechnoItems.WoodenHeavyWaterBucket, "WoodenHeavyWaterBucket");
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("brine"), new ItemStack(TechnoItems.WoodenBrineBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("lithium"), new ItemStack(TechnoItems.WoodenLithiumBucket), new ItemStack(TFCItems.woodenBucketEmpty));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("heavywater"), new ItemStack(TechnoItems.WoodenHeavyWaterBucket), new ItemStack(TFCItems.woodenBucketEmpty));
    }
	
	public void SetupMagicItems()
	{
		// Setup magic ingots
		TechnoItems.ThaumicSteelIngot = new ItemTechnofirmaIngot().SetIconTexture("thaumcraft:thaumiumingot").setUnlocalizedName("Thaumic Steel Ingot");
		
		// Setup Magic Melted Metal
		TechnoItems.ThaumicSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Thaumic Steel Unshaped");
		
		// Setup Magic Metal
		TechnoItems.ThaumicSteel = new Metal("Thaumic Steel", TechnoItems.ThaumicSteelUnshaped, TechnoItems.ThaumicSteelIngot);
	
		// Setup magic tools
		TechnoItems.ThaumicSteelAxe = new ItemTechnofirmaAxe(TechnofirmaCore.ThaumicSteelMaterial, 250).SetIconTexture("thaumcraft:thaumiumaxe").setUnlocalizedName("Thaumic Steel Axe").setMaxDamage(TFCItems.steelUses + 250);
		TechnoItems.ThaumicSteelShovel = new ItemTechnofirmaShovel(TechnofirmaCore.ThaumicSteelMaterial).SetIconTexture("thaumcraft:thaumiumshovel").setUnlocalizedName("Thaumic Steel Shovel").setMaxDamage(TFCItems.steelUses + 250);
		TechnoItems.ThaumicSteelPickaxe = new ItemTechnofirmaPickaxe(TechnofirmaCore.ThaumicSteelMaterial).SetIconTexture("thaumcraft:thaumiumpick").setUnlocalizedName("Thaumic Steel Pickaxe").setMaxDamage(TFCItems.steelUses + 250);
		TechnoItems.ThaumicSteelHoe = new ItemTechnofirmaHoe(TechnofirmaCore.ThaumicSteelMaterial).SetIconTexture("thaumcraft:thaumiumhoe").setUnlocalizedName("Thaumic Steel Hoe").setMaxDamage(TFCItems.steelUses + 250);
		TechnoItems.ThaumicSteelSword = new ItemTechnofirmaSword(TechnofirmaCore.ThaumicSteelMaterial, 300).SetIconTexture("thaumcraft:thaumiumsword").setUnlocalizedName("Thaumic Steel Sword").setMaxDamage(TFCItems.steelUses + 250);
		
		// Setup magic tool heads
		TechnoItems.ThaumicSteelAxeHead = new ItemTechnofirmaToolHead().SetIconTexture("technofirma:Thaumic Steel Axe Head").setUnlocalizedName("Thaumic Steel Axe Head");
		TechnoItems.ThaumicSteelShovelHead = new ItemTechnofirmaToolHead().SetIconTexture("technofirma:Thaumic Steel Shovel Head").setUnlocalizedName("Thaumic Steel Shovel Head");
		TechnoItems.ThaumicSteelPickaxeHead = new ItemTechnofirmaToolHead().SetIconTexture("technofirma:Thaumic Steel Pickaxe Head").setUnlocalizedName("Thaumic Steel Pickaxe Head");
		TechnoItems.ThaumicSteelHoeHead = new ItemTechnofirmaToolHead().SetIconTexture("technofirma:Thaumic Steel Hoe Head").setUnlocalizedName("Thaumic Steel Hoe Head");
		TechnoItems.ThaumicSteelSwordHead = new ItemTechnofirmaToolHead().SetIconTexture("technofirma:Thaumic Steel Sword Head").setUnlocalizedName("Thaumic Steel Sword Head");
	
		// Create our items
		TechnoItems.ProspectingFocus = new ItemFocusProspecting().setUnlocalizedName("prospecting_focus");
	}
	
	public void RegisterRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicSteelAxe, 1), new Object[] { "#", "%", Character.valueOf('#'), new ItemStack(TechnoItems.ThaumicSteelAxeHead, 1), Character.valueOf('%'), new ItemStack(TFCItems.stick, 1)});
		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicSteelShovel, 1), new Object[] { "#", "%", Character.valueOf('#'), new ItemStack(TechnoItems.ThaumicSteelShovelHead, 1), Character.valueOf('%'), new ItemStack(TFCItems.stick, 1)});
		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicSteelPickaxe, 1), new Object[] { "#", "%", Character.valueOf('#'), new ItemStack(TechnoItems.ThaumicSteelPickaxeHead, 1), Character.valueOf('%'), new ItemStack(TFCItems.stick, 1)});
		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicSteelHoe, 1), new Object[] { "#", "%", Character.valueOf('#'), new ItemStack(TechnoItems.ThaumicSteelHoeHead, 1), Character.valueOf('%'), new ItemStack(TFCItems.stick, 1)});
		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicSteelSword, 1), new Object[] { "#", "%", Character.valueOf('#'), new ItemStack(TechnoItems.ThaumicSteelSwordHead, 1), Character.valueOf('%'), new ItemStack(TFCItems.stick, 1)});
	
		GameRegistry.addShapelessRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot, 1), new Object[] { ItemApi.getItem("itemResource", 2), new ItemStack(TFCItems.steelIngot, 1) });

		GameRegistry.addRecipe(new ItemStack(TechnoItems.ThaumicAnvil, 1), new Object[] { "   ", "   ", "###", Character.valueOf('#'), ItemApi.getBlock("blockCosmeticSolid", 4)});
	}
	
	public void RegisterEvents() 
	{
		MinecraftForge.EVENT_BUS.register(new ThaumcraftTreeFell());
        MinecraftForge.EVENT_BUS.register(new EventHooks());
	}
	
	public void RegisterMagicTileEntities()
	{
		GameRegistry.registerTileEntity(TEMagicAnvil.class, "MagicAnvil");
		GameRegistry.registerTileEntity(TETFIngotPile.class, "IngotPile");
	}
	
	public void RegisterWailaMessages()
	{
		// For our dark ore to show up properly in waila
		if (TechnofirmaCore.foundThaumcraft)
		{
			FMLInterModComms.sendMessage("Waila", "register", "technofirma.WAILA.WDarkOre.callbackRegister");
			FMLInterModComms.sendMessage("Waila", "register", "technofirma.WAILA.WTFIngotPile.callbackRegister");
		}
	}
	
	public void RegisterMagicItems()
	{
		// Register magic tools
		GameRegistry.registerItem(TechnoItems.ThaumicSteelAxe, "ThaumicSteelAxe");
		OreDictionary.registerOre("itemAxe", TechnoItems.ThaumicSteelAxe);
		GameRegistry.registerItem(TechnoItems.ThaumicSteelShovel, "ThaumicSteelShovel");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelPickaxe, "ThaumicSteelPickage");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelHoe, "ThaumicSteelHoe");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelSword, "ThaumicSteelSword");
		
		// Register magic tool heads
		GameRegistry.registerItem(TechnoItems.ThaumicSteelAxeHead, "ThaumicSteelAxeHead");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelShovelHead, "ThaumicSteelShovelHead");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelPickaxeHead, "ThaumicSteelPickaxeHead");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelHoeHead, "ThaumicSteelHoeHead");
		GameRegistry.registerItem(TechnoItems.ThaumicSteelSwordHead, "ThaumicSteelSwordHead");
		
		// Register magic ingots
		GameRegistry.registerItem(TechnoItems.ThaumicSteelIngot, "ThaumicSteelIngot");
		
		// Register magic unshaped metals
		GameRegistry.registerItem(TechnoItems.ThaumicSteelUnshaped, "ThaumicSteelUnshaped");
		
		// Register our magic foci
		GameRegistry.registerItem(TechnoItems.ProspectingFocus, "Prospecting Focus");
	}
	
	public void RegisterMagicToolsClasses()
	{
		TechnoItems.ThaumicSteelAxe.setHarvestLevel("axe", 3);
		TechnoItems.ThaumicSteelShovel.setHarvestLevel("shovel", 3);
		TechnoItems.ThaumicSteelPickaxe.setHarvestLevel("pickaxe", 3);
	}
	
	
	public void RegisterAnvilPlans()
	{
		// Get the anvil manager
		AnvilManager anvilmgr = AnvilManager.getInstance();
		
		// Prospecting focus
		anvilmgr.addPlan("pro_focus", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.DRAWNOTLAST, RuleEnum.BENDNOTLAST}));
		
		// Iron Wand cap
		anvilmgr.addPlan("iron_wand_cap", new PlanRecipe(new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
	}
	
	public void RegisterAnvilRecipes()
	{
		// Get the anvil manager
		AnvilManager anvilmgr = AnvilManager.getInstance();
		
		// Prospecting Focus
		anvilmgr.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.steelIngot2x), null, "pro_focus", 70, false, AnvilReq.STEEL.ordinal(), new ItemStack(TechnoItems.ProspectingFocus, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		
		// Iron Wand Cap
		anvilmgr.addRecipe(new AnvilRecipe(new ItemStack(TFCItems.wroughtIronIngot), null, "iron_wand_cap", 70, false, AnvilReq.WROUGHTIRON.ordinal(), ItemApi.getItem("itemWandCap", 0)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	}
		
	public void RegisterMagicAnvilPlans()
	{
		// Get the anvil manager
		MagicAnvilManager anvilmgr = MagicAnvilManager.getInstance();
		
		// Register our plans
		AspectList alist = new AspectList();
		alist.add(Aspect.ORDER, 2);
		alist.add(Aspect.FIRE, 2);
		alist.add(Aspect.AIR, 2);

		anvilmgr.addPlan("copper_wand_cap", new MagicPlanRecipe("CAP_copper", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		
		alist = new AspectList();
		alist.add(Aspect.ORDER, 4);
		alist.add(Aspect.FIRE, 4);
		alist.add(Aspect.AIR, 4);
		
		anvilmgr.addPlan("silver_wand_cap", new MagicPlanRecipe("CAP_silver", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		
		alist = new AspectList();
		alist.add(Aspect.ORDER, 3);
		alist.add(Aspect.FIRE, 3);
		alist.add(Aspect.AIR, 3);
		
		anvilmgr.addPlan("gold_wand_cap", new MagicPlanRecipe("CAP_gold", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.PUNCHSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		
		alist = new AspectList();	
		anvilmgr.addPlan("thaumiumaxe", new MagicPlanRecipe("ASPECTS", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST}));
		anvilmgr.addPlan("thaumiumshovel", new MagicPlanRecipe("ASPECTS", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITNOTLAST, RuleEnum.ANY}));
		anvilmgr.addPlan("thaumiumpickaxe", new MagicPlanRecipe("ASPECTS", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.BENDNOTLAST, RuleEnum.DRAWNOTLAST}));
		anvilmgr.addPlan("thaumiumhoe", new MagicPlanRecipe("ASPECTS", alist, new RuleEnum[]{RuleEnum.PUNCHLAST, RuleEnum.HITNOTLAST, RuleEnum.BENDNOTLAST}));
		anvilmgr.addPlan("thaumiumsword", new MagicPlanRecipe("ASPECTS", alist, new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
	}
	
	public void RegisterMagicAnvilRecipes()
	{
		// Get the anvil manager
		MagicAnvilManager anvilmgr = MagicAnvilManager.getInstance();
						
		// Register our anvil recipes
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TFCItems.copperIngot), null, "copper_wand_cap", ItemApi.getItem("itemWandCap", 3)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TFCItems.silverIngot), null, "silver_wand_cap", ItemApi.getItem("itemWandCap", 5)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TFCItems.goldIngot), null, "gold_wand_cap", ItemApi.getItem("itemWandCap", 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot), null, "thaumiumaxe", new ItemStack(TechnoItems.ThaumicSteelAxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot), null, "thaumiumshovel", new ItemStack(TechnoItems.ThaumicSteelShovelHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot), null, "thaumiumpickaxe", new ItemStack(TechnoItems.ThaumicSteelPickaxeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot), null, "thaumiumhoe", new ItemStack(TechnoItems.ThaumicSteelHoeHead, 1)).addRecipeSkill(Global.SKILL_TOOLSMITH));
		anvilmgr.addRecipe(new MagicAnvilRecipe(new ItemStack(TechnoItems.ThaumicSteelIngot), null, "thaumiumsword", new ItemStack(TechnoItems.ThaumicSteelSwordHead, 1)).addRecipeSkill(Global.SKILL_WEAPONSMITH));
	}
}
