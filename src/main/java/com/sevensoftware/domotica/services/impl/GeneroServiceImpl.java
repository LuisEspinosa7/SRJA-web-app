/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.GeneroDao;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.services.GeneroService;

/**
 * @author LUIS
 *
 */
@Service("generoService")
public class GeneroServiceImpl implements GeneroService{

	@Autowired
	GeneroDao generoDao;
	
	@Override
	public Genero obtenerGenero(String username) {
		Genero genero = generoDao.obtenerGenero(username);
		return genero;
	}

	@Override
	public List<Genero> obtenerGeneros() {
		List<Genero> generos = generoDao.obtenerGeneros();
		return generos;		
	}
	
	
}
