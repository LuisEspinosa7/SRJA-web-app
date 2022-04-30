/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.EspacioDao;
import com.sevensoftware.domotica.dao.MedicionDao;
import com.sevensoftware.domotica.entities.TMedicion;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.services.MedicionService;

/**
 * @author LUIS
 *
 */
@Service("medicionService")
public class MedicionServiceImpl implements MedicionService {
	
	private Logger logger = Logger.getLogger(MedicionServiceImpl.class);

	@Autowired
	MedicionDao medicionDao;

	@Override
	public void agregarMedicion(int dispositivoItem, int tipoMedicion, int tipoValor, double valor) {
		
		// APLICAMOS VALIDACIONES
		logger.debug("--- EN AGREGAR MEDICION -----");

		logger.debug("Iniciando Validaciones de INSERCION DE MEDICION");

		if (dispositivoItem == 0 || tipoMedicion == 0 || tipoValor == 0) {
			throw new ValueNotPermittedException("No se permiten valores nulos");
		} else {

			// Aqui ya podemos guardar el espacio
			try {
				boolean returnInsercion = medicionDao.agregarMedicion(dispositivoItem, tipoMedicion, tipoValor, valor);

				if (!returnInsercion) {
					throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
				}

			} catch (DAOException daoe) {
				daoe.printStackTrace();
				throw new DAOException("Ocurrio un inconveniente al insertar el registro en la base de datos");
			}			

		}
		
	}

	@Override
	public void agregarMedicionNodo(List<TMedicion> mediciones) {
		
		medicionDao.agregarMedicionNodo(mediciones);
		
	}
	
	

}
