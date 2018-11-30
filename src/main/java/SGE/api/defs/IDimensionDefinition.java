/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.defs;

import net.minecraft.world.WorldProvider;

/**
 * Dimension definition interface
 *
 * @author Exodus Games
 *
 */
public interface IDimensionDefinition extends IGameDef {

	/**
	 * Get the class responsible for providing the world management.
	 *
	 * @return The class responsible for world management.
	 */
	public Class<? extends WorldProvider> getWorldProviderClass();

}
