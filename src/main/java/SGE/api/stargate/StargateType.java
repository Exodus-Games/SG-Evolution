/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api.stargate;

/**
 * Stargate type list
 *
 * @author Exodus Games
 *
 */
public enum StargateType {
	/** Standard */
	STANDARD("stargate.standard", ""),
	/** Atlantis */
	ATLANTIS("stargate.pegasus", "pegasus");

	private final String name;
	private final String suffix;

	StargateType(String name, String suffix) {
		this.name = name;
		this.suffix = suffix;
	}

	/**
	 * Get the name of the type
	 *
	 * @return The name of the type
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the data suffix of the type
	 *
	 * @return The suffix of the type
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * Get a type from an ordinal
	 *
	 * @param ordinal
	 *            The numeric ordinal
	 * @return The type
	 */
	public static StargateType fromOrdinal(int ordinal) {
		return StargateType.values()[ordinal];
	}

	/**
	 * Get a count of all types
	 *
	 * @return The count of all types
	 */
	public static int count() {
		return values().length;
	}

}
