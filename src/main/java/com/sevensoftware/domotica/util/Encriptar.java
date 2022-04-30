/**
 * 
 */
package com.sevensoftware.domotica.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author LUIS
 *
 */
public class Encriptar {

	public static String toBCryptPasswordEncoder(String text) {
		
		int i = 0;
		String hashedPassword = "";
		
		while (i < 12) {
			String password = text;
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			hashedPassword = passwordEncoder.encode(password);
			i++;
		}
		
		return hashedPassword;
	}
	
	
	
}
