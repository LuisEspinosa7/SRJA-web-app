/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Genero;

/**
 * @author LUIS
 *
 */
public interface CiudadDao {
	
	/**
	 * Agrega un nuevo Ciudad
	 * @param Ciudad Datos de Ciudad a registrar
	 */
	public boolean agregarCiudad(Ciudad ciudad);
	
	/**
	 * Modifica Ciudad
	 * @param Ciudad Datos de Ciudad a modificar
	 */
	public boolean modificarCiudad(int id, Ciudad ciudad);
	/**
	 * Elimina Ciudad
	 * @param Ciudad Datos de Ciudad a eliminar
	 */
	public boolean eliminarCiudad(int id);
	
	
	/**
	 * Busca el Ciudad por id
	 * @return Ciudad
	 */	
	//public Departamento buscarDepartamento(int idDepartamento);
	
	
	/**
	 * Listar los Ciudad
	 * @return Lista de los Ciudad
	 */
	public List<Ciudad> listarCiudad(int idDepartamento);
	
	/**
	 * Obtiene el ciudad del usuario
	 * @return Ciudad del usuario
	 */
	public Ciudad obtenerCiudad(String username);
	
	/**
	 * Obtiene el ciudad del usuario
	 * @return Ciudad del usuario
	 */
	public Ciudad obtenerCiudad(int id);
	
	/**
	 * Obtiene el ciudad del usuario por el id
	 * @return Ciudad del usuario
	 */
	public Ciudad obtenerCiudadPorId(int id);

}
