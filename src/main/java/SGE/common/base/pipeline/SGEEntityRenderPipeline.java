package SGE.common.base.pipeline;

import SGE.SGERuntime;
import SGE.api.defs.ISGEvolutionRenderer;
import SGE.common.SGELog;
import SGE.common.base.SGEEntityRenderer;
import SGE.common.impl.registry.DefinitionRegistry;
import SGE.common.impl.registry.DefinitionRegistry.RendererType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class SGEEntityRenderPipeline extends Render {
	private final DefinitionRegistry registry;

	public SGEEntityRenderPipeline() {
		registry = (DefinitionRegistry) SGERuntime.runtime.registries().definitions();
	}

	public void useEntityTexture(Entity e) {
		this.bindEntityTexture(e);
	}

	@Override
	public void doRender(Entity e, double rpx, double rpy, double rpz, float yaw, float frame) {
		boolean flag = true;
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.ENTITY, e.getClass());
		if (worker == null || !(worker instanceof SGEEntityRenderer))
			flag = false;
		else {
			SGEEntityRenderer entityRenderer = (SGEEntityRenderer) worker;
			while (entityRenderer != null && !entityRenderer.doRender(this, e, rpx, rpy, rpz, yaw, frame)) {
				worker = registry.getRenderer(RendererType.ENTITY, entityRenderer.getParent());
				if (worker == null || !(worker instanceof SGEEntityRenderer)) {
					flag = false;
					break;
				}
				entityRenderer = (SGEEntityRenderer) worker;
			}
		}

		if (!flag)
			SGELog.warn("Unable to render entity class %s.", e.getClass());
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		ISGEvolutionRenderer worker = registry.getRendererFor(RendererType.ENTITY, e.getClass());
		if (worker == null || !(worker instanceof SGEEntityRenderer))
			return null;
		SGEEntityRenderer entityRenderer = (SGEEntityRenderer) worker;
		return entityRenderer.getEntityTexture(this, e);
	}

}
