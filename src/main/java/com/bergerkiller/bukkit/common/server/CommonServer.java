package com.bergerkiller.bukkit.common.server;

public interface CommonServer {

	/**
	 * Tries to initialize the server
	 * 
	 * @return True if initializing was successful, False if not
	 */
	public boolean init();

	/**
	 * Gets the versioning information of the server
	 * 
	 * @return server versioning description
	 */
	public String getServerVersion();

	/**
	 * Gets the full name of the server
	 * 
	 * @return server name
	 */
	public String getServerName();

	/**
	 * Gets the real Class name for the given Path, allowing Class path translations to occur
	 * 
	 * @param path to the Class
	 * @return the real Class path
	 */
	public String getClassName(String path);

	/**
	 * Obtains the real method name for a given method, allowing name translations to occur
	 * 
	 * @param type of Class the method is in
	 * @param methodName of the method
	 * @param params of the method
	 * @return the (translated) method name
	 */
	public String getMethodName(Class<?> type, String methodName, Class<?>... params);

	/**
	 * Obtains the real field name for a given field, allowing name translations to occur
	 * 
	 * @param type of Class the field is in
	 * @param fieldName of the field
	 * @return the (translated) field name
	 */
	public String getFieldName(Class<?> type, String fieldName);

	/**
	 * Checks whether BKCommonLib is compatible with this server
	 * 
	 * @return True if compatible, False if not
	 */
	public boolean isCompatible();

	/**
	 * Gets the version of Minecraft the server supports
	 * 
	 * @return Minecraft version
	 */
	public String getMinecraftVersion();
}
