/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.Usuario;

/**
 * @author LUIS
 *
 */
public interface PaisDao {
	
	/**
	 * Agrega un nuevo Pais
	 * @param Pais Datos de Pais a registrar
	 */
	public boolean agregarPais(Pais pais);
	
	/**
	 * Modifica Pais
	 * @param Pais Datos de Pais a modificar
	 */
	public boolean modificarPais(int id, Pais pais);
	/**
	 * Elimina Pais
	 * @param Pais Datos de Pais a eliminar
	 */
	public boolean eliminarPais(int id);
	
	
	/**
	 * Busca Pais por id
	 * @return Pais
	 */
	public Pais buscarPais(int id);
	
	
	/**
	 * Listar los Pais
	 * @return Lista de los Pais
	 */
	public List<Pais> listarPais();
	
	/**
	 * Obtiene el Pais del usuario
	 * @return Pais del usuario
	 */
	public Pais obtenerPais(String username);
	
	/**
	 * Obtiene el Pais del usuario
	 * @return Pais del usuario
	 */
	public Pais obtenerPais(int id);
		
	/**
	 * Obtiene el Pais del usuario
	 * @return Pais del usuario
	 */
	public Pais obtenerPaisPorIdDeUsuario(int id);

	
}
