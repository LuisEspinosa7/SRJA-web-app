/**
 * 
 */
package com.sevensoftware.domotica.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensoftware.domotica.dao.DepartamentoDao;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.services.DepartamentoService;

/**
 * @author LUIS
 *
 */
@Service("departamentoService")
public class DepartamentoServiceImpl implements DepartamentoService{
	
	@Autowired
	DepartamentoDao departamentoDao;

	@Override
	public String agregarDepartamento(Departamento departamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modificarDepartamento(int id, Departamento departamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eliminarDepartamento(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Departamento> listarDepartamento(int idPais) {
		List<Departamento> departamentos = departamentoDao.listarDepartamento(idPais);
		return departamentos;
	}	
	
}
