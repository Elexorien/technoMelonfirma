package technofirma.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabTechno
{
    public static final CreativeTabs TECHNO_TAB = new CreativeTabs(Reference.MOD_ID)
    {
        @Override
        public Item getTabIconItem()
        {
            return TechnoItems.ThaumicSteelAxe;
        }
    };

}
