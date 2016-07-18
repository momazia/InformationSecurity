package com.otp.dao;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.ObjectifyService;
import com.otp.datastore.entity.OneTimePassword;

/**
 * DAO for OTP related operations
 * 
 * @author Max
 *
 */
@Repository
public class OTPDAOImpl {

	/**
	 * Saves the given currentHash for the username passed.
	 * 
	 * @param currentHash
	 * @param userName
	 */
	public void save(String currentHash, String userName) {
		ObjectifyService.ofy().save().entity(new OneTimePassword(userName, currentHash));
	}

	/**
	 * Gets the OneTimePassword data saved for user name passed.
	 * 
	 * @param userName
	 * @return
	 */
	public OneTimePassword find(String userName) {
		return ObjectifyService.ofy().load().type(OneTimePassword.class).id(userName).now();
	}

}
