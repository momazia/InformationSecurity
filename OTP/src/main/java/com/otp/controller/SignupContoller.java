package com.otp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.otp.controller.form.UserForm;
import com.otp.controller.form.validator.SignupValidator;
import com.otp.service.UserService;

@Controller
@RequestMapping(value = "/signup")
public class SignupContoller {

	@Autowired
	private SignupValidator signupValidator;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/render", method = RequestMethod.GET)
	public ModelAndView render() {
		return new ModelAndView("signup");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, @Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {

		// Business validation
		signupValidator.validate(userForm, result);

		if (result.hasErrors()) {
			return "signup";
		}

		// Creating the user
		userService.createUser(userForm.getEmail(), userForm.getFirstName(), userForm.getLastName(), userForm.getPassword());

		return "redirect:/";
	}
}
