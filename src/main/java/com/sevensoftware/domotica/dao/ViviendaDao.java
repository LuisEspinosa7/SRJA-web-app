/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Vivienda;

/**
 * @author LUIS
 *
 */
public interface ViviendaDao {
	
	
	/**
	 * Agrega un nuevo Vivienda
	 * @param Vivienda Datos de Vivienda a registrar
	 */
	public boolean agregarVivienda(Vivienda vivienda);
	
	/**
	 * Modifica Vivienda
	 * @param Vivienda Datos de Vivienda a modificar
	 */
	public boolean modificarVivienda(int id, Vivienda vivienda);
	/**
	 * Elimina Vivienda
	 * @param Vivienda Datos de Vivienda a eliminar
	 */
	public boolean eliminarVivienda(int id);
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */	
	public JSONRespuesta listarTablaVivienda(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca Vivienda por id
	 * @return Vivienda
	 */
	public Boolean buscarVivienda(int id);
	
	/**
	 * Busca existencia del Vivienda
	 * @return Busca existencia del usuario
	 */
	public Boolean buscarPorCodigo(String codigo);
	
	/**
	 * Busca existencia del Vivienda
	 * @return Busca existencia del usuario
	 */
	public Boolean buscarPorDireccion(String direccion);
	
	
	/**
	 * Listar los Vivienda
	 * @return Lista de los Vivienda
	 */
	public List<Vivienda> listarViviendas();
	
	/**
	 * Obtiene el Vivienda del usuario
	 * @return Vivienda del usuario
	 */
	//public Vivienda obtenerVivienda(String username);
	
	/**
	 * Obtiene el Vivienda del usuario
	 * @return Vivienda del usuario
	 */
	public Vivienda obtenerVivienda(int id);
		
	/**
	 * Obtiene el Vivienda del usuario
	 * @return Vivienda del usuario
	 */
	//public Vivienda obtenerViviendaPorIdDeUsuario(int id);
	
	
	
	

}
