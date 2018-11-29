package SGE.client.render.fabs.tiles;

import SGE.client.animation.Animation;
import SGE.client.models.ModelTransportRing;
import SGE.common.base.LCTile;
import SGE.common.base.LCTileRenderer;
import SGE.common.base.pipeline.LCTileRenderPipeline;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.common.util.data.StateMap;
import SGE.tiles.TileTransportRing;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.lwjgl.opengl.GL11;

public class TileTransportRingRenderer extends SGETileRenderer {

	public TileTransportRingRenderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	@Override
	public LCTileRenderer getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean renderTileEntityAt(SGETile tile, SGETileRenderPipeline renderer, double x, double y, double z,
			float partialTickTime) {
		if (ModelTransportRing.$ == null)
			return false;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y + 0.75f, z);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResourceAccess.getNamedResource(ResourceAccess
				.formatResourceName("textures/models/transport_rings_${TEX_QUALITY}.png")));

		TileTransportRing ring = (TileTransportRing) tile;

		StateMap state = ring.renderInfoTile().tileRenderState();
		Animation animation = (Animation) ring.renderInfoTile().tileAnimation();
		if (animation != null) {
			Double frame = ring.renderInfoTile().tileAnimationProgress() + (double) partialTickTime;
			if (!animation.finished(frame))
				animation.sampleProperties(state, frame);
		}

		for (int i = 0; i < 6; i++) {
			GL11.glPushMatrix();
			double height = state.get("ring-height-" + i, 0.0d);
			GL11.glTranslated(0.0d, height, 0.0d);
			ModelTransportRing.$.prepareAndRender();
			GL11.glPopMatrix();
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		return true;
	}

}
