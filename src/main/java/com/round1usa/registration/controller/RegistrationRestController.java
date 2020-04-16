package com.round1usa.registration.controller;

import java.io.BufferedOutputStream;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.round1usa.registration.service.PdfmakeService;

@RestController
@RequestMapping(value = "/rest")
public class RegistrationRestController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PdfmakeService pdfmakeService;

	@RequestMapping(value = "/pdfmake", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void pdfmake(Locale locale, HttpServletResponse response, @RequestBody Map<String, Object> request) throws Exception {
		BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());

		int bytes = pdfmakeService.callPdfmake(request, output);

		response.setContentType("application/pdf");
		response.setContentLength((int) bytes);

		output.flush();
	}
}
