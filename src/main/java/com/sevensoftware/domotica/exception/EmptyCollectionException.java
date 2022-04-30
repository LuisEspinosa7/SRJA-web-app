package com.sevensoftware.domotica.exception;

public class EmptyCollectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6355526872081235314L;

	public EmptyCollectionException() {

	}

	public EmptyCollectionException(String message) {
		super(message);
	}

	public EmptyCollectionException(Throwable cause) {
		super(cause);
	}

	public EmptyCollectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyCollectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
