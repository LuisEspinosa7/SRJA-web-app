/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.TipoDispositivoDao;
import com.sevensoftware.domotica.entities.TipoDispositivo;
import com.sevensoftware.domotica.services.TipoDispositivoService;

/**
 * @author LUIS
 *
 */
@Service("tipoDispositivoService")
public class TipoDispositivoServiceImpl implements TipoDispositivoService{

	
	@Autowired
	TipoDispositivoDao tipoDispositivoDao;
	
	@Override
	public List<TipoDispositivo> obtenerTiposDispositivo() {
		List<TipoDispositivo> tiposDispositivo = tipoDispositivoDao.obtenerTiposDispositivo();
		return tiposDispositivo;
	}

}
