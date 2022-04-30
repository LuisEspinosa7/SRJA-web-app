/**
 * Clase PaisRestController para gestionar los servicios web de Pais
 */
package com.sevensoftware.domotica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.services.PaisService;

/**
 * 
 * @author LUIS LLANOS
 * @version 1.0
 */
@RestController
public class PaisRestController {	
	
	private static final String url = "/api/paises";
	
	@Autowired
	PaisService paisService;
	
	
	/**
	 * Metodo GET para listar los registros de paises
	 * @return retorna la lista de pais
	 */
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public List<Pais> getPaises() {
		List<Pais> listOfPaises = new ArrayList<Pais>();
		listOfPaises = paisService.listarPais();
		return listOfPaises;
	}
	
}