/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class TMedicion {
	
	private int dispositivoItemId;
	private int tipoMedicion;
	private int tipoValor;
	private double valor;
	
	public TMedicion() {
		super();
	}

	public TMedicion(int dispositivoItemId, int tipoMedicion, int tipoValor, double valor) {
		super();
		this.dispositivoItemId = dispositivoItemId;
		this.tipoMedicion = tipoMedicion;
		this.tipoValor = tipoValor;
		this.valor = valor;
	}

	public int getDispositivoItemId() {
		return dispositivoItemId;
	}

	public void setDispositivoItemId(int dispositivoItemId) {
		this.dispositivoItemId = dispositivoItemId;
	}

	public int getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(int tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public int getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(int tipoValor) {
		this.tipoValor = tipoValor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}	
	

}
