/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sevensoftware.domotica.entities.TipoNodo;
import com.sevensoftware.domotica.services.TipoNodoService;

/**
 * @author LUIS
 *
 */
@RestController
public class TipoNodosRestController {
	
	private static final String url = "/api/tiposNodo";
	
	@Autowired
	TipoNodoService tipoNodoService;
		
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TipoNodo> buscarTiposNodo() {
		List<TipoNodo> tiposNodo = tipoNodoService.obtenerTiposNodo();
		return tiposNodo;
	}	
	
	
}
