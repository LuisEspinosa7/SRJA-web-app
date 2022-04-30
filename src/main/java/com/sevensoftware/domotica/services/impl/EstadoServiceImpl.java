/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.services.EstadoService;

/**
 * @author LUIS
 *
 */
@Service("estadoService")
public class EstadoServiceImpl implements EstadoService{

	private Logger logger = Logger.getLogger(EstadoServiceImpl.class);
	
	@Autowired
	EstadoDao estadoDao;
	
	@Override
	public Estado obtenerEstado(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estado> listarEstados() {
		List<Estado> estados = estadoDao.listarEstados();
		return estados;
	}

	@Override
	public List<Estado> buscarEstadosPorEntidad(String entidad) {
		logger.debug("DENTRO DEL SERVICIO ESTADOS POR ENTIDAD");
		
		List<Estado> estados = estadoDao.buscarEstadosPorEntidad(entidad);
		return estados;
	}

	@Override
	public Estado buscarEstadoPor_Id(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
