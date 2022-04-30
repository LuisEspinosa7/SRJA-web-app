/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.Perfil;
import com.sevensoftware.domotica.services.PerfilService;

/**
 * @author LUIS
 *
 */
@RestController
public class PerfilRestController {
	
	private static final String url = "/api/perfiles";
	
	@Autowired
	PerfilService perfilService;
	
	/**
	 * Metodo GET para listar los registros de los perfiles
	 * @return retorna la lista de los perfiles
	 */
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public List<Perfil> getPerfiles() {
		List<Perfil> listOfPerfiles = new ArrayList<Perfil>();
		listOfPerfiles = perfilService.buscarPerfiles();
		return listOfPerfiles;
	}

}
