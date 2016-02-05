package technofirma.integration;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;

public class TFPluginmanager implements ITFPluginManager
{
    private ArrayList<ITFPlugin> plugins = new ArrayList();

    @Override
    public void registerPlugin(ITFPlugin plugin)
    {
        plugins.add(plugin);
    }

    @Override
    public ArrayList<ITFPlugin> getPluginsList()
    {
        return plugins;
    }

    @Override
    public void runPluginLoadEvent(Object o)
    {
        for (ITFPlugin plugin : this.getPluginsList())
        {
            if (o instanceof FMLPreInitializationEvent)
            {
                plugin.onPreLoad();
            }
            else if (o instanceof FMLInitializationEvent)
            {
                plugin.onLoad();
            }
            else if (o instanceof FMLPostInitializationEvent)
            {
                plugin.onPostLoad();
            }
        }
    }
}
