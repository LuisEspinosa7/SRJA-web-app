/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Pais;

/**
 * @author LUIS
 *
 */
public interface DepartamentoService {
	
	/**
	 * Agrega un nuevo Departamento
	 * @param Pais Departamento de Departamento a registrar
	 */
	public String agregarDepartamento(Departamento departamento);
	
	/**
	 * Modificar un Departamento
	 * @param Pais Departamento de Departamento a modificar
	 */
	public String  modificarDepartamento(int id, Departamento departamento);
	/**
	 * Elimina un Departamento
	 * @param Pais Departamento de Departamento a eliminar
	 */
	public String  eliminarDepartamento(int id);
		
	/**
	 * Listar los Departamento
	 * @return Lista de los Departamento
	 */
	public List<Departamento> listarDepartamento(int idPais);

}
