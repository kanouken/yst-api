package org.notification.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "thridPartApplicaiton")
public class CommonProperties {
	private String smsBackList;
	
	public String getSmsBackList() {
		return smsBackList;
	}

	public void setSmsBackList(String smsBackList) {
		this.smsBackList = smsBackList;
	}

	private String fileSavePath;

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}
	
}
