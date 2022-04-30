/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.entities.Nodo;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.dao.NodoDao;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.services.NodoService;

/**
 * @author LuisLlanos
 *
 */
@Service("nodoService")
public class NodoServiceImpl implements NodoService {
	
	private Logger logger = Logger.getLogger(NodoServiceImpl.class);
	
	@Autowired
	NodoDao nodoDao;

	@Override
	public void agregarNodo(Nodo nodo) {
		
		logger.debug("--- EN AGREGAR Nodo -----");

		logger.debug("Iniciando Validaciones de Inscripcion de Nodo");

		if (nodo.getCodigo() == null || nodo.getNombre() == null || nodo.getEstado() == null || nodo.getTipoNodo() == null || nodo.getEspacio() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el nodo exista por nombre y coordenada
			Boolean existeNodo = nodoDao.buscarNodo(nodo);

			if (existeNodo) {
				throw new ObjectAlreadyExistException("El nodo ya existe por Codigo o Nombre en determinado Espacio");
			} else {

				// Aqui ya podemos guardar el nodo
				try {
					boolean returnInsercion = nodoDao.agregarNodo(nodo);

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
	public void modificarNodo(int id, Nodo nodo) {
		
		logger.debug("--- EN MODIFICAR nodo -----");

		logger.debug("Iniciando Validaciones de MODIFICAR de nodo");

		if (nodo.getId() == 0 || nodo.getCodigo() == null || nodo.getNombre() == null || nodo.getEstado() == null || 
				nodo.getTipoNodo() == null || nodo.getEspacio() == null) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Buscamos que el nodo exista por nombre
			Boolean existeNodo = nodoDao.buscarNodo(nodo.getId());

			if (!existeNodo) {
				throw new ObjectAlreadyExistException("El nodo NO existe por Nombre O Codigo");
			} else {

				// Aqui ya podemos MODIFICAR el nodo
				try {
					boolean returnModificacion = nodoDao.modificarNodo(id, nodo);

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
	public void eliminarNodo(int id) {
		
		 logger.debug("--- EN ELIMINAR nodo -----");
		  
		 if (id > 0) {
		  
			 Boolean existeNodoPorId = nodoDao.buscarNodo(id);
			 
			 if (!existeNodoPorId) { 
				 throw new ObjectNotFoundException("La nodo no se puede eliminar, pues no existe"); 
			 } else {
				 // Aqui ya podemos modificar el Eps 
				 try { 
					 boolean returnInsercion = nodoDao.eliminarNodo(id);
					 
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
		 
		 logger.debug("--- SALIR DE ELIMINAR nodo -----");
		
	}

	@Override
	public JSONRespuesta listarTablaNodos(String search, int start, int length, int draw, int posicion,
			String direccion) {
		return nodoDao.listarTablaNodo(search, start, length, draw, posicion, direccion);
	}
	
	

}
