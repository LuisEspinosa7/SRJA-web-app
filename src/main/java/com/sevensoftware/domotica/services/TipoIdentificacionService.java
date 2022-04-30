/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.TipoIdentificacion;

/**
 * @author LUIS
 *
 */
public interface TipoIdentificacionService {
	
	/**
	 * Listar el tipo identificion
	 * @return tipo identificion
	 */
	public TipoIdentificacion obtenerTipoIdentificacion(String username);

	/**
	 * Listar los tipo identificion disponibles
	 * @return tipos de identificion
	 */
	public List<TipoIdentificacion> obtenerTiposIdentificacion();
	
	
}
