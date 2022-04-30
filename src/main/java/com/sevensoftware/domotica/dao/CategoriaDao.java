/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Categoria;

/**
 * @author LUIS
 *
 */
public interface CategoriaDao {
	
	/**
	 * Agrega un nuevo Categoria
	 * @param Categoria Datos de Categoria a registrar
	 */
	public Boolean agregarCategoria(Categoria categoria);
	
	/**
	 * Modificar una Categoria
	 * @param Categoria Datos de Categoria a Modificar
	 */
	public Boolean modificarCategoria(Categoria categoria);
	
	/**
	 * Categoria segun el id
	 * @return Categoria segun el id 
	 */
	public Categoria obtenerCategoria(int id);
	
	
	/**
	 * Categorias disponibles
	 * @return Categorias disponibles 
	 */
	public List<Categoria> obtenerCategorias();

}
