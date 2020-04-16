package com.round1usa.registrationadminweb.dto;

public class StoreDto {

	private String store_id;
	private String store_name;
	private String insert_datetime;
	private String update_datetime;
	private String store_password;

	public StoreDto(){

	}



	public StoreDto(String store_id, String store_name, String insert_datetime, String update_datetime,
			String store_password) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.insert_datetime = insert_datetime;
		this.update_datetime = update_datetime;
		this.store_password = store_password;
	}



	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getInsert_datetime() {
		return insert_datetime;
	}

	public void setInsert_datetime(String insert_datetime) {
		this.insert_datetime = insert_datetime;
	}

	public String getUpdate_datetime() {
		return update_datetime;
	}

	public void setUpdate_datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

	public String getStore_password() {
		return store_password;
	}

	public void setStore_password(String store_password) {
		this.store_password = store_password;
	}

	@Override
	public String toString() {
		return "StoreDto [store_id=" + store_id + ", store_name=" + store_name + ", insert_datetime=" + insert_datetime
				+ ", update_datetime=" + update_datetime + ", store_password=" + store_password + "]";
	}







}
