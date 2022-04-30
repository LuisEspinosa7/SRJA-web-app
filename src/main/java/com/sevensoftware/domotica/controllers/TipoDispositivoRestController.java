/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.TipoDispositivo;
import com.sevensoftware.domotica.services.TipoDispositivoService;

/**
 * @author LUIS
 *
 */
@RestController
public class TipoDispositivoRestController {
	
private static final String url = "/api/tiposDispositivo";
	
	@Autowired
	TipoDispositivoService tipoDispositivoService;
		
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	public List<TipoDispositivo> buscarTiposDispositivo() {
		List<TipoDispositivo> tiposDispositivo = tipoDispositivoService.obtenerTiposDispositivo();
		return tiposDispositivo;
	}	
	
	
}
