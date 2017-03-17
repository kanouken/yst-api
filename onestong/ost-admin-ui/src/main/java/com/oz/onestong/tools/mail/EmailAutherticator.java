package com.oz.onestong.tools.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAutherticator extends Authenticator {
	private String username;
	private String password;
	public EmailAutherticator() {
		super();
	}

	public EmailAutherticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
