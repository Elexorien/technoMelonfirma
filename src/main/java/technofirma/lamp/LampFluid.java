package technofirma.lamp;

import java.util.HashMap;
import java.util.Set;

import com.bioxx.tfc.api.TFCFluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class LampFluid 
{
	private static HashMap<Fluid, LampFluid> lampfluids = new HashMap<Fluid, LampFluid>();

	private Fluid ForgeFluid;
	private int Duration;
	
	public LampFluid(Fluid forgefluid, int durationmodifier)
	{
		ForgeFluid = forgefluid;
		Duration = durationmodifier;
		lampfluids.put(forgefluid, this);
	}
	
	public Fluid GetFluid()
	{
		return ForgeFluid;
	}
	
	public int GetDurationModifier()
	{
		return Duration;
	}
	
	public static boolean ValidFluid(Fluid forgefluid)
	{
		return lampfluids.containsKey(forgefluid);
	}
	
	public static LampFluid GetLampFluid(Fluid forgefluid)
	{
		return lampfluids.get(forgefluid);
	}
	
	public static void CreateFluid(String fluidname, int durationmodifier)
	{
		Fluid forgefluid = FluidRegistry.getFluid(fluidname);
		
		if (forgefluid != null)
			new LampFluid(forgefluid, durationmodifier);
	}
	
	public static Set<Fluid> GetAllForgeFluids()
	{
		return lampfluids.keySet();
	}
}
