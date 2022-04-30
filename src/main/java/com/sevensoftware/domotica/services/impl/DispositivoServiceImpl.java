/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.DispositivoDao;
import com.sevensoftware.domotica.dao.TipoIdentificacionDao;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.services.DispositivoService;

/**
 * @author LUIS
 *
 */

@Service("dispositivoService")
public class DispositivoServiceImpl  implements DispositivoService{
	
	private Logger logger = Logger.getLogger(DispositivoServiceImpl.class);
	
	@Autowired
	DispositivoDao dispositivoDao;

	@Override
	public void agregarDispositivo(Dispositivo dispositivo) {
		
		
		logger.debug("--- EN AGREGAR Dispositivo -----");

		logger.debug("Iniciando Validaciones de Inscripcion de Dispositivo");

		if (dispositivo.getNombre() == null || dispositivo.getEstado() == null || dispositivo.getTipoDispositivo() == null || dispositivo.getCategoria() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el dispositivo exista 
			Boolean existeDispositivo = dispositivoDao.buscarDispositivo(dispositivo);

			if (existeDispositivo) {
				throw new ObjectAlreadyExistException("El dispositivo ya existe por Codigo o Nombre en determinado Espacio");
			} else {

				// Aqui ya podemos guardar el dispositivo
				try {
					boolean returnInsercion = dispositivoDao.agregarDispositivo(dispositivo);

					if (!returnInsercion) {
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}
				
	}

	@Override
	public void modificarDispositivo(int id, Dispositivo dispositivo) {
		
		logger.debug("--- EN MODIFICAR dispositivo -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de dispositivo");

		if (dispositivo.getId() == 0 || dispositivo.getNombre() == null || dispositivo.getEstado() == null || 
				dispositivo.getTipoDispositivo() == null || dispositivo.getCategoria() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el dispositivo exista por nombre
			Boolean existeDispositivo = dispositivoDao.buscarDispositivo(dispositivo.getId());

			if (!existeDispositivo) {
				throw new ObjectAlreadyExistException("El dispositivo NO existe por Nombre O Codigo");
			} else {

				// Aqui ya podemos MODIFICAR el dispositivo
				try {
					boolean returnModificacion = dispositivoDao.modificarDispositivo(id, dispositivo);

					if (!returnModificacion) {
						throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
					}

				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			}

		}
		
	}

	@Override
	public void eliminarDispositivo(int id) {

		 logger.debug("--- EN ELIMINAR dispositivo -----");
		  
		 if (id > 0) {
		  
			 Boolean existeDispositivoPorId = dispositivoDao.buscarDispositivo(id);
			 
			 if (!existeDispositivoPorId) { 
				 throw new ObjectNotFoundException("La dispositivo no se puede eliminar, pues no existe"); 
			 } else {
				 // Aqui ya podemos modificar el Eps 
				 try { 
					 boolean returnInsercion = dispositivoDao.eliminarDispositivo(id);
					 
					 if (!returnInsercion) { 
						 throw new DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos"); 
					 }
			  
				 } catch (DAOException daoe) { 
					 daoe.printStackTrace(); 
					 throw new  DAOException("Ocurrio un inconveniente al eliminar el registro en la base de datos"); 
				}			 
			}
		 
		 } else { 
			 throw new ValueNotPermittedException("El id no esta permitido"); 
		 }
		 
		 logger.debug("--- SALIR DE ELIMINAR dispositivo -----");
		
		
	}

	@Override
	public JSONRespuesta listarTablaDispositivos(String search, int start, int length, int draw, int posicion,
			String direccion) {
		return dispositivoDao.listarTablaDispositivo(search, start, length, draw, posicion, direccion);
	}
	
	
	
	
	
	
	
	
}
