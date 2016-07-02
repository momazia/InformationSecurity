package com.security.util;

public class CRCUtils {
	/**
	 * static Singleton instance
	 */
	private static CRCUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private CRCUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static CRCUtils getInstance() {
		if (instance == null) {
			instance = new CRCUtils();
		}
		return instance;
	}

}
