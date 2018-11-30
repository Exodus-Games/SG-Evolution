/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.rendering;

import SGE.common.util.data.StateMap;

/**
 * Contract interface for tile entity rendering information providers.
 *
 * @author Exodus Games
 *
 */
public interface ITileRenderInfo {

	/**
	 * Get the rendering state map.
	 * 
	 * @return The state map.
	 */
	public StateMap tileRenderState();

	public Object tileAnimation();

	public double tileAnimationProgress();

}
