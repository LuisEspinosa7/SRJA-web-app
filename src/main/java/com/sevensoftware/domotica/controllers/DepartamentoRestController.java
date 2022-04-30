/**
 * Clase DepartamentoController para getionar el servicio web de listar los departamentos
 */
package com.sevensoftware.domotica.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.services.DepartamentoService;

/**
 * @author LUIS LLANOS
 * @version 1.0
 *
 */
@RestController
public class DepartamentoRestController {
	
	private static final String url = "/api/departamentos";

	@Autowired
	DepartamentoService departamentoService;

	/**
	 * Metodo GET de departamento para listarlos segun un paramentro de busqueda
	 * 
	 * @param idPais
	 *            codigo de pais con el cual se quieren listar los paises
	 * @return retorna en una lista los paises encontrados segun el parametro de
	 *         busqueda idPais
	 */
	
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	public List<Departamento> getDepartamento(@RequestParam("idPais") int idPais) {
		List<Departamento> listOfDepartamento = new ArrayList<Departamento>();
		listOfDepartamento = departamentoService.listarDepartamento(idPais);
		return listOfDepartamento;
	}

	
	
}
