/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.TipoEspacioDao;
import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.services.TipoEspacioService;

/**
 * @author LUIS
 *
 */
@Service("tipoEspacioService")
public class TipoEspacioServiceImpl implements TipoEspacioService{

	@Autowired
	TipoEspacioDao tipoEspacioDao;
	
	@Override
	public List<TipoEspacio> obtenerTiposEspacio() {
		List<TipoEspacio> tiposEspacio = tipoEspacioDao.listarTipoEspacios();
		return tiposEspacio;
	}
	
	
	
	

}
