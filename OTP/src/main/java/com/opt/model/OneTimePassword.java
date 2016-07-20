package com.opt.model;

/**
 * Business model for OTP.
 * 
 * @author Max
 *
 */
public class OneTimePassword {

	private String currentHash;
	private String userName;

	public String getCurrentHash() {
		return currentHash;
	}

	public void setCurrentHash(String currentHash) {
		this.currentHash = currentHash;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
