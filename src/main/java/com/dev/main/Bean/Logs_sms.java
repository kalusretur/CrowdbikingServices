package com.dev.main.Bean;

public class Logs_sms {
	
	private int id_log;
	private String cel_phone;
	private int sms_code;
	private String sns_messageId;
	private String fec_send_sms;
	private String fec_validate_sms;
	
	public Logs_sms() {
		
	}

	public int getId_log() {
		return id_log;
	}

	public void setId_log(int id_log) {
		this.id_log = id_log;
	}

	public String getCel_phone() {
		return cel_phone;
	}

	public void setCel_phone(String cel_phone) {
		this.cel_phone = cel_phone;
	}

	public int getSms_code() {
		return sms_code;
	}

	public void setSms_code(int sms_code) {
		this.sms_code = sms_code;
	}

	public String getSns_messageId() {
		return sns_messageId;
	}

	public void setSns_messageId(String sns_messageId) {
		this.sns_messageId = sns_messageId;
	}

	public String getFec_send_sms() {
		return fec_send_sms;
	}

	public void setFec_send_sms(String fec_send_sms) {
		this.fec_send_sms = fec_send_sms;
	}

	public String getFec_validate_sms() {
		return fec_validate_sms;
	}

	public void setFec_validate_sms(String fec_validate_sms) {
		this.fec_validate_sms = fec_validate_sms;
	}
	
	

}
