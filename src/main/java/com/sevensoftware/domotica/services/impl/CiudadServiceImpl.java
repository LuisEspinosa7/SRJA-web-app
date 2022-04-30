/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.CiudadDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.services.CiudadService;

/**
 * @author LUIS
 *
 */
@Service("ciudadService")
public class CiudadServiceImpl implements CiudadService{
	
	@Autowired
	CiudadDao ciudadDao;
	

	@Override
	public String agregarCiudad(Ciudad ciudad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modificarCiudad(int id, Ciudad ciudad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eliminarCiudad(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ciudad> listarCiudad(int idDepartamento) {
		List<Ciudad> ciudades = ciudadDao.listarCiudad(idDepartamento);
		return ciudades;
	}
	
}
