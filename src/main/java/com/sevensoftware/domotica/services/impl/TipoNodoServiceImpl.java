/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sevensoftware.domotica.dao.TipoIdentificacionDao;
import com.sevensoftware.domotica.dao.TipoNodoDao;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.entities.TipoNodo;
import com.sevensoftware.domotica.services.TipoNodoService;

/**
 * @author LUIS
 *
 */
@Service("tipoNodoService")
public class TipoNodoServiceImpl implements TipoNodoService{

	@Autowired
	TipoNodoDao tipoNodoDao;
	
	
	@Override
	public List<TipoNodo> obtenerTiposNodo() {
		List<TipoNodo> tiposNodo = tipoNodoDao.obtenerTiposNodo();
		return tiposNodo;
	}
	
	

}
