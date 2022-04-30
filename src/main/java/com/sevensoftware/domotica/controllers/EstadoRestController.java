/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.services.EstadoService;

/**
 * @author LUIS
 *
 */

@RestController
public class EstadoRestController {

	private static final String url = "/api/estados";

	@Autowired
	private EstadoService estadoService;
	
	@RequestMapping(value = url, method = RequestMethod.GET)
	public List<Estado> listarEstados() {
		return estadoService.listarEstados(); 
	}
	
	@RequestMapping(value = url + "/entidad", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Estado> buscarEstadosPorEntidad(@RequestParam("entidad") String entidad) {
		return estadoService.buscarEstadosPorEntidad(entidad);
	}	
	
	/**
	@RequestMapping(value = url + "/{id}", method = RequestMethod.GET)
	public Estado buscarEstadoPor_Id(@PathVariable("id") int id) {
		return estadoService.buscarPorId(id);
	}	
	**/
}
