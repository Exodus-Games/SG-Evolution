package SGE.client.openal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import SGE.SGERuntime;
import SGE.api.audio.ISoundController;
import SGE.api.audio.channel.IMixer;
import SGE.api.audio.streaming.ISoundPosition;
import SGE.api.audio.streaming.ISoundServer;
import SGE.api.event.ITickEventHandler;
import SGE.common.SGELog;
import SGE.common.util.java.DestructableReference;
import SGE.common.util.math.DimensionPos;

/**
 * Client sound controller implementation.
 * 
 * @author Exodus Games
 *
 */
public class ClientSoundController implements ISoundController, ITickEventHandler {

	private final ISoundServer server;

	private final HashMap<DestructableReference<Object>, IMixer> liveMixers;

	/** Default constructor */
	public ClientSoundController() {
		SGERuntime.runtime.ticks().register(this);
		this.server = new StreamingSoundServer();
		this.liveMixers = new HashMap<DestructableReference<Object>, IMixer>();
		this.server.initialize();
	}

	@Override
	public boolean ready() {
		return server != null && server.ready();
	}

	@Override
	public ISoundPosition getPosition(Object object) {
		DimensionPos pos = null;
		if (object instanceof NBTTagCompound) {
			pos = new DimensionPos((NBTTagCompound) object);
		} else if (object instanceof TileEntity) {
			pos = new DimensionPos((TileEntity) object);
		} else
			return null;
		return new StreamingSoundPosition(pos);
	}

	@Override
	public ISoundServer getSoundService() {
		return server;
	}

	@Override
	public IMixer findMixer(Object key) {
		Iterator<Entry<DestructableReference<Object>, IMixer>> itr = liveMixers.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<DestructableReference<Object>, IMixer> mz = itr.next();
			if (mz.getKey().get().equals(key))
				return mz.getValue();
		}
		StreamingSoundMixer mixer = new StreamingSoundMixer(this, key);
		liveMixers.put(new DestructableReference<Object>(key), mixer);
		return mixer;
	}

	@Override
	public void think(Side what) {
		if (what == Side.CLIENT) {
			server.think();
			Iterator<Entry<DestructableReference<Object>, IMixer>> iter = liveMixers.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<DestructableReference<Object>, IMixer> mixer = iter.next();
				DestructableReference<Object> ref = mixer.getKey();
				if (ref.get() != null) {
					mixer.getValue().think();
				} else {
					SGELog.debug("Cleaning up orphaned sound mixer %s", mixer);
					mixer.getValue().shutdown(true);
					iter.remove();
				}
			}
		}
	}

}
