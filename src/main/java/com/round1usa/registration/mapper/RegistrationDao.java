package com.round1usa.registration.mapper;

public interface RegistrationDao {
	public void agreementInsert(String store_id, String parent_name, String relationship, String address,
			String city, String state, String zip_code, String phone, String request_datetime,
			String pdf_path);
}
