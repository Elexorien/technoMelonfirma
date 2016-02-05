package technofirma.integration;

import java.util.ArrayList;

public interface ITFPluginManager
{
    public void registerPlugin(ITFPlugin plugin);
    public ArrayList<ITFPlugin> getPluginsList();
    public void runPluginLoadEvent(Object o);
}
