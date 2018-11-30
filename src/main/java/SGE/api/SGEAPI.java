/**
 * This file is part of the official SG Evolution API. Please see the usage guide and
 * restrictions on use in the package-info file.
 */
package SGE.api;

/**
 * <p>
 * SG Evolution's main API access class. Use this class to get a reference to the
 * SG Evolution mod at runtime.
 * </p>
 * <p>
 * <b>Do not use reflection or other methods to access the runtime instance of
 * the mod.</b>
 * </p>
 *
 * @author Exodus Games
 *
 */
public class SGEAPI {

	/** Runtime cache container */
	private static ISGEAPIProxy runtime = null;

	/**
	 * <p>
	 * Fetch the current SG Evolution API runtime element. If SG Evolution has
	 * been loaded by FML, this will return the current API runtime access
	 * instance. If SG Evolution has not been loaded, this will return null.
	 * </p>
	 *
	 * <p>
	 * <b>Do not use reflection or other methods to access the runtime instance
	 * of the mod.</b>
	 * </p>
	 *
	 * @return The current SG Evolution API runtime.
	 */
	public static ISGEAPIProxy runtime() {
		if (runtime == null)
			try {
				Class<?> rtc = Class.forName("SGE.SGERuntime");
				runtime = (ISGEAPIProxy) rtc.getField("runtime").get(rtc);
			} catch (Throwable e) {
				return null;
			}
		return runtime;
	}

}
