package org.common.tools.exception;

public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5401922877961547792L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 */

	public ApiException() {
	}

	public ApiException(String message) {
		super();
		this.message = message;
	}
}
