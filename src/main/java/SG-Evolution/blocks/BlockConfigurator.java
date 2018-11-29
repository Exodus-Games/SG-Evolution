package SGE.blocks;

import net.minecraft.block.material.Material;
import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.common.base.LCBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.items.ItemBlockConfigurator;
import SGE.tiles.TileConfigurator;

@Definition(name = "blockConfigurator", type = ComponentType.CORE, blockClass = BlockConfigurator.class, itemBlockClass = ItemBlockConfigurator.class, tileClass = TileConfigurator.class)
public class BlockConfigurator extends SGEBlock {

	public BlockConfigurator() {
		super(Material.ground);
		setProvidesInventory(true).setCanRotate(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

}
