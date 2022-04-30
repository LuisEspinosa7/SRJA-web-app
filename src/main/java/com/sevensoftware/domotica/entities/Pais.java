/**
 * 
 */
package com.sevensoftware.domotica.entities;


/**
 * @author ANDRES-GPIE
 *
 */

public class Pais {
	
	private int id;
	private String nombre;
	/**
	 * 
	 */
	public Pais() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param nombre
	 */
	public Pais(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	/**
	 * @return the id
	 */
	public int getid() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setid(int id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
}
