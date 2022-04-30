/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class LiveData {
	
	private double data;

	public LiveData(double data) {
		super();
		this.data = data;
	}

	public LiveData() {
		super();
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}
		
}
