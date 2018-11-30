package SGE.client.openal.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import SGE.common.SGELog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

/**
 * Streaming sound on-disk file connection between IResource and the OpenAL
 * driver.
 * 
 * @author Exodus Games
 *
 */
public class StreamingSoundFileConnection extends URLConnection {

	private final ResourceLocation resourceName;
	/** The stream protocol handler */
	protected final StreamingSoundProtocolHandler handler;

	public StreamingSoundFileConnection(URL url, StreamingSoundProtocolHandler proto) {
		super(url);
		resourceName = new ResourceLocation(url.getPath());
		handler = proto;
	}

	@Override
	public void connect() {
	}

	@Override
	public InputStream getInputStream() {
		try {
			SGELog.debug("Looking for resource: %s", resourceName.toString());
			IResource rsrc = Minecraft.getMinecraft().getResourceManager().getResource(resourceName);
			InputStream stream = rsrc.getInputStream();
			if (stream == null)
				throw new NullPointerException("stream == null!");
			return stream;
		} catch (Exception ex) {
			SGELog.warn("Can't open sound stream", ex);
			return null;
		}
	}
}
