package SGE.client.render.animations;

import net.minecraft.world.World;
import SGE.SGERuntime;
import SGE.client.animation.Animation;
import SGE.client.render.gfx.particle.GFXDust;
import SGE.common.base.SGETile;
import SGE.common.util.data.StateMap;
import SGE.common.util.game.RunnableTileCallback;
import SGE.common.util.math.Vector3;
import SGE.tiles.TileStargateBase;

public class RingSpinAnimation extends Animation {

	public RingSpinAnimation(Double duration, Double start, Double end, boolean resample) {
		super(duration, resample, new RunnableTileCallback() {
			@Override
			public void run(LCTile tile) {
				tile.mixer().replayChannel("spin");
				Vector3[] chevrons = ((TileStargateBase) tile).getChevronBlocks();
				World world = tile.getWorldObj();
				for (int i = 0; i < chevrons.length; i++) {
					if (world.rand.nextInt(2) == 1) {
						Vector3 chevron = chevrons[i];
						GFXDust spark = new GFXDust(world, chevron.x, chevron.y, chevron.z, 0.024f, 0.0f, 0.02f);
						LCRuntime.runtime.hints().particles().placeParticle(world, spark);
					}
				}
			}
		}, new RunnableTileCallback() {
			@Override
			public void run(SGETile tile) {
				tile.mixer().stopChannel("spin");
			}
		});
		addProperty("ring-rotation", start, end, InterpolationMode.SIN);
	}

	@Override
	public void resampleProperties(StateMap map) {
		super.resampleProperties(map);
		/** TODO: Compute how we really want to rotate */
	}

}
