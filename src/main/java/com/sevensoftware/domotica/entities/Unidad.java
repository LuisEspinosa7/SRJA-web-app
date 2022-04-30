/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class Unidad {
	
	private int id;
	private String nombre;
	private String acronimo;
	private String descripcion;
	
	
	
	public Unidad() {
		super();
	}

	public Unidad(int id, String nombre, String acronimo, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.acronimo = acronimo;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
