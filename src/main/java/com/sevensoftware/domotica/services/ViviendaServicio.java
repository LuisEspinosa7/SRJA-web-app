/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Vivienda;

/**
 * @author LUIS
 *
 */
public interface ViviendaServicio {
	
	/**
	 * Agrega un nuevo Vivienda
	 * @param Vivienda Datos de Vivienda a registrar
	 */
	public void agregarVivienda(Vivienda vivienda);
	
	/**
	 * Modificar un Vivienda
	 * @param Vivienda Datos de Vivienda a modificar
	 */
	public void  modificarVivienda(int id, Vivienda vivienda);
	/**
	 * Elimina un Vivienda
	 * @param Vivienda Datos de Vivienda a eliminar
	 */
	public void  eliminarVivienda(int id);
	
	
	/**
	 * Listar las Viviendas
	 * @return Lista de los Viviendas
	 */
	public List<Vivienda> listarVivienda(String criterio, String acronimo);
	/**
	 * Lista los Viviendas paginados
	 * @return lista Viviendas
	 * */
	
	public JSONRespuesta listarTablaVivienda(String search, int start, int length, int draw, int posicion, String direccion);
	

}
