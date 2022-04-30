/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Categoria;
import com.sevensoftware.domotica.services.CategoriaService;

/**
 * @author LUIS
 *
 */
@RestController
public class CategoriaRestController {
	
	private static final String url = "/api/categoria";
	
	@Autowired
	CategoriaService categoriaService;
		
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Categoria> buscarCategoria() {
		List<Categoria> tiposCategoria = categoriaService.obtenerCategorias();
		return tiposCategoria;
	}	

}
