package com.otp.datastore.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Entity to represent OTP
 * 
 * @author Max
 *
 */
@Entity(name = "OTP")
@Cache
public class OneTimePassword {

	@Id
	@Index
	private String userName;
	private String currentHash;

	public OneTimePassword() {
		super();
	}

	public OneTimePassword(String userName, String currentHash) {
		super();
		this.userName = userName;
		this.currentHash = currentHash;
	}

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
