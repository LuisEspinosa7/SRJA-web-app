/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;
import java.util.Set;


import com.sevensoftware.domotica.entities.TipoIdentificacion;

/**
 * @author LUIS
 *
 */
public interface TipoIdentificacionDao {
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public TipoIdentificacion obtenerTipoIdentificacion(String username);	
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public TipoIdentificacion obtenerTipoIdentificacion(int id);
	
	/**
	 * Tipo Identificacion por su id
	 * @return Tipo Identificacion por su id
	 */
	public TipoIdentificacion obtenerTipoIdentificacionPorId(int id);
	
	
	/**
	 * Listar los tipo identificion disponibles
	 * @return tipos de identificion
	 */
	public List<TipoIdentificacion> obtenerTiposIdentificacion();
	
}
