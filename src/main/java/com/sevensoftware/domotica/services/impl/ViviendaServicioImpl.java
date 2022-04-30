/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.ViviendaDao;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Vivienda;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.services.ViviendaServicio;

/**
 * @author LUIS
 *
 */
@Service("viviendaService")
public class ViviendaServicioImpl implements ViviendaServicio{
	
	private Logger logger = Logger.getLogger(ViviendaServicioImpl.class);

	@Autowired
	ViviendaDao viviendaDao;

	
	@Override
	public void agregarVivienda(Vivienda vivienda) {	
		
		logger.debug("--- EN AGREGAR VIVIENDA -----");		
		
		logger.debug("Iniciando Validaciones de Inscripcion de vivienda");
		
		
		if (vivienda.getDireccion() == null || vivienda.getCoordenada() == null || vivienda.getBarrio() == null || vivienda.getCiudad() == null 
				|| vivienda.getEstado() == null) {
			throw new ValueNotPermittedException(Respuesta.VALOR_NULL_ERROR);
		}else{
					
			//Buscamos al vivienda por identificacion, email y username
			Boolean codigo = viviendaDao.buscarPorCodigo(vivienda.getCodigo());
			Boolean direccion = viviendaDao.buscarPorDireccion(vivienda.getDireccion());			
			
			if (codigo || direccion) {
				throw new ObjectAlreadyExistException("La vivienda ya esta registrada");
			}else{
				
				// Aqui ya podemos guardar el vivienda
				try {
					boolean returnInsercion = viviendaDao.agregarVivienda(vivienda);
					
					if (!returnInsercion) {				
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}			
					
				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
				}				
				
			}		
			
		}	
		
	}

	@Override
	public void modificarVivienda(int id, Vivienda vivienda) {
		
		logger.debug("--- EN MODIFICAR VIVIENDA -----");
		
		
		logger.debug("Iniciando Validaciones de Inscripcion de vivienda");
		
		if (id > 0) {		
			
			if (vivienda.getId() == 0 || vivienda.getDireccion() == null || vivienda.getCoordenada() == null || vivienda.getBarrio() == null || vivienda.getCiudad() == null 
					|| vivienda.getEstado() == null) {
				throw new ValueNotPermittedException(Respuesta.VALOR_NULL_ERROR);
			}else{
				
				//Buscamos al vivienda por identificacion, email y username
				Boolean codigo = viviendaDao.buscarPorCodigo(vivienda.getCodigo());
				Boolean direccion = viviendaDao.buscarPorDireccion(vivienda.getDireccion());
				Boolean existePorId = viviendaDao.buscarVivienda(id);
				
				if (!codigo && !direccion && !existePorId) {
					throw new ObjectNotFoundException("La vivienda no se encuentra registrada");
				}else{					
					// Aqui ya podemos modificar el vivienda
					try {
						boolean returnInsercion = viviendaDao.modificarVivienda(id, vivienda);
						
						if (!returnInsercion) {				
							throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
						}			
						
					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}				
					
				}		
				
			}
			
			
		}else{			
			throw new ValueNotPermittedException(Respuesta.VALOR_ID_NULL_ERROR);
		}
						
		logger.debug("--- SALIR DE MODIFICAR VIVIENDA -----");		
	}

	@Override
	public void eliminarVivienda(int id) {
		
		logger.debug("--- EN ELIMINAR VIVIENDA -----");
		
		
		if (id > 0) {
			
			
			Boolean existePorId = viviendaDao.buscarVivienda(id);
			
			if (!existePorId) {
				throw new ObjectNotFoundException("El vivienda no se encuentra registrado");
			}else{					
				// Aqui ya podemos modificar el vivienda
				try {
					boolean returnInsercion = viviendaDao.eliminarVivienda(id);
					
					if (!returnInsercion) {				
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}			
					
				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
				}			
				
			}			
			
		}else{			
			throw new ValueNotPermittedException(Respuesta.VALOR_ID_NULL_ERROR);
		}
				
		logger.debug("--- SALIR DE ELIMINAR VIVIENDA -----");		
	}

	@Override
	public List<Vivienda> listarVivienda(String criterio, String acronimo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONRespuesta listarTablaVivienda(String search, int start, int length, int draw, int posicion,
			String direccion) {

		return viviendaDao.listarTablaVivienda(search, start, length, draw, posicion, direccion);
	}

}
