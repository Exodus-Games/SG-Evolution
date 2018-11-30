package SGE.api.components;

import SGE.api.defs.IStructureDefinition;

/**
 * Structure registry interface. Stores all declared structures at runtime.
 * 
 * @author Exodus Games
 *
 */
public interface IStructureRegistry {

	/**
	 * Register a new game definition
	 *
	 * @param definition
	 *            The definition element
	 */
	public void register(IStructureDefinition definition);

	/**
	 * Get a named definition from this registry
	 *
	 * @param name
	 *            The definition name
	 * @return The definition or null if it does not exist
	 */
	public IStructureDefinition getDefinition(String name);

}
