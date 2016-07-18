package com.otp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.otp.dao.OTPDAOImpl;
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
	 * @param currentHash
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

}
