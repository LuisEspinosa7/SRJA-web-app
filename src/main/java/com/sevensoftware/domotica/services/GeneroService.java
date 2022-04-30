/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.TipoIdentificacion;

/**
 * @author LUIS
 *
 */
public interface GeneroService {
	
	/**
	 * Listar los Generos
	 * @return Lista de los Generos
	 */
	public Genero obtenerGenero(String username);
	
	/**
	 * Listar los Generos disponibles
	 * @return Genero
	 */
	public List<Genero> obtenerGeneros();	

}
