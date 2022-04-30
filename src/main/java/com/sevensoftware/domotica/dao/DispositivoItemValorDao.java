/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.DispositivoItemValor;

/**
 * @author LUIS
 *
 */
public interface DispositivoItemValorDao {
	
	/**
	 * Listar los DispositivoItemValor
	 * @return Lista de los DispositivoItemValor
	 */
	public List<DispositivoItemValor> listarDispositivoItemValores(int dispositivoItemId);
	
	
	/**
	 * Obtiene el DispositivoItemValor del usuario
	 * @return DispositivoItemValor del usuario
	 */
	public DispositivoItemValor obtenerDispositivoItemValor(int id);
	
	
	/**
	 * Modifica DispositivoItemValor
	 * @param DispositivoItemValor Datos de DispositivoItemValor a modificar
	 */
	public boolean modificarDispositivoItemValor(int id, DispositivoItemValor actuadorValor);

	
}
