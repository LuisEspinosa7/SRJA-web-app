/**
 * 
 */
package com.sevensoftware.domotica.exception;

/**
 * @author LUIS
 *
 */
public class DAOException extends RuntimeException{
	
	public DAOException(){
		
	}
	
	public DAOException(String message){
		super(message);
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
