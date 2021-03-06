package SGE.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.common.base.SGEBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.items.ItemBlockTransportRing;
import SGE.tiles.TileTransportRing;

/**
 * Transporter ring block implementation
 * 
 * @author Exodus Games
 *
 */
@Definition(name = "blockTransportRing", type = ComponentType.STARGATE, blockClass = BlockTransportRing.class, itemBlockClass = ItemBlockTransportRing.class, tileClass = TileTransportRing.class)
public class BlockTransportRing extends SGEBlock {

	IIcon topAndBottomTexture;
	IIcon sideTexture;

	/** Default constructor */
	public BlockTransportRing() {
		super(Material.ground);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	@Override
	public IIcon getIcon(int side, int data) {
		if (side <= 1)
			return topAndBottomTexture;
		else
			return sideTexture;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		topAndBottomTexture = register.registerIcon(ResourceAccess
				.formatResourceName("${ASSET_KEY}:transport_ring_base_${TEX_QUALITY}"));
		sideTexture = register.registerIcon(ResourceAccess
				.formatResourceName("${ASSET_KEY}:stargate_ring_${TEX_QUALITY}"));
	}

}
