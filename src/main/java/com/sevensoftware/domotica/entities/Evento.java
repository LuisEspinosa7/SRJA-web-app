/**
 * 
 */
package com.sevensoftware.domotica.entities;


import java.time.LocalDateTime;

/**
 * @author LUIS
 *
 */
public class Evento {
	
	
	private int id;
	private LocalDateTime fechaHora;
	private TipoEvento tipoEvento;
	private String mensaje;
	private int confirmado;
	
	public Evento() {
		super();
	}
	
	public Evento(int id, LocalDateTime fechaHora, TipoEvento tipoEvento, String mensaje, int confirmado) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.tipoEvento = tipoEvento;
		this.mensaje = mensaje;
		this.confirmado = confirmado;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getConfirmado() {
		return confirmado;
	}
	public void setConfirmado(int confirmado) {
		this.confirmado = confirmado;
	}
	

}
