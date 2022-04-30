/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Espacio;

/**
 * @author LUIS
 *
 */
public interface EspacioDao {
	
	/**
	 * Agrega un nuevo Espacio
	 * @param Espacio Datos de Espacio a registrar
	 */
	public boolean agregarEspacio(Espacio espacio);
	
	/**
	 * Modifica Espacio
	 * @param Espacio Datos de Espacio a modificar
	 */
	public boolean modificarEspacio(int id, Espacio espacio);
	/**
	 * Elimina Espacio
	 * @param Espacio Datos de Espacio a eliminar
	 */
	public boolean eliminarEspacio(int id);
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */	
	public JSONRespuesta listarTablaEspacio(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca Espacio por id
	 * @return Espacio
	 */
	public Boolean buscarEspacio(int id);
	
	/**
	 * Busca Espacio por nombre
	 * @return Espacio
	 */
	public Boolean buscarEspacioPorNombre(String nombre);
	
	
	/**
	 * Listar los Espacio
	 * @return Lista de los Espacio
	 */
	public List<Espacio> listarEspacios();
	
	/**
	 * Obtiene el Espacio del usuario
	 * @return Espacio del usuario
	 */
	//public Espacio obtenerEspacio(String username);
	
	/**
	 * Obtiene el Espacio del usuario
	 * @return Espacio del usuario
	 */
	public Espacio obtenerEspacio(int id);
		
	/**
	 * Obtiene el Espacio del usuario
	 * @return Espacio del usuario
	 */
	//public Espacio obtenerEspacioPorIdDeUsuario(int id);
	
	

}
