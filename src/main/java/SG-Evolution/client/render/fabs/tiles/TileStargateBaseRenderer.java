package SGE.client.render.fabs.tiles;

import SGE.api.stargate.StargateType;
import SGE.client.animation.Animation;
import SGE.client.models.ModelStargate;
import SGE.common.base.LCTile;
import SGE.common.base.LCTileRenderer;
import SGE.common.base.multiblock.MultiblockState;
import SGE.common.base.pipeline.LCTileRenderPipeline;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.common.resource.ResourceMap;
import SGE.common.util.data.StateMap;
import SGE.tiles.TileStargateBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * Stargate base tile renderer.
 *
 * @author Exodus Games
 *
 */
public class TileStargateBaseRenderer extends SGETileRenderer {

	/** The texture resource map */
	public final ResourceMap resources = new ResourceMap();

	public final ResourceLocation fxHorizon = ResourceAccess.getNamedResource(ResourceAccess
			.formatResourceName("textures/fx/eventhorizon_${TEX_QUALITY}.png"));

	public final static int ehGridRadialSize = 10;
	public final static int ehGridPolarSize = ModelStargate.numRingSegments;
	public final static double ehBandWidth = ModelStargate.ringInnerRadius / ehGridRadialSize;

	/** The Stargate model */
	public final ModelStargate model;

	/** Default constructor */
	public TileStargateBaseRenderer() {
		for (StargateType type : StargateType.values()) {
			ResourceLocation fr, gl;
			String suffix = (type.getSuffix() != null && type.getSuffix().length() > 0) ? "_" + type.getSuffix()
					+ "_${TEX_QUALITY}.png" : "_${TEX_QUALITY}.png";
			fr = ResourceAccess.getNamedResource(ResourceAccess.formatResourceName("textures/tileentity/stargate"
					+ suffix));
			gl = ResourceAccess.getNamedResource(ResourceAccess
					.formatResourceName("textures/tileentity/stargate_glyphs" + suffix));
			ResourceMap map = new ResourceMap();
			map.add("frame", fr).add("glyphs", gl);
			resources.add(type.getName(), map);
		}

		model = new ModelStargate();
		model.init();
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
		if (tile instanceof TileStargateBase) {
			TileStargateBase base = (TileStargateBase) tile;
			if (base.getState() == MultiblockState.FORMED) {
				GL11.glPushMatrix();
				GL11.glTranslated(x + 0.5d, y + 3.5d, z + 0.5d);
				StateMap state = base.renderInfoTile().tileRenderState();
				Animation animation = (Animation) base.renderInfoTile().tileAnimation();
				if (animation != null) {
					Double frame = base.renderInfoTile().tileAnimationProgress() + (double) partialTickTime;
					if (!animation.finished(frame))
						animation.sampleProperties(state, frame);
				}
				model.render(this, renderer, base, state);
				if (state.get("event-horizon", false))
					renderGfxHorizonImmediate(base, renderer, x, y, z);
				GL11.glPopMatrix();
			}
		}
		return true;
	}

	private void renderGfxHorizonImmediate(TileStargateBase tile, SGETileRenderPipeline pipeline, double x, double y,
			double z) {
		pipeline.bind(fxHorizon);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glNormal3d(0, 0, 1);
		double grid[][] = tile.getGfxGrid()[0];
		for (int i = 1; i < ehGridRadialSize; i++) {
			GL11.glBegin(GL11.GL_QUAD_STRIP);
			for (int j = 0; j <= ehGridPolarSize; j++) {
				eventHVertex(grid, i, j);
				eventHVertex(grid, i + 1, j);
			}
			GL11.glEnd();
		}

		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2d(0.5, 0.5);
		GL11.glVertex3d(0, 0, grid[1][0]);
		for (int j = 0; j <= ehGridPolarSize; j++)
			eventHVertex(grid, 1, j);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_CULL_FACE);

	}

	private void eventHVertex(double[][] grid, int i, int j) {
		double r = i * 2.75d / 10.0d;
		double x = r * ModelStargate.cos[j];
		double y = r * ModelStargate.sin[j];

		double u = (x + 3) / 6;
		double v = (y + 3) / 6;
		GL11.glTexCoord2d(u, v);
		GL11.glVertex3d(x, y, grid[j + 1][i]);
	}
}
