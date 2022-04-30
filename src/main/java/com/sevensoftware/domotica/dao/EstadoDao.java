/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;

import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Perfil;

/**
 * @author LUIS
 *
 */
public interface EstadoDao {
	
	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public Estado obtenerEstado(String username);

	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public Estado obtenerEstado(int id);
	
	/**
	 * Obtiene el Estado por el id del estado
	 * @return Estado de el estado
	 */
	public Estado obtenerEstadoPorId(int id);
	
	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public List<Estado> listarEstados();
	
	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public List<Estado> buscarEstadosPorEntidad(String entidad);
	
}
