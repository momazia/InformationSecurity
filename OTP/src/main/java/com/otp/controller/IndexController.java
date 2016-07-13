package com.otp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main controller in charge of rendering the main page.
 * 
 * @author Max
 *
 */
@Controller
public class IndexController {

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView renderIndex() {
		return new ModelAndView("index");
	}

}
