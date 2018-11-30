/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.rendering;

/**
 * Contract interface for rendering information providers.
 *
 * @author Exodus Games
 *
 */
public interface IRenderInfo {

	/**
	 * @return A block rendering info provider
	 */
	public IBlockRenderInfo renderInfoBlock();

	/**
	 * @return A tile rendering info provider
	 */
	public ITileRenderInfo renderInfoTile();

	/**
	 * @return An entity rendering info provider
	 */
	public IEntityRenderInfo renderInfoEntity();
}
