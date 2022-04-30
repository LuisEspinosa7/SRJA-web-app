/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.TipoDispositivo;

/**
 * @author LUIS
 *
 */
public interface TipoDispositivoDao {
	
	
	/**
	 * Agrega un nuevo TipoDispositivo
	 * @param TipoDispositivo Datos de TipoDispositivo a registrar
	 */
	public Boolean agregarTipoDispositivo(TipoDispositivo tipoDispositivo);
	
	/**
	 * Modificar una TipoDispositivo
	 * @param TipoDispositivo Datos de TipoDispositivo a Modificar
	 */
	public Boolean modificarTipoDispositivo(TipoDispositivo tipoDispositivo);
	
	/**
	 * TipoDispositivo segun el id
	 * @return TipoDispositivo segun el id 
	 */
	public TipoDispositivo obtenerTipoDispositivo(int id);
	
	/**
	 * Listar los tipo de Dispositivo disponibles
	 * @return tipos de Dispositivo
	 */
	public List<TipoDispositivo> obtenerTiposDispositivo();

}
