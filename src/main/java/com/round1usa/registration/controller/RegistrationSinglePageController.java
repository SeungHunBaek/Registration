package com.round1usa.registration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationSinglePageController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "single/index";
	}
}
