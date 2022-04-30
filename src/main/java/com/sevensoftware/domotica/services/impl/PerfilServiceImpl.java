/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.services.PerfilService;
import com.sevensoftware.domotica.dao.PerfilDao;
import com.sevensoftware.domotica.entities.Perfil;

/**
 * @author LUIS
 *
 */
@Service("perfilService")
public class PerfilServiceImpl implements PerfilService{
	
	@Autowired
	PerfilDao perfilDao;

	@Override
	public List<Perfil> buscarPerfiles() {
		List<Perfil> perfiles = perfilDao.buscarPerfiles();
		return perfiles;
	}
	
	

}
