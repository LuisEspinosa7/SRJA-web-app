/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.TipoNodo;

/**
 * @author LuisLlanos
 *
 */
public interface TipoNodoDao {
	
	/**
	 * Listar los tipo de Nodo disponibles
	 * @return tipos de Nodo
	 */
	public List<TipoNodo> obtenerTiposNodo();
	
	/**
	 * TipoNodo segun el id
	 * @return TipoNodo segun el id 
	 */
	public TipoNodo obtenerTipoNodo(int id);

}
