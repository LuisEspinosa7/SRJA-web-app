package com.sevensoftware.domotica.exception;


public class ObjectAlreadyExistException extends RuntimeException {
	
	public ObjectAlreadyExistException() {

	}

	public ObjectAlreadyExistException(String message) {
		super(message);
	}

	public ObjectAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public ObjectAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectAlreadyExistException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
