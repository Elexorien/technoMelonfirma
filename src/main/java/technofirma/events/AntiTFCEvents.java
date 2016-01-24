package technofirma.events;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class AntiTFCEvents 
{
	@SubscribeEvent
	public void OnPlayerRespawn(PlayerRespawnEvent event)
	{		
		if (!event.player.worldObj.isRemote && event.player != null)
		{	
			FoodStatsTFC foodstats = new FoodStatsTFC(event.player);
			foodstats.nutrFruit = 0.5f;
			foodstats.nutrGrain = 0.5f;
			foodstats.nutrProtein = 0.5f;
			foodstats.nutrVeg = 0.5f;
			foodstats.nutrDairy = 0.5f;
			TFC_Core.setPlayerFoodStats(event.player, foodstats);
		}
	}
}
