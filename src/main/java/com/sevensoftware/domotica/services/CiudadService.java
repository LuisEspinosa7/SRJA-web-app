/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;

/**
 * @author LUIS
 *
 */
public interface CiudadService {

	/**
	 * Agrega una nueva Ciudad
	 * @param Datos de la Ciudad a registrar
	 */
	public String agregarCiudad(Ciudad ciudad);
	
	/**
	 * Modificar un Ciudad
	 * @param Datos de la Ciudad a modificar
	 */
	public String  modificarCiudad(int id, Ciudad ciudad);
	
	/**
	 * Elimina un Ciudad
	 * @param Ciudad Id de Ciudad a eliminar
	 */
	public String  eliminarCiudad(int id);
		
	/**
	 * Listar los Ciudades
	 * @return Lista de las Ciudades
	 */
	public List<Ciudad> listarCiudad(int idDepartamento);

}
