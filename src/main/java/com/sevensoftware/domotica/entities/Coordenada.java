/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class Coordenada {
	
	private int id;
	private String latitud;
	private String longitud;
	private String altitud;	
	
	public Coordenada() {
		super();
	}

	public Coordenada(int id, String latitud, String longitud, String altitud) {
		super();
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.altitud = altitud;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getAltitud() {
		return altitud;
	}

	public void setAltitud(String altitud) {
		this.altitud = altitud;
	}

}
