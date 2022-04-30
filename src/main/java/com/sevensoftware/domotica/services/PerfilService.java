/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Perfil;

/**
 * @author LUIS
 *
 */
public interface PerfilService {
	
	/**
	 * Listar los Perfiles
	 * @return Lista de los Perfiles
	 */
	public List<Perfil> buscarPerfiles();	

}
