/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.services.EstadoService;
import com.sevensoftware.domotica.services.GeneroService;

/**
 * @author LUIS
 *
 */
@RestController
public class GeneroRestController {
	
	private static final String url = "/api/generos";
	
	
	@Autowired
	private GeneroService generoService;
	
	/**
	@RequestMapping(value = url, method = RequestMethod.GET)
	public List<Estado> listarEstados() {
		return estadoService.listarEstados();
	}
	**/
	
	@RequestMapping(value = url + "/username", method = RequestMethod.GET, headers = "Accept=application/json")
	public Genero buscarGeneroPorEntidad(@RequestParam("username") String username) {
		return generoService.obtenerGenero(username);
	}	
	
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public List<Genero> buscarGeneros() {
		List<Genero> generos = generoService.obtenerGeneros();
		return generos;
	}
	
	
	/**
	@RequestMapping(value = url + "/{id}", method = RequestMethod.GET)
	public Estado buscarEstadoPor_Id(@PathVariable("id") int id) {
		return estadoService.buscarPorId(id);
	}	
	**/
	
	
	
	
}
