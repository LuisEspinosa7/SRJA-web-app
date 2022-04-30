/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.Evento;

import com.sevensoftware.domotica.services.EventoService;

/**
 * @author LUIS
 *
 */
@RestController
public class EventoRestController {
	
	private static final String url = "/api/eventos";
	
	
	@Autowired
	private EventoService eventoService;
	
	
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN", "ROLE_PROPIETARIO", "ROLE_INTEGRANTE" })
	public ResponseEntity<Respuesta> modificarEvento(@PathVariable int id, @RequestBody Evento evento) {
		
		eventoService.modificarEvento(id, evento);	
		
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje("Enviando Respuesta");					
		respuesta.setBody(null);																																		
		
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}

}
