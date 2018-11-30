package SGE.client.render.fabs.tiles;

import org.lwjgl.opengl.GL11;

import SGE.api.stargate.StargateType;
import SGE.client.models.ModelDHD;
import SGE.common.base.SGETile;
import SGE.common.base.SGETileRenderer;
import SGE.common.base.pipeline.SGETileRenderPipeline;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.util.math.Orientations;
import SGE.tiles.TileDHD;

public class TileDHDRenderer extends SGETileRenderer {


	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SGETileRenderer getParent() {
		return null;
	}

	@Override
	public boolean renderTileEntityAt(SGETile tile, SGETileRenderPipeline renderer, double x, double y, double z,
			float partialTickTime) {
		if (ModelDHD.$ == null)
			return false;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5f, 0.0f, 0.5f);
		GL11.glRotatef(Orientations.from(tile.getRotation()).angle(), 0, 1, 0);
		GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
		TileDHD dhd = (TileDHD) tile;
		ModelDHD.$.prepareAndRender(StargateType.fromOrdinal(tile.getBlockMetadata()), dhd.clientAskConnectionOpen());
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		return true;
	}

}
