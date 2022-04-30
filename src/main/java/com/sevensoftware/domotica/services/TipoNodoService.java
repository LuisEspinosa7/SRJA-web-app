/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;
import com.sevensoftware.domotica.entities.TipoNodo;

/**
 * @author LUIS
 *
 */
public interface TipoNodoService {
	
	/**
	 * Listar los tipo nodo disponibles
	 * @return tipos de nodo
	 */
	public List<TipoNodo> obtenerTiposNodo();

}
