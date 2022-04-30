/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Pais;

/**
 * @author LUIS
 *
 */
public interface DepartamentoDao {
	
	/**
	 * Agrega un nuevo Departamento
	 * @param Departamento Datos de Departamento a registrar
	 */
	public boolean agregarDepartamento(Departamento departamento);
	
	/**
	 * Modifica Departamento
	 * @param Departamento Datos de Departamento a modificar
	 */
	public boolean modificarDepartamento(int id, Departamento departamento);
	/**
	 * Elimina Departamento
	 * @param Departamento Datos de Departamento a eliminar
	 */
	public boolean eliminarDepartamento(int id);
	
	
	/**
	 * Busca el Departamento por id
	 * @return Departamento
	 */
	public Departamento buscarDepartamento(int idDepartamento);
	
	/**
	 * Listar los Departamento
	 * @return Lista de los Departamento
	 */
	public List<Departamento> listarDepartamento(int idPais);
	
	/**
	 * Obtiene el Departamento del usuario
	 * @return Departamento del usuario
	 */
	public Departamento obtenerDepartamento(String username);
	
	/**
	 * Obtiene el Departamento del usuario
	 * @return Departamento del usuario
	 */
	public Departamento obtenerDepartamento(int id);
		
	/**
	 * Obtiene el Departamento del usuario
	 * @return Departamento del usuario
	 */
	public Departamento obtenerDepartamentoPorIdDeUsuario(int id);


	
}
