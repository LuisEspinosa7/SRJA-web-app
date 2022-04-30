/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author jass
 *
 */
public class Ciudad {
	
	private int id;
	private String nombre;
	//private Departamento departamento;
	
	public Ciudad() {
		super();
	}

	public Ciudad(int id, String nombre
			//, Departamento departamento
			) {
		super();
		this.id = id;
		this.nombre = nombre;
		//this.departamento = departamento;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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

	/**
	 * @return the departamento
	 */
	/**
	public Departamento getDepartamento() {
		return departamento;
	}
	**/
	
	/**
	 * @param departamento the departamento to set
	 */
	/**
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	**/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	@Override
	public String toString() {
		return "Ciudad [id=" + id + ", nombre=" + nombre + ", departamento=" + departamento + "]";
	}
	**/
	
}
