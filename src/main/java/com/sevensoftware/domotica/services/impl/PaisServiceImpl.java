/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.services.PaisService;

/**
 * @author LUIS
 *
 */
@Service("paisService")
public class PaisServiceImpl implements PaisService {

	@Autowired
	PaisDao paisDao;
		
	
	@Override
	public String agregarPais(Pais Pais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modificarPais(int id, Pais pais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eliminarPais(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pais> listarPais() {
		List<Pais> paises = paisDao.listarPais();
		return paises;
	}	

}
