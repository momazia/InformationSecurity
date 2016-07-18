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
public class OTPDAOImpl extends DAOImpl {

	/**
	 * Saves the given currentHash for the username passed.
	 * 
	 * @param currentHash
	 * @param userName
	 */
	public void save(String currentHash, String userName) {
		ObjectifyService.ofy().save().entity(new OneTimePassword(userName, currentHash));
	}

}
