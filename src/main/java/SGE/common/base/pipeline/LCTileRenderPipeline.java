package lc.common.base.pipeline;

import SGE.SGERuntime;
import SGE.api.defs.ISGEvolutionRenderer;
import SGE.client.render.fabs.DefaultTileRenderer;
import SGE.common.SGELog;
import SGE.common.base.SGETile;
import SGE.common.base.SGETileRenderer;
import SGE.common.impl.registry.DefinitionRegistry;
import SGE.common.impl.registry.DefinitionRegistry.RendererType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Tile entity rendering hook.
 *
 * @author Exodus Games
 *
 */
public class SGETileRenderPipeline extends TileEntitySpecialRenderer {

	private DefinitionRegistry registry;
	private final DefaultTileRenderer defaultTileRenderer;

	/**
	 * Create a new rendering hook.
	 */
	public SGETileRenderPipeline() {
		registry = (DefinitionRegistry) SGERuntime.runtime.registries().definitions();
		defaultTileRenderer = new DefaultTileRenderer();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTickTime) {
		SGETile lct = (SGETile) tile;
		if (!lct.shouldRender())
			return;
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.TILE, lct.getClass());
		if (worker == null && !(worker instanceof SGETileRenderer))
			flag = false;
		else
			try {
				SGETileRenderer tileRenderer = (SGETileRenderer) worker;
				while (tileRenderer != null && !tileRenderer.renderTileEntityAt(lct, this, x, y, z, partialTickTime)) {
					worker = tileRenderer.getParent();
					if (worker == null || !(worker instanceof SGETileRenderer)) {
						flag = false;
						break;
					}
				}
			} catch (Throwable t) {
				SGELog.warn("Uncaught tile rendering exception.", t);
				flag = false;
			}
		if (!flag)
			defaultTileRenderer.renderTileEntityAt(lct, this, x, y, z, partialTickTime);
	}

	/**
	 * Bind a texture in OpenGL
	 *
	 * @param texture
	 *            The texture resource.
	 */
	public void bind(ResourceLocation texture) {
		bindTexture(texture);
	}

}
