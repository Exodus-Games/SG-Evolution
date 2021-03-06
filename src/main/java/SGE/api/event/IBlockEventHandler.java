/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.event;

/**
 * Contract interface for block-event handlers
 *
 * @author Exodus Games
 *
 */
public interface IBlockEventHandler {

	/** Called when the host block is placed */
	public void blockPlaced();

	/** Called when the host block is broken */
	public void blockBroken();
	
	/** Called when a neighbor block is changed */
	public void neighborChanged();

}
