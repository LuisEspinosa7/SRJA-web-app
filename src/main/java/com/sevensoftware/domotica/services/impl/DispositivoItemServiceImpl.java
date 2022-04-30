/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.DispositivoItemDao;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.services.DispositivoItemService;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;

/**
 * @author LUIS
 *
 */
@Service("dispositivoItemService")
public class DispositivoItemServiceImpl implements DispositivoItemService {
	
	private Logger logger = Logger.getLogger(DispositivoItemServiceImpl.class);
	
	@Autowired
	DispositivoItemDao dispositivoItemDao;
	

	@Override
	public void agregarDispositivoItem(DispositivoItem dispositivoItem) {
		
		logger.debug("--- EN AGREGAR Dispositivo -----");

		logger.debug("Iniciando Validaciones de Inscripcion de Dispositivo");

		if (dispositivoItem.getCodigo() == null || dispositivoItem.getEstado() == null || dispositivoItem.getDispositivo() == null || dispositivoItem.getNodo() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el dispositivo exista 
			Boolean existeDispositivoItem = dispositivoItemDao.buscarDispositivoItem(dispositivoItem);

			if (existeDispositivoItem) {
				throw new ObjectAlreadyExistException("El dispositivo ya existe por Codigo");
			} else {

				// Aqui ya podemos guardar el dispositivo
				try {
					boolean returnInsercion = dispositivoItemDao.agregarDispositivoItem(dispositivoItem);

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
	public void modificarDispositivoItem(int id, DispositivoItem dispositivoItem) {
		
		logger.debug("--- EN MODIFICAR DispositivoItem -----");
		
		
		logger.debug("Iniciando Validaciones de modificar DispositivoItem");
		
		if (id > 0) {		
			
			if (dispositivoItem.getId() == 0 || dispositivoItem.getCodigo() == null || dispositivoItem.getNodo() == null ||
					dispositivoItem.getEstado() == null || dispositivoItem.getDispositivo() == null) {
				throw new ValueNotPermittedException("No se permiten valores nulos");
			}else{
				
				//Buscamos al usuario por identificacion, email y username
				Boolean existe = dispositivoItemDao.buscarDispositivoItem(dispositivoItem.getId());
				
				//Boolean identificacion = usuarioDao.buscarPorIdentificacion(usuario.getIdentificacion());
				//Boolean email = usuarioDao.buscarPorEmail(usuario.getEmail());
				//Boolean username = usuarioDao.buscarPorUsername(usuario.getUsername());
				
				if (!existe) {
					throw new ObjectNotFoundException("El Dispositivo Item no esta registrado");
				}else{					
					// Aqui ya podemos modificar el usuario
					try {
						boolean returnInsercion = dispositivoItemDao.modificarDispositivoItem(id, dispositivoItem);
						
						if (!returnInsercion) {				
							throw new DAOException("Ocurrio un inconveniente al modificar el registro en la base de datos");			
						}			
						
					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException("Ocurrio un inconveniente al modificar el registro en la base de datos");			
					}				
					
				}		
				
			}
			
			
		}else{			
			throw new ValueNotPermittedException("El id no esta permitido");
		}
						
		logger.debug("--- SALIR DE MODIFICAR DispositivoItem -----");
		
	}

	@Override
	public void eliminarDispositivoItem(int id) {
		
		 logger.debug("--- EN ELIMINAR dispositivo -----");
		  
		 if (id > 0) {
		  
			 Boolean existeDispositivoPorId = dispositivoItemDao.buscarDispositivoItem(id);
			 
			 if (!existeDispositivoPorId) { 
				 throw new ObjectNotFoundException("La dispositivo no se puede eliminar, pues no existe"); 
			 } else {
				 // Aqui ya podemos modificar el Eps 
				 try { 
					 boolean returnInsercion = dispositivoItemDao.eliminarDispositivoItem(id);
					 
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
	public List<DispositivoItem> listarDispositivoItem() {
		List<DispositivoItem> listadispositivoItem = dispositivoItemDao.listarDispositivoItem();
		return listadispositivoItem;
	}

	@Override
	public void modificarDispositivoItemActuador(int id, DispositivoItem dispositivoItem) {
		
		logger.debug("--- EN MODIFICAR VALORES DEL DispositivoItem ACTUADOR-----");
		
		
		logger.debug("Iniciando Validaciones de modificar VALORES DEL  DispositivoItem ACTUADOR");
		
		if (id > 0) {		
			
			if (dispositivoItem.getId() == 0 || dispositivoItem.getCodigo() == null || dispositivoItem.getNodo() == null ||
					dispositivoItem.getEstado() == null || dispositivoItem.getDispositivo() == null) {
				throw new ValueNotPermittedException("No se permiten valores nulos");
			}else{
				
				//Buscamos al usuario por identificacion, email y username
				Boolean existe = dispositivoItemDao.buscarDispositivoItem(dispositivoItem.getId());
					
				
				if (!existe) {
					throw new ObjectNotFoundException("El Dispositivo Item ACTUADOR no esta registrado");
				}else{					
					// Aqui ya podemos modificar el usuario
					try {
						boolean returnInsercion = dispositivoItemDao.modificarDispositivoItemValores(id, dispositivoItem);
						
						if (!returnInsercion) {				
							throw new DAOException("Ocurrio un inconveniente al modificar el registro en la base de datos");			
						}			
						
					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException("Ocurrio un inconveniente al modificar el registro en la base de datos");			
					}				
					
				}		
				
			}
			
			
		}else{			
			throw new ValueNotPermittedException("El id no esta permitido");
		}
						
		logger.debug("--- SALIR DE MODIFICAR DispositivoItem -----");
		
	}

	@Override
	public JSONRespuesta listarTablaDispositivosItem(String search, int start, int length, int draw, int posicion,
			String direccion) {
		return dispositivoItemDao.listarTablaDispositivoItem(search, start, length, draw, posicion, direccion);
	}
	
}
