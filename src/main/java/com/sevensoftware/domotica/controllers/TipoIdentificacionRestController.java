/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.services.TipoIdentificacionService;

/**
 * @author LUIS
 *
 */
@RestController
public class TipoIdentificacionRestController {
	
	private static final String url = "/api/tiposIdentificacion";
	
	@Autowired
	TipoIdentificacionService tipoIdentificacionService;
	
	
	@RequestMapping(value = url + "/username", method = RequestMethod.GET, headers = "Accept=application/json")
	public TipoIdentificacion buscarTipoIdentificacionPorEntidad(@RequestParam("username") String username) {
		return tipoIdentificacionService.obtenerTipoIdentificacion(username);
	}	
	
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TipoIdentificacion> buscarTiposIdentificacion() {
		List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionService.obtenerTiposIdentificacion();
		return tiposIdentificacion;
	}	
	
	
}
