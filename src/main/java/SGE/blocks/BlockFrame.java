package SGE.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.common.base.SGEBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.items.ItemBlockFrame;
import SGE.tiles.TileFrame;

@Definition(name = "frame", type = ComponentType.CORE, blockClass = BlockFrame.class, itemBlockClass = ItemBlockFrame.class, tileClass = TileFrame.class)
public class BlockFrame extends SGEBlock {

	IIcon sideTexture;

	public BlockFrame() {
		super(Material.ground);
		setHardness(3F).setResistance(2000F);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	@Override
	public IIcon getIcon(int side, int data) {
		return sideTexture;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		sideTexture = register.registerIcon(ResourceAccess
				.formatResourceName("${ASSET_KEY}:controller_side_${TEX_QUALITY}"));
	}

}
