package SGE.client.models;

import SGE.api.stargate.StargateType;
import SGE.client.models.loader.WavefrontModel;
import SGE.common.LCLog;
import SGE.common.resource.ResourceAccess;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ModelDHD extends WavefrontModel {

	public static ModelDHD $;

	static {
		try {
			$ = new ModelDHD();
		} catch (WavefrontModelException mfe) {
			LCLog.warn("Failed to load ModelDHD, model error", mfe);
		}
	}

	public ModelDHD() throws WavefrontModelException {
		super(ResourceAccess.getNamedResource("models/model_dhd.obj"));
	}

	public void prepareAndRender(StargateType type, boolean state) {
		String typename = (type.getSuffix().length() != 0) ? "dhd_%s_" + type.getSuffix() : "dhd_%s";
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResourceAccess.getNamedResource(ResourceAccess
				.formatResourceName("textures/models/%s_${TEX_QUALITY}.png",
						String.format(typename, (state) ? "on" : "off"))));
		renderAll();
	}

}
