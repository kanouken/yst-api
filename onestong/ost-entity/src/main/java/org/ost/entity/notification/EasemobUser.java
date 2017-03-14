package org.ost.entity.notification;

import java.io.Serializable;

public class EasemobUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6042994415883234230L;
	private String account;
	private String password;
	private String name;
	private String application;

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
