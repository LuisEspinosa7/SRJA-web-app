/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.TMedicion;

/**
 * @author LUIS
 *
 */
public interface MedicionDao {
	
	/**
	 * Agrega una nueva medicion
	 * @param  Datos de medicion a registrar
	 */
	public Boolean agregarMedicion(int dispositivoItem, int tipoMedicion, int tipoValor, double valor);
	
	/**
	 * Agrega una nueva medicion
	 * @param  Datos de medicion a registrar
	 */
	public void agregarMedicionNodo(List<TMedicion> mediciones);

}
