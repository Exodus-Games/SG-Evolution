package SGE.client.models;

import SGE.client.models.loader.WavefrontModel;
import SGE.common.LCLog;
import SGE.common.resource.ResourceAccess;

public class ModelTransportRing extends WavefrontModel {

	public static ModelTransportRing $;

	static {
		try {
			$ = new ModelTransportRing();
		} catch (WavefrontModelException mfe) {
			SGELog.warn("Failed to load ModelTransportRing, model error", mfe);
		}
	}

	public ModelTransportRing() throws WavefrontModelException {
		super(ResourceAccess.getNamedResource("models/model_transport_ring.obj"));
	}

	public void prepareAndRender() {
		renderAll();
	}

}
