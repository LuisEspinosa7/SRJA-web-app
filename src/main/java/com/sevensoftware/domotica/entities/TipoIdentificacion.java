/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class TipoIdentificacion {
	
	private int id;
	private String nombre;
	private String acronimo;
	
	public TipoIdentificacion() {
		super();
	}

	public TipoIdentificacion(int id, String nombre, String acronimo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.acronimo = acronimo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}	
	

}
