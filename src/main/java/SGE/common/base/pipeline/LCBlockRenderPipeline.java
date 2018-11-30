package SGE.common.base.pipeline;

import SGE.SGERuntime;
import SGE.api.defs.ISGEvolutionRenderer;
import SGE.client.render.fabs.DefaultBlockRenderer;
import SGE.common.SGELog;
import SGE.common.base.SGEBlockRenderer;
import SGE.common.impl.registry.DefinitionRegistry;
import SGE.common.impl.registry.DefinitionRegistry.RendererType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

/**
 * Block rendering hook.
 *
 * @author Exodus Games
 *
 */
public class SGEBlockRenderPipeline implements ISimpleBlockRenderingHandler {

	private final int renderIdx;
	private final DefinitionRegistry registry;
	private final DefaultBlockRenderer defaultBlockRenderer;

	/**
	 * Create a new rendering hook.
	 *
	 * @param renderIdx
	 *            The renderer ID of this hook
	 */
	public SGEBlockRenderPipeline(int renderIdx) {
		this.renderIdx = renderIdx;
		registry = (DefinitionRegistry) LCRuntime.runtime.registries().definitions();
		defaultBlockRenderer = new DefaultBlockRenderer();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.BLOCK, block.getClass());
		if (worker == null || !(worker instanceof SGEBlockRenderer))
			flag = false;
		else
			try {
				SGEBlockRenderer blockRenderer = (SGEBlockRenderer) worker;
				while (blockRenderer != null && !blockRenderer.renderInventoryBlock(block, renderer, metadata)) {
					worker = registry.getRenderer(RendererType.BLOCK, blockRenderer.getParent());
					if (worker == null || !(worker instanceof SGEBlockRenderer)) {
						flag = false;
						break;
					}
					blockRenderer = (SGEBlockRenderer) worker;
				}
			} catch (Throwable t) {
				SGELog.warn("Uncaught block rendering exception.", t);
				flag = false;
			}
		if (!flag)
			defaultBlockRenderer.renderInventoryBlock(block, renderer, metadata);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.BLOCK, block.getClass());
		if (worker == null || !(worker instanceof SGEBlockRenderer))
			flag = false;
		else {
			SGEBlockRenderer blockRenderer = (SGEBlockRenderer) worker;
			while (blockRenderer != null && !blockRenderer.renderWorldBlock(block, renderer, world, x, y, z)) {
				worker = registry.getRenderer(RendererType.BLOCK, blockRenderer.getParent());
				if (worker == null || !(worker instanceof SGEBlockRenderer)) {
					flag = false;
					break;
				}
				blockRenderer = (SGEBlockRenderer) worker;
			}
		}

		if (!flag)
			flag = defaultBlockRenderer.renderWorldBlock(block, renderer, world, x, y, z);
		return flag;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderIdx;
	}

}
