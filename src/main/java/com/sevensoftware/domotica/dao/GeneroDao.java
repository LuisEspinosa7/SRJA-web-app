/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Genero;

/**
 * @author LUIS
 *
 */
public interface GeneroDao {
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public Genero obtenerGenero(String username);
	
	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public Genero obtenerGenero(int	id);
	
	/**
	 * Obtiene el genero por el id
	 * @return Obtiene el genero por el id
	 */
	public Genero obtenerGeneroPorId(int id);
	
	/**
	 * Listar los Generos disponibles
	 * @return Genero
	 */
	public List<Genero> obtenerGeneros();	
}
