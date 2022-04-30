/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class DispositivoItemValor {
	
	private int id;
	private TipoValor tipoValor;
	private double valor;
	
		
	public DispositivoItemValor() {
		super();
	}

	public DispositivoItemValor(int id, TipoValor tipoValor, double valor) {
		super();
		this.id = id;
		this.tipoValor = tipoValor;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoValor getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValor tipoValor) {
		this.tipoValor = tipoValor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
