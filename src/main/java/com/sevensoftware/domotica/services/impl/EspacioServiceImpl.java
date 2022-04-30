/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.dao.EspacioDao;
import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.services.EspacioService;

/**
 * @author LUIS
 *
 */
@Service("espacioService")
public class EspacioServiceImpl implements EspacioService {

	private Logger logger = Logger.getLogger(EspacioServiceImpl.class);

	@Autowired
	EspacioDao espacioDao;

	@Override
	public void agregarEspacio(Espacio espacio) {
		

		logger.debug("--- EN AGREGAR Espacio -----");

		logger.debug("Iniciando Validaciones de Inscripcion de Espacio");

		if (espacio.getNombre() == null || espacio.getDescripcion() == null || espacio.getAncho() == 0
				|| espacio.getLargo() == 0 || espacio.getEstado() == null || espacio.getTipoEspacio() == null || espacio.getVivienda().getId() == 0) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el espacio exista por nombre y coordenada
			Boolean existeEspacio = espacioDao.buscarEspacioPorNombre(espacio.getNombre());

			if (existeEspacio) {
				throw new ObjectAlreadyExistException("El espacio ya existe por Nombre");
			} else {

				// Aqui ya podemos guardar el espacio
				try {
					boolean returnInsercion = espacioDao.agregarEspacio(espacio);

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
	public void modificarEspacio(int id, Espacio espacio) {
		
		logger.debug("--- EN MODIFICAR ESPACIO -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de ESPACIO");

		if (espacio.getId() == 0 || espacio.getNombre() == null || espacio.getDescripcion() == null
				|| espacio.getAncho() == 0 || espacio.getLargo() == 0 || espacio.getEstado() == null || espacio.getTipoEspacio() == null
				|| espacio.getVivienda().getId() == 0) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el espacio exista por nombre y coordenada
			Boolean existeEspacio = espacioDao.buscarEspacio(espacio.getId());

			if (!existeEspacio) {
				throw new ObjectAlreadyExistException("El espacio NO existe");
			} else {

				// Aqui ya podemos MODIFICAR el espacio
				try {
					boolean returnModificacion = espacioDao.modificarEspacio(id, espacio);

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
	public void eliminarEspacio(int id) {
		
		 logger.debug("--- EN ELIMINAR ESPACIO -----");
		  
		 if (id > 0) {
		  
			 Boolean existeEspacioPorId = espacioDao.buscarEspacio(id);
			 
			 if (!existeEspacioPorId) { 
				 throw new ObjectNotFoundException("La ESPACIO no se puede eliminar, pues no existe"); 
			 } else {
				 // Aqui ya podemos modificar el Eps 
				 try { 
					 boolean returnInsercion = espacioDao.eliminarEspacio(id);
					 
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
		 
		 logger.debug("--- SALIR DE ELIMINAR ESPACIO -----");		
		 
	}

	@Override
	public JSONRespuesta listarTablaEspacios(String search, int start, int length, int draw, int posicion,
			String direccion) {
		return espacioDao.listarTablaEspacio(search, start, length, draw, posicion, direccion);
	}

	@Override
	public List<Espacio> listarEspacios() {
		List<Espacio> espacios = espacioDao.listarEspacios();
		return espacios;
	}

}
