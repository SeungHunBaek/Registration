package com.round1usa.registrationadminweb.dto;

public class Participant {
	private int info_num;
	private String participant_name;
	private String participant_gender;
	private String participant_birth;
	private String request_datetime;

	private String parent_name;
	private String phone;
	private String pdf_path;

	public Participant() {}

	public Participant(int info_num, String participant_name, String participant_gender, String participant_birth,
			String request_datetime) {
		this.info_num = info_num;
		this.participant_name = participant_name;
		this.participant_gender = participant_gender;
		this.participant_birth = participant_birth;
		this.request_datetime = request_datetime;
	}







	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPdf_path() {
		return pdf_path;
	}

	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}

	public int getInfo_num() {
		return info_num;
	}

	public void setInfo_num(int info_num) {
		this.info_num = info_num;
	}

	public String getParticipant_name() {
		return participant_name;
	}

	public void setParticipant_name(String participant_name) {
		this.participant_name = participant_name;
	}

	public String getParticipant_gender() {
		return participant_gender;
	}

	public void setParticipant_gender(String participant_gender) {
		this.participant_gender = participant_gender;
	}

	public String getParticipant_birth() {
		return participant_birth;
	}

	public void setParticipant_birth(String participant_birth) {
		this.participant_birth = participant_birth;
	}

	public String getRequest_datetime() {
		return request_datetime;
	}

	public void setRequest_datetime(String request_datetime) {
		this.request_datetime = request_datetime;
	}

	@Override
	public String toString() {
		return "Participant [info_num=" + info_num + ", participant_name=" + participant_name + ", participant_gender="
				+ participant_gender + ", participant_birth=" + participant_birth + ", request_datetime="
				+ request_datetime + "]";
	}


}
