/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Estado;

/**
 * @author LUIS
 *
 */
public interface EstadoService {
	
	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public Estado obtenerEstado(String username);

	
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
	
	/**
	 * Listar el Estado
	 * @return Lista de el estado
	 */
	public Estado buscarEstadoPor_Id(int id);	
	
}
