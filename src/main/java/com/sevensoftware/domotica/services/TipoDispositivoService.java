/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.TipoDispositivo;

/**
 * @author LUIS
 *
 */
public interface TipoDispositivoService {
	
	/**
	 * Listar los tipo dispositivo disponibles
	 * @return tipos de dispositivo
	 */
	public List<TipoDispositivo> obtenerTiposDispositivo();

}
