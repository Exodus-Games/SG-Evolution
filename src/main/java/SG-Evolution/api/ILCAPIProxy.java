/**
 * This file is part of the official Exodus Games API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api;

import SGE.api.components.IRegistryContainer;
import SGE.api.init.Biomes;
import SGE.api.init.Blocks;
import SGE.api.init.Dimensions;
import SGE.api.init.Entities;
import SGE.api.init.Interfaces;
import SGE.api.init.Items;
import SGE.api.init.Recipes;
import SGE.api.init.Structures;

/**
 * API proxy interface
 *
 * @author Exodus Games
 *
 */
public interface ISGEAPIProxy {

	/**
	 * Get the current Exodus Games build and runtime information.
	 *
	 * @return The current Exodus Games build and runtime information proxy.
	 */
	public IModInfo info();

	/**
	 * Get the signed state of the mod.
	 *
	 * @return If the mod is signed.
	 */
	public boolean signedState();

	/**
	 * Get the current Exodus Games block registry
	 *
	 * @return The list of blocks
	 */
	public Blocks blocks();

	/**
	 * Get the current Exodus Games item registry
	 *
	 * @return The list of items
	 */
	public Items items();

	/**
	 * Get the current Exodus Games entity registry
	 *
	 * @return The list of entities
	 */
	public Entities entities();

	/**
	 * Get the registry container
	 *
	 * @return The registry container
	 */
	public IRegistryContainer registries();

	/**
	 * Get the list of biomes
	 *
	 * @return The list of biomes
	 */
	public Biomes biomes();

	/**
	 * Get the list of dimensions
	 *
	 * @return The list of dimensions
	 */
	public Dimensions dimensions();

	/**
	 * Get the list of recipes
	 *
	 * @return The list of recipes
	 */
	public Recipes recipes();

	/**
	 * Get the list of structures
	 *
	 * @return The list of structures
	 */
	public Structures structures();

	/**
	 * Get the list of interfaces
	 *
	 * @return The list of interfaces
	 */
	public Interfaces interfaces();

}
