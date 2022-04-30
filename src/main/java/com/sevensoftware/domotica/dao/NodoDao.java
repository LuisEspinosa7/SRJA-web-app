package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Nodo;
import com.sevensoftware.domotica.entities.JSONRespuesta;

public interface NodoDao {
	
	
	/**
	 * Agrega un nuevo Nodo
	 * @param Nodo Datos de Nodo a registrar
	 */
	public boolean agregarNodo(Nodo nodo);
	
	/**
	 * Modifica Nodo
	 * @param Nodo Datos de Nodo a modificar
	 */
	public boolean modificarNodo(int id, Nodo nodo);
	/**
	 * Elimina Nodo
	 * @param Nodo Datos de Nodo a eliminar
	 */
	public boolean eliminarNodo(int id);
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */	
	public JSONRespuesta listarTablaNodo(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca Nodo por id
	 * @return Nodo
	 */
	public Boolean buscarNodo(int id);
	
	/**
	 * Busca Nodo por codigo, nombre y  espacio 
	 * @return Nodo
	 */
	public Boolean buscarNodo(Nodo nodo);
	
	
	/**
	 * Listar los Nodo
	 * @return Lista de los Nodo
	 */
	public List<Nodo> listarNodos();
	
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	//public Nodo obtenerNodo(String username);
	
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	public Nodo obtenerNodo(int id);
		
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	//public Nodo obtenerNodoPorIdDeUsuario(int id);
	
	
}
