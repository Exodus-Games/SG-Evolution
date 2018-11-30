package SGE.client.render.fabs.blocks;

import SGE.api.rendering.IBlockRenderInfo;
import SGE.common.base.SGEBlock;
import SGE.common.base.SGEBlockRenderer;
import SGE.common.configuration.xml.ComponentConfig;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Voidable block renderer implementation.
 *
 * @author Exodus Games
 *
 */
public abstract class BlockVoidRenderer extends SGEBlockRenderer {

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderInventoryBlock(Block block, RenderBlocks renderer, int metadata) {
		return false;
	}

	@Override
	public boolean renderWorldBlock(Block block, RenderBlocks renderer, IBlockAccess world, int x, int y, int z) {
		if (!(block instanceof SGEBlock))
			return false;
		SGEBlock sgeb = (SGEBlock) block;
		IBlockRenderInfo info = sgeb.renderInfoBlock();
		if (info == null)
			return false;
		return info.doProperty("noRender", world, world.getBlockMetadata(x, y, z), x, y, z, false);
	}
}
