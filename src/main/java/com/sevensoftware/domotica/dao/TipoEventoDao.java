/**
 * 
 */
package com.sevensoftware.domotica.dao;

import com.sevensoftware.domotica.entities.TipoEvento;

/**
 * @author LUIS
 *
 */
public interface TipoEventoDao {
	
	/**
	 * Obtiene el TipoEvento del usuario
	 * @return TipoEvento del EVENTO
	 */
	public TipoEvento obtenerTipoEvento(int id);

}
