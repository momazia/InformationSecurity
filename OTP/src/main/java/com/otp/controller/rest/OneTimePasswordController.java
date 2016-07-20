package com.otp.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otp.controller.model.JsonResponse;
import com.otp.service.OTPService;

/**
 * The main restful service which takes care of initializing an OTP for a user
 * and verifying the user's OTP.
 * 
 * @author Max
 *
 */
@RestController
@RequestMapping(value = "/otp")
public class OneTimePasswordController {

	@Autowired
	private OTPService otpService;

	/**
	 * Returns True if the initialization was successful.
	 * 
	 * @param currentHash
	 * @return
	 */
	@RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public JsonResponse<Boolean> initialize(@RequestParam(name = "currentHash") String currentHash) {

		otpService.initiate(currentHash);

		JsonResponse<Boolean> res = new JsonResponse<>();
		res.setData(Boolean.TRUE);

		return res;
	}

	/**
	 * Validates if the given hash is valid or not.
	 * 
	 * @param hash
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public JsonResponse<Boolean> verify(@RequestParam(name = "hash") String hash) {

		boolean isValidOtp = otpService.isValid(hash);

		// If the OTP is valid, we want to update the current hash value for the
		// user.
		if (isValidOtp) {
			otpService.initiate(hash);
		}

		JsonResponse<Boolean> res = new JsonResponse<>();
		res.setData(isValidOtp);
		return res;
	}
}
