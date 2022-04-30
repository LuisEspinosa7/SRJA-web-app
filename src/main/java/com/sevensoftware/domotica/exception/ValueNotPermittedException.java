package com.sevensoftware.domotica.exception;

public class ValueNotPermittedException extends RuntimeException {	


	private static final long serialVersionUID = 7949435273202374202L;

	public ValueNotPermittedException() {

	}

	public ValueNotPermittedException(String message) {
		super(message);
	}

	public ValueNotPermittedException(Throwable cause) {
		super(cause);
	}

	public ValueNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueNotPermittedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
