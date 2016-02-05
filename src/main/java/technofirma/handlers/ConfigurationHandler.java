package technofirma.handlers;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class ConfigurationHandler
{
    public static void init(File configFile)
    {
        Configuration config = new Configuration(configFile);

        try
        {
            config.load();

        }
        catch (Exception e)
        {

        }
        finally
        {
            if (config.hasChanged())
            {
                config.save();
            }
        }
    }
}
