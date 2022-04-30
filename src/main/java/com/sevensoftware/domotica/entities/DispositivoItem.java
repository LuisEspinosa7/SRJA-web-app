/**
 * 
 */
package com.sevensoftware.domotica.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class DispositivoItem {
	
	private int id;
	private String codigo;
	private Dispositivo dispositivo;
	private Nodo nodo;
	private Estado estado;
	private List<DispositivoItemValor> valores;
	
	
	public DispositivoItem() {
		super();
	}

	public DispositivoItem(int id, String codigo, Dispositivo dispositivo, Nodo nodo, Estado estado,
			List<DispositivoItemValor> valores) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.dispositivo = dispositivo;
		this.nodo = nodo;
		this.estado = estado;
		this.valores = valores;
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

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<DispositivoItemValor> getValores() {
		return valores;
	}

	public void setValores(List<DispositivoItemValor> valores) {
		this.valores = valores;
	}
	
}
