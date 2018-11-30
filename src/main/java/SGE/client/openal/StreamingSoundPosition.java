package SGE.client.openal;

import net.minecraftforge.common.DimensionManager;
import SGE.api.audio.streaming.ISoundPosition;
import SGE.common.util.math.DimensionPos;
import SGE.common.util.math.Vector3;

public class StreamingSoundPosition implements ISoundPosition {

	private DimensionPos position;

	public StreamingSoundPosition(DimensionPos pos) {
		this.position = pos;
	}

	@Override
	public Object getWorldObject() {
		return DimensionManager.getWorld(position.dimension);
	}

	@Override
	public Object getPositionObject() {
		return new Vector3(position.x, position.y, position.z);
	}

}
