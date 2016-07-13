package com.otp.controller.form.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.otp.controller.form.UserForm;
import com.otp.util.PasswordUtils;

public abstract class UserInformationValidator implements Validator {

	/**
	 * Validates the passwords entered by the user, by checking if both matching
	 * and the password matches the minimum criteria defined in regexp.
	 * 
	 * @param errors
	 * @param signupForm
	 */
	protected void validatePasswords(Errors errors, UserForm signupForm) {

		if (StringUtils.isNotEmpty(signupForm.getPassword()) || StringUtils.isNotEmpty(signupForm.getReenterPassword())) {

			if (!signupForm.getPassword().equals(signupForm.getReenterPassword())) {
				errors.rejectValue("password", "Mismatch.userForm.passwords");
			}

			if (!PasswordUtils.getInstance().validatePasswordFormat(signupForm.getPassword())) {
				errors.rejectValue("password", "Invalid.userForm.password");
			}
		}
	}
}
