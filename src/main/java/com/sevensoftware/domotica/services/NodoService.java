/**
 * 
 */
package com.sevensoftware.domotica.services;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Nodo;

/**
 * @author LuisLlanos
 *
 */
public interface NodoService {
	
	/**
	 * Agrega un nuevo Nodo
	 * @param Nodo Datos de Nodo a registrar
	 */
	public void agregarNodo(Nodo nodo);
	
	/**
	 * Modificar un Nodo
	 * @param Nodo Datos de Nodo a modificar
	 */
	public void  modificarNodo(int id, Nodo nodo);
	
	/**
	 * Elimina un Nodo
	 * @param Nodo Datos de Nodo a eliminar
	 */
	public void  eliminarNodo(int id);
	
	/**
	 * Lista los Nodo paginados
	 * @return lista Nodo
	 * */
	
	public JSONRespuesta listarTablaNodos(String search, int start, int length, int draw, int posicion, String direccion);

}
