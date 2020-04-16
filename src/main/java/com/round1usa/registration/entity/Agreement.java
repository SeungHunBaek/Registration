package com.round1usa.registration.entity;

public class Agreement {
	private int info_num;
	private String store_id;
	private String parent_name;
	private String relationship;
	private String address;
	private String city;
	private String state;
	private String zip_code;
	private String country_code;
	private String phone;
	private String request_datetime;
	private String pdf_path;
	private String store_name;

	public Agreement() {	}

	public Agreement(int info_num, String store_id, String parent_name, String relationship, String address,
			String city, String state, String zip_code, String country_code, String phone, String request_datetime,
			String pdf_path,String store_name) {
		super();
		this.info_num = info_num;
		this.store_id = store_id;
		this.parent_name = parent_name;
		this.relationship = relationship;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip_code = zip_code;
		this.country_code = country_code;
		this.phone = phone;
		this.request_datetime = request_datetime;
		this.pdf_path = pdf_path;
		this.store_name = store_name;
	}



	public int getInfo_num() {
		return info_num;
	}

	public void setInfo_num(int info_num) {
		this.info_num = info_num;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRequest_datetime() {
		return request_datetime;
	}
	public void setRequest_datetime(String request_datetime) {
		this.request_datetime = request_datetime;
	}
	public String getPdf_path() {
		return pdf_path;
	}
	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}

	@Override
	public String toString() {
		return "Agreement [info_num=" + info_num + "store_id=" + store_id + ", parent_name=" + parent_name
				+ ", relationship=" + relationship + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", zip_code=" + zip_code + ", country_code=" + country_code + ", phone=" + phone
				+ ", request_datetime=" + request_datetime + ", pdf_path=" + pdf_path + ", store_name=" + store_name
				+ "]";
	}




}
