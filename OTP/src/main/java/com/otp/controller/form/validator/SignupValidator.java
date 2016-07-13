package com.otp.controller.form.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.otp.controller.form.UserForm;
import com.otp.service.UserService;

/**
 * Validator for sign up form
 * 
 * @author Max
 *
 */
public class SignupValidator extends UserInformationValidator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SignupValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm userForm = (UserForm) target;

		if (StringUtils.isNotBlank(userForm.getEmail()) && userService.isUserRegistered(userForm.getEmail())) {
			errors.rejectValue("email", "Alreadytaken.userForm.email");
		}

		if (StringUtils.isBlank(userForm.getReenterPassword())) {
			errors.rejectValue("reenterPassword", "NotEmpty.userForm.reenterPassword");
		}

		if (StringUtils.isBlank(userForm.getPassword())) {
			errors.rejectValue("password", "NotEmpty.userForm.password");
		}
		validatePasswords(errors, userForm);
	}

}
