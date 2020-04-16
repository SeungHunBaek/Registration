package com.round1usa.registration.service;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.round1usa.registration.mapper.RegistrationDao;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationDao mapper;

	public void insert(Map<String, Object> inputData, String pdfPath_) {

		String storeId      = (String) inputData.get("storeId");
		String parentName   = (String) inputData.get("parentName");
		String phone        = (String) inputData.get("phone");
		String relationship = (String) inputData.get("relationship");
		String address      = (String) inputData.get("address");
		String city         = (String) inputData.get("city");
		String state        = (String) inputData.get("state");
		String zipCode      = (String) inputData.get("zipCode");

		String pdfPath = pdfPath_.replace('\\', '/');

		mapper.agreementInsert(
				storeId, 												// store_id
				parentName,
				relationship, 											// relationship
				address, 												// address
				city, 													// city
				state, 													// state
				zipCode, 												// zip_code
				phone,
				new Timestamp(System.currentTimeMillis()).toString(),	// request_datetime
				pdfPath);
	}
}
