package technofirma.core;

public class Reference
{
	public static final String MOD_ID = "technofirma";
	public static final String MOD_NAME = "Technofirma";

	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 4;
	public static final int VERSION_REV = 3;

	public static final String MOD_VERSION = VERSION_MAJOR+"."+VERSION_MINOR+"."+VERSION_REV;

	public static final String MOD_DEPENDENCIES = "required-after:terrafirmacraft;required-after:Thaumcraft;after:BuildCraft|Core;after:BuildCraft|Transport;after:BuildCraft|Silicon;after:BuildCraft|Builders;after:BuildCraft|Energy;after:BuildCraft|Factory;after:Forestry";

	public static final String SERVER_PROXY_CLASS = "technofirma.core.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "technofirma.core.ClientProxy";

	public static final String ASSET_PATH = "/assets/" + MOD_ID + "/";
	public static final String ASSET_PATH_GUI = "textures/gui/";
}