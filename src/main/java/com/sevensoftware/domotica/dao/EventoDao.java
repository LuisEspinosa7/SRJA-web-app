/**
 * 
 */
package com.sevensoftware.domotica.dao;

import com.sevensoftware.domotica.entities.Evento;

/**
 * @author LUIS
 *
 */
public interface EventoDao {
	

	/**
	 * Agrega un nuevo evento
	 * @param Evento Datos de Evento a registrar
	 */
	public int agregarEvento(Evento evento);
	
	/**
	 * Busca un nuevo evento
	 * @param Evento Datos de Evento a buscar
	 */
	public Boolean buscarEvento(Evento evento, int confirmado);
	
	/**
	 * Busca un nuevo evento
	 * @param Evento Datos de Evento a buscar
	 */
	public Boolean buscarAlertas(Evento evento, int confirmado);
	
	
	/**
	 * Busca un nuevo evento
	 * @param Evento Datos de Evento a buscar
	 */
	public Evento obtenerEvento(int id);
	
	
	/**
	 * Modificar un DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a modificar
	 */
	public Boolean  modificarEvento(int id, Evento evento);

}
