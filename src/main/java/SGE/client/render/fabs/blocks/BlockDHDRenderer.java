package SGE.client.render.fabs.blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import SGE.api.stargate.StargateType;
import SGE.client.models.ModelDHD;
import SGE.common.base.SGEBlockRenderer;
import SGE.common.configuration.xml.ComponentConfig;

/**
 * Stargate DHD block renderer
 * 
 * @author Exodus Games
 *
 */
public class BlockDHDRenderer extends SGEBlockRenderer {

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<? extends SGEBlockRenderer> getParent() {
		return null;
	}

	@Override
	public boolean renderInventoryBlock(Block block, RenderBlocks renderer, int metadata) {
		if (ModelDHD.$ == null)
			return false;
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(0.5f, 0.5f, 0.0f);
		GL11.glRotatef(45, 0, 1, 0);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		GL11.glTranslatef(-0.25f, -0.5f, 0.0f);
		GL11.glScalef(0.8f, 0.8f, 0.8f);
		ModelDHD.$.prepareAndRender(StargateType.fromOrdinal(metadata), false);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		return true;
	}

	@Override
	public boolean renderWorldBlock(Block block, RenderBlocks renderer, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean renderInventoryItemAs3d() {
		return true;
	}

}
