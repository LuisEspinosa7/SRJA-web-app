/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.TipoIdentificacionDao;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.services.TipoIdentificacionService;

/**
 * @author LUIS
 *
 */
@Service("tipoIdentificacionService")
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService{

	@Autowired
	TipoIdentificacionDao tipoIdentificacionDao;
	
	
	
	@Override
	public TipoIdentificacion obtenerTipoIdentificacion(String username) {
		TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacion(username);
		return tipoIdentificacion;
	}



	@Override
	public List<TipoIdentificacion> obtenerTiposIdentificacion() {
		List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDao.obtenerTiposIdentificacion();
		return tiposIdentificacion;
	}

	
}
