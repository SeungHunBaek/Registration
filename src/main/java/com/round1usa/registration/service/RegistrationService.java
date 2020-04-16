package com.round1usa.registration.service;

import java.util.Map;

public interface RegistrationService {
	public void insert(Map<String, Object> inputData, String pdfPath);
}