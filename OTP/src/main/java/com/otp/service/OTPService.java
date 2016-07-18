package com.otp.service;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.otp.dao.OTPDAOImpl;
import com.otp.datastore.entity.OneTimePassword;
import com.otp.logger.CustomLogger;

/**
 * Takes care of all the OTP related business logics
 * 
 * @author Max
 *
 */
@Service
public class OTPService {

	private static final CustomLogger LOG = CustomLogger.getLogger(OTPService.class.getName());

	@Autowired
	private OTPDAOImpl otpDao;

	/**
	 * Initiates OTP by saving the current hash for the user who is currently
	 * logged into the system.
	 * 
	 * @param seed
	 */
	public void initiate(String currentHash) {
		// Getting the current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		// Registering the OTP for the first time.
		LOG.info("Initiating OTP for user [" + userName + "]...");
		otpDao.save(currentHash, userName);
		LOG.info("OTP for user [" + userName + "] is registered successfully.");
	}

	/**
	 * Validates to see if the input string passed is the same as the one on the
	 * server by hashing it once and comparing the result.
	 * 
	 * @param input
	 * @return
	 */
	public boolean isValid(String input) {
		try {
			String encryptedInput = hash(input);
			String savedHash = getCurrentUserHash();
			return encryptedInput.equals(savedHash);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Cannot apply hash function", e);
		}
		return false;
	}

	/**
	 * Gets the current user's hash value.
	 * 
	 * @return
	 */
	private String getCurrentUserHash() {
		// Getting the current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		OneTimePassword otpResult = otpDao.find(userName);
		return otpResult.getCurrentHash();
	}

	/**
	 * Applies SHA-1 to the input passed.
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String hash(String input) throws NoSuchAlgorithmException {
		return DigestUtils.sha1Hex(input);
	}

}
