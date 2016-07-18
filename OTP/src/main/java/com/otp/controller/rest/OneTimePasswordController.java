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
	@RequestMapping(value = "/initiate", method = RequestMethod.GET)
	public JsonResponse<Boolean> initiate(@RequestParam(name = "currentHash") String currentHash) {
		JsonResponse<Boolean> res = new JsonResponse<>();
		otpService.initiate(currentHash);
		res.setData(Boolean.TRUE);
		return res;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public JsonResponse<Boolean> verify() {
		JsonResponse<Boolean> res = new JsonResponse<>();
		res.setData(Boolean.FALSE);
		return res;
	}
}
