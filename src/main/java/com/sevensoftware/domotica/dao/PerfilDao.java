/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;
import java.util.Set;

import com.sevensoftware.domotica.entities.Perfil;

/**
 * @author LUIS
 *
 */
public interface PerfilDao {

	/**
	 * Listar los Roles
	 * @return Lista de los Roles
	 */
	public Set<Perfil> buscarPerfilesPorUsername(String nombre);	
	
	
	/**
	 * Perfil 
	 * @return busca perfil
	 */
	public Perfil buscarPerfil(String nombre);
	
	
	/**
	 * Verificar si tiene asignado el perfil (Rol) 
	 * @return Booleano (True o False)
	 */
	//public Boolean buscarRolExistente(String username, int idUsuario, int idPerfil);
	
	
	/**
	 * Buscar Perfiles 
	 * @return lista de perfiles
	 */
	public List<Perfil> buscarPerfiles();
	
}
