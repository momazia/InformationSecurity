package com.otp.util;

/**
 * A singleton utility class for OTP.
 * 
 * @author Max
 *
 */
public enum OTPUtils {

	INSTANCE;

	private OTPUtils() {
	}

	public static OTPUtils GetInstance() {
		return INSTANCE;
	}

}
