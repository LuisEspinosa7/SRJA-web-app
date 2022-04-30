/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.CiudadDao;
import com.sevensoftware.domotica.dao.DepartamentoDao;
import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Pais;

/**
 * @author LUIS
 *
 */
@Repository("ciudadDao")
public class CiudadDaoImpl implements CiudadDao{
	
	private Logger logger = Logger.getLogger(CiudadDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	PaisDao paisDao;
	
	@Autowired
	DepartamentoDao departamentoDao;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}

	@Override
	public boolean agregarCiudad(Ciudad ciudad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarCiudad(int id, Ciudad ciudad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarCiudad(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ciudad> listarCiudad(int idDepartamento) {
		
		String sql = "select ciu_id, ciu_nombre, ciu_departamento from cuidad where ciu_departamento = ? order by ciu_nombre";
		List<Ciudad> listaCiudades = jdbcTemplateObject.query(sql, new Object[] { idDepartamento },new RowMapper<Ciudad>() {

			public Ciudad mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ciudad ciudadRow = new Ciudad();
				ciudadRow.setId(rs.getInt("ciu_id"));
				ciudadRow.setNombre(rs.getString("ciu_nombre"));
				
				//Departamento departamento = departamentoDao.buscarDepartamento(rs.getInt("ciu_departamento"));				
				//Pais pais = paisDao.buscarPais(departamento.getId());						
				//departamento.setPais(pais);
				//ciudadRow.setDepartamento(departamento);
				
				return ciudadRow;
			}
		});
		return listaCiudades;		
	}

	@Override
	public Ciudad obtenerCiudad(String username) {
		
		logger.debug("Ciudad del usuario ");

		String SQL = "select c.ciu_id, c.ciu_nombre, c.ciu_departamento from cuidad c "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		Ciudad ciudadUsuario = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<Ciudad>(){

			@Override
			public Ciudad extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Ciudad ciudadRow = new Ciudad();
					ciudadRow.setId(rs.getInt("ciu_id"));
					ciudadRow.setNombre(rs.getString("ciu_nombre"));
					
					//Departamento departamento = departamentoDao.buscarDepartamento(rs.getInt("ciu_departamento"));				
					//Pais pais = paisDao.buscarPais(departamento.getId());						
					//departamento.setPais(pais);
					//ciudadRow.setDepartamento(departamento);
					
					return ciudadRow;
				}			
				return null;
			}		
		});
		return ciudadUsuario;		
	}

	@Override
	public Ciudad obtenerCiudad(int id) {
		logger.debug("Ciudad del usuario ");

		String SQL = "select c.ciu_id, c.ciu_nombre, c.ciu_departamento from cuidad c "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Ciudad ciudadUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Ciudad>(){

			@Override
			public Ciudad extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Ciudad ciudadRow = new Ciudad();
					ciudadRow.setId(rs.getInt("ciu_id"));
					ciudadRow.setNombre(rs.getString("ciu_nombre"));			
					
					return ciudadRow;
				}			
				return null;
			}		
		});
		return ciudadUsuario;
	}

	@Override
	public Ciudad obtenerCiudadPorId(int id) {
		logger.debug("Ciudad del usuario ");

		String SQL = "select c.ciu_id, c.ciu_nombre, c.ciu_departamento from cuidad c WHERE c.ciu_id = ?;";

		Ciudad ciudadUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Ciudad>(){

			@Override
			public Ciudad extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Ciudad ciudadRow = new Ciudad();
					ciudadRow.setId(rs.getInt("ciu_id"));
					ciudadRow.setNombre(rs.getString("ciu_nombre"));			
					
					return ciudadRow;
				}			
				return null;
			}		
		});
		return ciudadUsuario;
	}	
	
}
