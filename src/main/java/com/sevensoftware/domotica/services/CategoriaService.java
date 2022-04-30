package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.Categoria;

public interface CategoriaService {
	
	/**
	 * Listar las Categorias disponibles
	 * @return tipos de categorias
	 */
	public List<Categoria> obtenerCategorias();	
	
}
