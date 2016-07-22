package com.security.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the information for each of the files.
 * 
 * @author Max
 *
 */
public class AccessControl {

	private String fileName;
	// Key: username, Value: access right
	private Map<String, Integer> userAccesses = new HashMap<>();

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Integer> getUserAccesses() {
		return userAccesses;
	}

	public void setUserAccesses(Map<String, Integer> userAccesses) {
		this.userAccesses = userAccesses;
	}

}
