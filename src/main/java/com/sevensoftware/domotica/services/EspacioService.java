/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.JSONRespuesta;

/**
 * @author LUIS
 *
 */
public interface EspacioService {
	
	/**
	 * Agrega un nuevo Espacio
	 * @param Espacio Datos de Espacio a registrar
	 */
	public void agregarEspacio(Espacio espacio);
	
	/**
	 * Modificar un Espacio
	 * @param Espacio Datos de Espacio a modificar
	 */
	public void  modificarEspacio(int id, Espacio espacio);
	
	/**
	 * Elimina un Espacio
	 * @param Espacio Datos de Espacio a eliminar
	 */
	public void  eliminarEspacio(int id);
	
	
	/**
	 * Listar los Espacios
	 * @return Lista de las Espacios
	 */
	public List<Espacio> listarEspacios();
	
	
	/**
	 * Lista los Espacio paginados
	 * @return lista Espacio
	 * */
	
	public JSONRespuesta listarTablaEspacios(String search, int start, int length, int draw, int posicion, String direccion);

}
