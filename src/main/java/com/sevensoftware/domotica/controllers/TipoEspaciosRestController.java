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

import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.services.TipoEspacioService;
import com.sevensoftware.domotica.services.TipoIdentificacionService;

/**
 * @author LUIS
 *
 */
@RestController
public class TipoEspaciosRestController {
	
	private static final String url = "/api/tiposEspacio";
	
	@Autowired
	TipoEspacioService tipoEspacioService;
		
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TipoEspacio> buscarTiposEspacio() {
		List<TipoEspacio> tiposEspacio = tipoEspacioService.obtenerTiposEspacio();
		return tiposEspacio;
	}	
	
	
}
