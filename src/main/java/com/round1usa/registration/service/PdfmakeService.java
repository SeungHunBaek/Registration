package com.round1usa.registration.service;

import java.io.OutputStream;
import java.util.Map;

public interface PdfmakeService {
	public int callPdfmake(Map<String, Object> request, OutputStream output) throws Exception;
}
