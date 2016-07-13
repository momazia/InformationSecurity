package com.otp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.otp.controller.form.LoginForm;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@RequestMapping(value = "/render", method = RequestMethod.GET)
	public ModelAndView render() {
		return new ModelAndView("login", "loginForm", new LoginForm());
	}

}
