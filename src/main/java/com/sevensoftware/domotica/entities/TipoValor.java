/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class TipoValor {
	
	private int id;
	private String nombre;
	private double rangoInicio;
	private double rangoFin;
	private Unidad unidad;
	
	
	
	public TipoValor() {
		super();
	}

	public TipoValor(int id, String nombre, double rangoInicio, double rangoFin, Unidad unidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.rangoInicio = rangoInicio;
		this.rangoFin = rangoFin;
		this.unidad = unidad;
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

	public double getRangoInicio() {
		return rangoInicio;
	}

	public void setRangoInicio(double rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	public double getRangoFin() {
		return rangoFin;
	}

	public void setRangoFin(double rangoFin) {
		this.rangoFin = rangoFin;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

}
