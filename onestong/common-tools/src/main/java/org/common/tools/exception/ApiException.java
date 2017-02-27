package org.common.tools.exception;

public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5401922877961547792L;
	private String message;
	private String innerException;

	public String getInnerException() {
		return innerException;
	}

	public void setInnerException(String innerException) {
		this.innerException = innerException;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public ApiException(String message, String innerMessage) {
		this.message = message;
		this.innerException = innerMessage;
	}

	public ApiException(String message) {
		super();
		this.message = message;
	}

}
