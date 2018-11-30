package SGE.common.base.multiblock;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import SGE.SGERuntime;
import SGE.common.SGELog;
import SGE.common.base.SGETile;
import SGE.common.network.SGENetworkException;
import SGE.common.network.SGEPacket;
import SGE.common.network.packets.LCMultiblockPacket;
import SGE.common.util.Tracer;
import SGE.common.util.math.DimensionPos;
import SGE.common.util.math.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

/**
 * Internal multi-block implementation.
 *
 * @author Exodus Games
 *
 */
public abstract class SGEMultiblockTile extends LCTile {

	/**
	 * The multi-block metadata compound. If you change this, you should call
	 * {@link SGEMultiblockTile#markMultiblockDirty()} to send the change to all
	 * clients within range.
	 */
	private NBTTagCompound multiblockCompound = new NBTTagCompound();
	private boolean multiblockNbtDirty = false;
	private boolean isSlave = false;

	/**
	 * Set this multi-block to a slave state
	 *
	 * @param state
	 *            The state to set
	 */
	public void setSlave(boolean state) {
		isSlave = state;
	}

	/**
	 * Get this multi-block's slave state
	 *
	 * @return The state
	 */
	public boolean isSlave() {
		return isSlave;
	}

	/**
	 * Change the multi-block state.
	 *
	 * @param next
	 *            The state to transition to.
	 */
	protected void changeState(MultiblockState next) {
		if (isSlave)
			LCLog.fatal(new OperationNotSupportedException("Not allowed to changeState on a slave."));
		else {
			if (multiblockCompound.hasKey("state")
					&& MultiblockState.fromOrdinal(multiblockCompound.getInteger("state")) == next)
				return;
			multiblockCompound.setInteger("state_next", next.ordinal());
		}
	}

	/**
	 * Set the owner location of this block
	 *
	 * @param owner
	 *            The owner location
	 */
	public void setOwner(Vector3 owner) {
		if (!isSlave)
			LCLog.fatal(new OperationNotSupportedException("Not allowed to setOwner on a master."));
		else {
			if (owner == null)
				multiblockCompound.removeTag("owner");
			else
				multiblockCompound.setTag("owner", owner.toNBT());
			markMultiblockDirty();
		}
	}

	/**
	 * Get the state of this multi-block
	 *
	 * @return The state of this multi-block
	 */
	public MultiblockState getState() {
		if (isSlave) {
			if (!multiblockCompound.hasKey("owner"))
				return MultiblockState.NONE;
			Vector3 owner = Vector3.from(multiblockCompound.getCompoundTag("owner"));
			TileEntity tile = worldObj.getTileEntity(new BlockPos(owner.fx(), owner.fy(), owner.fz()));
			if (!(tile instanceof SGEMultiblockTile))
				return MultiblockState.NONE;
			return ((SGEMultiblockTile) tile).getState();
		} else {
			if (!multiblockCompound.hasKey("state"))
				return MultiblockState.NONE;
			return MultiblockState.fromOrdinal(multiblockCompound.getInteger("state"));
		}
	}

	/**
	 * Get the next-state of the multi-block.
	 *
	 * @return The next state.
	 */
	public MultiblockState nextState() {
		if (!multiblockCompound.hasKey("state_next"))
			return null;
		return MultiblockState.fromOrdinal(multiblockCompound.getInteger("state_next"));
	}

	/**
	 * Mark the multi-block dirty.
	 */
	public void markMultiblockDirty() {
		multiblockNbtDirty = true;
	}

	/**
	 * Get the structure configuration for this multiblock.
	 *
	 * @return The structure configuration instance.
	 */
	public abstract StructureConfiguration getConfiguration();

	/** Called on the server to perform multiblock logic update */
	public abstract void thinkMultiblock();

	@Override
	public void thinkServerPost() {
		super.thinkServerPost();
		Tracer.begin(this);
		thinkMultiblock();
		MultiblockState next = nextState();
		if (next != null && next != getState()) {
			multiblockCompound.setInteger("state", next.ordinal());
			multiblockNbtDirty = true;
		}

		if (multiblockNbtDirty) {
			multiblockNbtDirty = false;
			SGEMultiblockPacket update = new SGEMultiblockPacket(new DimensionPos(this), multiblockCompound);
			SGERuntime.runtime.network().getPreferredPipe().sendToAllAround(update, update.target, 128.0d);
		}
		Tracer.end();
	}

	@Override
	public void thinkPacket(LCPacket packet, EntityPlayer player) throws SGENetworkException {
		Tracer.begin(this);
		if (packet instanceof SGEMultiblockPacket)
			if (worldObj.isRemote) {
				multiblockCompound = ((SGEMultiblockPacket) packet).compound;
				worldObj.markBlockForUpdate(getPos());
			}
		Tracer.end();
	}

	@Override
	public void sendPackets(List<LCPacket> packets) throws SGENetworkException {
		super.sendPackets(packets);
		Tracer.begin(this);
		packets.add(new SGEMultiblockPacket(new DimensionPos(this), multiblockCompound));
		Tracer.end();
	}

	@Override
	public void save(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

}
