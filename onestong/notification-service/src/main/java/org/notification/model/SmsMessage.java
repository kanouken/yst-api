package org.notification.model;

public class SmsMessage {
	private String verifyCode;

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public int getEvnType() {
		return evnType;
	}

	public void setEvnType(int evnType) {
		this.evnType = evnType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private int evnType;
	private String content;
	private String phone;
}
