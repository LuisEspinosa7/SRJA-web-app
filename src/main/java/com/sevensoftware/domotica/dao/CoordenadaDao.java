/**
 * 
 */
package com.sevensoftware.domotica.dao;

import com.sevensoftware.domotica.entities.Coordenada;

/**
 * @author LUIS
 *
 */
public interface CoordenadaDao {
	
	/**
	 * Agrega un nuevo Coordenada
	 * @param Coordenada Datos de Coordenada a registrar
	 */
	public Boolean agregarCoordenada(Coordenada coordenada);
	
	/**
	 * Modificar una Coordenada
	 * @param Coordenada Datos de Coordenada a Modificar
	 */
	public Boolean modificarCoordenada(Coordenada coordenada);
	
	/**
	 * Coordenada segun el id
	 * @return Coordenada segun el id 
	 */
	public Coordenada obtenerCoordenada(int id);

}
