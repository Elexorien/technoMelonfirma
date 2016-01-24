package technofirma.handlers;

import codechicken.nei.api.API;
import technofirma.NEI.MagicAnvilNEIHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class NEIHandler
{
    @SubscribeEvent
    public void tickEvent(WorldEvent.Load loadEvent)
    {
    	API.registerRecipeHandler(new MagicAnvilNEIHandler());
    }
}
