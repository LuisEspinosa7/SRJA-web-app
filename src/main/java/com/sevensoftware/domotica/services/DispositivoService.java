/**
 * 
 */
package com.sevensoftware.domotica.services;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Dispositivo;

/**
 * @author LUIS
 *
 */
public interface DispositivoService {	
	
	/**
	 * Agrega un nuevo Dispositivo
	 * @param Dispositivo Datos de Dispositivo a registrar
	 */
	public void agregarDispositivo(Dispositivo dispositivo);
	
	/**
	 * Modificar un Dispositivo
	 * @param Dispositivo Datos de Dispositivo a modificar
	 */
	public void  modificarDispositivo(int id, Dispositivo dispositivo);
	
	/**
	 * Elimina un Dispositivo
	 * @param Dispositivo Datos de Dispositivo a eliminar
	 */
	public void  eliminarDispositivo(int id);
	
	/**
	 * Lista los Dispositivo paginados
	 * @return lista Dispositivo
	 * */
	
	public JSONRespuesta listarTablaDispositivos(String search, int start, int length, int draw, int posicion, String direccion);

}
