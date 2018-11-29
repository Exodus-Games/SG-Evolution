package SGE.client.render.fabs;

import java.util.ArrayList;

import SGE.client.models.ModelError;
import SGE.client.models.loader.WavefrontModel.WavefrontModelException;
import SGE.client.opengl.BufferDisplayList;
import SGE.common.LCLog;
import SGE.common.base.LCTile;
import SGE.common.base.LCTileRenderer;
import SGE.common.base.pipeline.LCTileRenderPipeline;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

/**
 * The default tile entity renderer implementation.
 *
 * @author Exodus Games
 *
 */
public class DefaultTileRenderer extends SGETileRenderer {

	/** The system error model */
	private ModelError model;
	/** Display buffer for error model */
	private final BufferDisplayList listModel = new BufferDisplayList();

	private ArrayList<Class<? extends SGETile>> seenTypes;

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	/** Default constructor */
	public DefaultTileRenderer() {
		try {
			model = new ModelError(ResourceAccess.getNamedResource("models/error.obj"));
			listModel.init();
			listModel.enter();
			model.renderAll();
			listModel.exit();
		} catch (WavefrontModelException e) {
			e.printStackTrace();
		}
		seenTypes = new ArrayList<Class<? extends SGETile>>();
	}

	@Override
	public SGETileRenderer getParent() {
		return null;
	}

	@Override
	public boolean renderTileEntityAt(SGETile tile, SGETileRenderPipeline renderer, double x, double y, double z,
			float partialTickTime) {
		if (!seenTypes.contains(tile.getClass())) {
			Class<? extends LCTile> clazz = tile.getClass();
			seenTypes.add(clazz);
			LCLog.warn("Tile class %s fed to default renderer, check implementation!", clazz.getName());
		}
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);

		float a0 = (180.0f - Minecraft.getMinecraft().thePlayer.rotationYaw) % 360.0f;
		float a1 = -Minecraft.getMinecraft().thePlayer.rotationPitch % 360.0f;

		GL11.glTranslated(x + 0.5f, y, z + 0.5f);
		GL11.glTranslated(0f, 0.5f, 0f);
		GL11.glRotatef(a0, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(a1, 1.0f, 0.0f, 0.0f);
		GL11.glTranslatef(0f, -0.5f, 0f);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		listModel.bind();
		listModel.release();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		return true;
	}

}
