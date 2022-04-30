/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.EventoDao;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.Evento;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.services.EventoService;

/**
 * @author LUIS
 *
 */
@Service("eventoService")
public class EventoServiceImpl implements EventoService{
	
	private Logger logger = Logger.getLogger(EventoServiceImpl.class);
	
	@Autowired
	EventoDao eventoDao;
	

	@Override
	public void agregarEvento(Evento evento) {
		
		/**
		// Aqui ya podemos guardar el vivienda
		try {
			boolean returnInsercion = eventoDao.agregarEvento(evento);
			
			if (!returnInsercion) {				
				throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
			}			
			
		} catch (DAOException daoe) {
			daoe.printStackTrace();
			throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
		}
		**/
		
	}


	@Override
	public void modificarEvento(int id, Evento evento) {

		// Aqui ya podemos guardar el vivienda
		try {
			boolean returnInsercion = eventoDao.modificarEvento(id, evento);

			if (!returnInsercion) {
				throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);
			}

		} catch (DAOException daoe) {
			daoe.printStackTrace();
			throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);
		}

	}
	
	
	
	
	

}
