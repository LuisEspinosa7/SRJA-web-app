/**
 * 
 */
package com.sevensoftware.domotica.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class Dispositivo {

	private int id;
	private String nombre;
	private TipoDispositivo tipoDispositivo;
	private Categoria categoria;
	private Estado estado;
	private List<TipoValor> tiposValor;
	
	
	
	public Dispositivo() {
		super();
	}

	public Dispositivo(int id, String nombre, TipoDispositivo tipoDispositivo, Categoria categoria, Estado estado,
			List<TipoValor> tiposValor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoDispositivo = tipoDispositivo;
		this.categoria = categoria;
		this.estado = estado;
		this.tiposValor = tiposValor;
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

	public TipoDispositivo getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(TipoDispositivo tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<TipoValor> getTiposValor() {
		return tiposValor;
	}

	public void setTiposValor(List<TipoValor> tiposValor) {
		this.tiposValor = tiposValor;
	}
	
}
