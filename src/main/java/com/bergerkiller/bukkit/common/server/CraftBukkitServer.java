package com.bergerkiller.bukkit.common.server;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.utils.StringUtil;

public class CraftBukkitServer implements CommonServer {
	/**
	 * Defines the Package Version
	 */
	public String PACKAGE_VERSION;
	/**
	 * Defines the Minecraft Version
	 */
	public String MC_VERSION;
	/**
	 * Defines the net.minecraft.server root path
	 */
	public String NMS_ROOT_VERSIONED;
	/**
	 * Defines the org.bukkit.craftbukkit root path
	 */
	public String CB_ROOT_VERSIONED;

	@Override
	public boolean init() {
		// Find out what package version is used
		String serverPath = Bukkit.getServer().getClass().getName();
		if (!serverPath.startsWith(Common.CB_ROOT)) {
			return false;
		}
		PACKAGE_VERSION = StringUtil.getBefore(serverPath.substring(Common.CB_ROOT.length() + 1), ".");

		// Obtain the versioned roots
		if (PACKAGE_VERSION.isEmpty()) {
			NMS_ROOT_VERSIONED = Common.NMS_ROOT;
			CB_ROOT_VERSIONED = Common.CB_ROOT;
		} else {
			NMS_ROOT_VERSIONED = Common.NMS_ROOT + "." + PACKAGE_VERSION;
			CB_ROOT_VERSIONED = Common.CB_ROOT + "." + PACKAGE_VERSION;
		}

		// Figure out the MC version from the server
		String version = PACKAGE_VERSION;
		try {
			// Load required classes
			Class<?> server = Class.forName(getClassName(Common.CB_ROOT + ".CraftServer"));
			Class<?> minecraftServer = Class.forName(getClassName(Common.NMS_ROOT + ".MinecraftServer"));
			// Get methods and instances
			Method getServer = server.getDeclaredMethod("getServer");
			Object minecraftServerInstance = getServer.invoke(Bukkit.getServer());
			Method getVersion = minecraftServer.getDeclaredMethod("getVersion");
			// Get the version
			version = (String) getVersion.invoke(minecraftServerInstance);
		} catch (Throwable t) {
		}
		MC_VERSION = version;
		return true;
	}

	@Override
	public String getClassName(String path) {
		if (path.startsWith(Common.NMS_ROOT) && !path.startsWith(NMS_ROOT_VERSIONED)) {
			return NMS_ROOT_VERSIONED + path.substring(Common.NMS_ROOT.length());
		}
		if (path.startsWith(Common.CB_ROOT) && !path.startsWith(CB_ROOT_VERSIONED)) {
			return CB_ROOT_VERSIONED + path.substring(Common.CB_ROOT.length());
		}
		return path;
	}

	@Override
	public String getMethodName(Class<?> type, String methodName, Class<?>... params) {
		return methodName;
	}

	@Override
	public String getFieldName(Class<?> type, String fieldName) {
		return fieldName;
	}

	@Override
	public boolean isCompatible() {
		return PACKAGE_VERSION.isEmpty() || PACKAGE_VERSION.equals(Common.DEPENDENT_MC_VERSION);
	}

	@Override
	public String getMinecraftVersion() {
		return MC_VERSION;
	}

	@Override
	public String getServerVersion() {
		return (PACKAGE_VERSION.isEmpty() ? "(Unknown)" : PACKAGE_VERSION) + " (Minecraft " + MC_VERSION + ")";
	}

	@Override
	public String getServerName() {
		return "CraftBukkit (" + Bukkit.getServer().getVersion() + ")";
	}
}
