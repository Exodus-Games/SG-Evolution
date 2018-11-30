/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api;

/**
 * Mod info access interface
 *
 * @author Exodus Games
 *
 */
public interface IModInfo {

	/**
	 * Get the asset key for the mod.
	 *
	 * @return The system asset key.
	 */
	public String assets();

	/**
	 * Get the build number of the mod.
	 *
	 * @return The build number.
	 */
	public int build();

	/**
	 * Ask the mod if this is a development workspace or a development build of
	 * the mod.
	 *
	 * @return If the mod is an experimental build.
	 */
	public boolean development();

}
