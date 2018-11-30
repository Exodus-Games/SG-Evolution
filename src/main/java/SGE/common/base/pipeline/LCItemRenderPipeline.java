package SGE.common.base.pipeline;

import SGE.SGERuntime;
import SGE.api.defs.ISGEvolutionRenderer;
import SGE.common.SGELog;
import SGE.common.base.SGEItemRenderer;
import SGE.common.base.SGETileRenderer;
import SGE.common.impl.registry.DefinitionRegistry;
import SGE.common.impl.registry.DefinitionRegistry.RendererType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

/**
 * Item rendering hook.
 *
 * @author Exodus Games
 *
 */
public class SGEItemRenderPipeline implements IItemRenderer {

	private DefinitionRegistry registry;

	/**
	 * Create a new rendering hook.
	 */
	public SGEItemRenderPipeline() {
		registry = (DefinitionRegistry) LCRuntime.runtime.registries().definitions();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.ITEM, item.getItem().getClass());
		if (worker == null && !(worker instanceof SGEItemRenderer))
			flag = false;
		else
			try {
				SGEItemRenderer itemRenderer = (SGEItemRenderer) worker;
				while (itemRenderer != null && !itemRenderer.handleRenderType(item, type)) {
					worker = itemRenderer.getParent();
					if (worker == null || !(worker instanceof SGETileRenderer)) {
						flag = false;
						break;
					}
				}
			} catch (Throwable t) {
				SGELog.warn("Uncaught item rendering exception.", t);
				flag = false;
			}
		return flag;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.ITEM, item.getItem().getClass());
		if (worker == null && !(worker instanceof SGEItemRenderer))
			flag = false;
		else
			try {
				SGEItemRenderer itemRenderer = (SGEItemRenderer) worker;
				while (itemRenderer != null && !itemRenderer.shouldUseRenderHelper(type, item, helper)) {
					worker = itemRenderer.getParent();
					if (worker == null || !(worker instanceof LCTileRenderer)) {
						flag = false;
						break;
					}
				}
			} catch (Throwable t) {
				SGELog.warn("Uncaught item rendering exception.", t);
				flag = false;
			}
		return flag;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.ITEM, item.getItem().getClass());
		if (worker != null && worker instanceof SGEItemRenderer)
			try {
				SGEItemRenderer itemRenderer = (SGEItemRenderer) worker;
				while (itemRenderer != null && !itemRenderer.renderItem(type, item, data)) {
					worker = itemRenderer.getParent();
					if (worker == null || !(worker instanceof SGETileRenderer))
						break;
				}
			} catch (Throwable t) {
				SGELog.warn("Uncaught item rendering exception.", t);
			}
	}
}
