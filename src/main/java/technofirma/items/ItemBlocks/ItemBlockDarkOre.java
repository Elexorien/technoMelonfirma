package technofirma.items.ItemBlocks;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import net.minecraft.block.Block;
import technofirma.core.Global;
import technofirma.core.TechnoItems;
import technofirma.core.TechnofirmaCore;

public class ItemBlockDarkOre extends ItemTerraBlock
{
        public ItemBlockDarkOre(Block b)
    {
        super(b);
        setHasSubtypes(true);
        metaNames = Global.DARKORENAMES;
    }

}
