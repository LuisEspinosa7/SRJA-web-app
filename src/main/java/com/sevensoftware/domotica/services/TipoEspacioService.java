/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;
import com.sevensoftware.domotica.entities.TipoEspacio;

/**
 * @author LUIS
 *
 */
public interface TipoEspacioService {
	
	/**
	 * Listar los tipo espacio disponibles
	 * @return tipos de espacio
	 */
	public List<TipoEspacio> obtenerTiposEspacio();

}
