package SGE.api.defs;

/**
 * Game interface definition interface
 * 
 * @author Exodus Games
 *
 */
public interface IInterfaceDefinition extends IGameDef {
	/**
	 * Get the name of the interface
	 * 
	 * @return The name of the interface
	 */
	public String getName();

	/**
	 * Get the class of the Container object
	 * 
	 * @return The Container class
	 */
	public String getContainerClass();

	/**
	 * Get the class of the GUI object
	 * 
	 * @return The GUI object
	 */
	public String getGUIClass();

	/**
	 * Get the GUI ID of the definition
	 * 
	 * @return The GUI ID of the definition
	 */
	public int getGUIID();

	/**
	 * Set the GUI ID of the definition
	 * 
	 * @param guiId
	 *            The GUI ID of the definition
	 */
	public void setGUIID(int guiId);

}
