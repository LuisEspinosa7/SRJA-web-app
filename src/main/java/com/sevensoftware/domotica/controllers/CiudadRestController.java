/**
 * 
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.dao.impl.UsuarioDaoImpl;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.services.CiudadService;

/**
 * @author LUIS
 * @version 1.0
 */
@RestController
public class CiudadRestController {
	
	private static final String url = "/api/ciudades";
	
	private Logger logger = Logger.getLogger(CiudadRestController.class);

	@Autowired
	CiudadService ciudadService;
	

	/**
	 * Metodo GET para listar los datos de las ciudades
	 * 
	 * @param idDep
	 *            codigo de departamento con el cual se quiere listar los
	 *            ciudades
	 * @return retorna una lista de ciudades segun el departamento
	 */
	
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	public List<Ciudad> getCiudades(@RequestParam("idDepartamento") int idDep) {
		List<Ciudad> listaDeCiudad = new ArrayList<Ciudad>();
		listaDeCiudad = ciudadService.listarCiudad(idDep);
		return listaDeCiudad;
	}	
	
	
	
}
