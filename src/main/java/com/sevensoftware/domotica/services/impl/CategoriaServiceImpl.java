/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.CategoriaDao;
import com.sevensoftware.domotica.dao.TipoDispositivoDao;
import com.sevensoftware.domotica.entities.Categoria;
import com.sevensoftware.domotica.entities.TipoDispositivo;
import com.sevensoftware.domotica.services.CategoriaService;

/**
 * @author LUIS
 *
 */

@Service("categoriaService")
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	CategoriaDao categoriaoDao;

	@Override
	public List<Categoria> obtenerCategorias() {
		List<Categoria> categorias = categoriaoDao.obtenerCategorias();
		return categorias;
	}
	
	
	

}
