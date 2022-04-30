/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class Espacio {
	
	private int id;
	private String nombre;
	private String descripcion;
	private double ancho;
	private double largo;
	private Vivienda vivienda;
	private Estado estado;
	private TipoEspacio tipoEspacio;
	
	public Espacio() {
		super();
	}

	public Espacio(int id, String nombre, String descripcion, double ancho, double largo, Vivienda vivienda,
			Estado estado, TipoEspacio tipoEspacio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ancho = ancho;
		this.largo = largo;
		this.vivienda = vivienda;
		this.estado = estado;
		this.tipoEspacio = tipoEspacio;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public Vivienda getVivienda() {
		return vivienda;
	}

	public void setVivienda(Vivienda vivienda) {
		this.vivienda = vivienda;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoEspacio getTipoEspacio() {
		return tipoEspacio;
	}

	public void setTipoEspacio(TipoEspacio tipoEspacio) {
		this.tipoEspacio = tipoEspacio;
	}
	
}
