/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LuisLlanos
 *
 */
public class Nodo {

	private int id;
	private String codigo;
	private String nombre;
	private Estado estado;
	private TipoNodo tipoNodo;
	private Espacio espacio;

	public Nodo() {
		super();
	}

	public Nodo(int id, String codigo, String nombre, Estado estado, TipoNodo tipoNodo, Espacio espacio) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.estado = estado;
		this.tipoNodo = tipoNodo;
		this.espacio = espacio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoNodo getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(TipoNodo tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public Espacio getEspacio() {
		return espacio;
	}

	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
	}

}
