/**
 * 
 */
package com.sevensoftware.domotica.services;


import com.sevensoftware.domotica.entities.Evento;

/**
 * @author LUIS
 *
 */
public interface EventoService {
	
	
	/**
	 * Agrega un nuevo evento
	 * @param Evento Datos de Evento a registrar
	 */
	public void agregarEvento(Evento evento);
	
	
	/**
	 * Modificar un DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a modificar
	 */
	public void  modificarEvento(int id, Evento evento);
	

}
