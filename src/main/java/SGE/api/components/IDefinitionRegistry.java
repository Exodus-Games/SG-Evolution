/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.components;

import SGE.api.defs.IContainerDefinition;

/**
 * Definition registry interface. Stores copies of all known definitions at
 * runtime.
 *
 * @author Exodus Games
 *
 */
public interface IDefinitionRegistry {

	/**
	 * Add a new game definition
	 *
	 * @param definition
	 *            The definition element
	 */
	public void addDefinition(IContainerDefinition definition);

	/**
	 * Get a named definition from this registry
	 *
	 * @param name
	 *            The definition name
	 * @return The definition or null if it does not exist
	 */
	public IContainerDefinition getDefinition(String name);

}
