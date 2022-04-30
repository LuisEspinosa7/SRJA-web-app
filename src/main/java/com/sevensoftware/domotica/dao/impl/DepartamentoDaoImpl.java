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

import com.sevensoftware.domotica.dao.DepartamentoDao;
import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Pais;

/**
 * @author LUIS
 *
 */
@Repository("departamentoDao")
public class DepartamentoDaoImpl implements DepartamentoDao{
	
	private Logger logger = Logger.getLogger(DepartamentoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	PaisDao paisDao;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}

	@Override
	public boolean agregarDepartamento(Departamento departamento) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarDepartamento(int id, Departamento departamento) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarDepartamento(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Departamento> listarDepartamento(int idPais) {
		
		String sql = "select dep_id, dep_nombre, dep_pais from departamento where dep_pais = ? order by dep_nombre";
		List<Departamento> listaDepartamento = jdbcTemplateObject.query(sql, new Object[] { idPais },new RowMapper<Departamento>() {

			public Departamento mapRow(ResultSet rs, int rowNum) throws SQLException {
				Departamento departamento = new Departamento();
				departamento.setId(rs.getInt("dep_id"));
				departamento.setNombre(rs.getString("dep_nombre"));
				
				//Pais pais = paisDao.buscarPais(rs.getInt("dep_pais"));
						
				//departamento.setPais(pais);
				return departamento;
			}
		});
		return listaDepartamento;		
	}

	@Override
	public Departamento buscarDepartamento(int idDepartamento) {
		logger.debug("departamento de la ciudad ");

		String SQL = "select dep_id, dep_nombre from departamento where dep_id = ?;";

		Departamento departamentoObjeto = jdbcTemplateObject.query(SQL, new Object[] { idDepartamento }, new ResultSetExtractor<Departamento>(){

			@Override
			public Departamento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Departamento departamentoRow = new Departamento();
					departamentoRow.setId(rs.getInt("dep_id"));
					departamentoRow.setNombre(rs.getString("dep_nombre"));
					return departamentoRow;
				}			
				return null;
			}		
		});
		return departamentoObjeto;
	}

	@Override
	public Departamento obtenerDepartamento(String username) {
		logger.debug("Departamento del usuario ");

		String SQL = "select d.dep_id, d.dep_nombre from departamento d "
				+ "INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		Departamento departamentoUsuario = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<Departamento>(){

			@Override
			public Departamento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Departamento departamentoRow = new Departamento();
					departamentoRow.setId(rs.getInt("dep_id"));
					departamentoRow.setNombre(rs.getString("dep_nombre"));
					
					//Departamento departamento = departamentoDao.buscarDepartamento(rs.getInt("ciu_departamento"));				
					//Pais pais = paisDao.buscarPais(departamento.getId());						
					//departamento.setPais(pais);
					//ciudadRow.setDepartamento(departamento);
					
					return departamentoRow;
				}			
				return null;
			}		
		});
		return departamentoUsuario;	
	}

	@Override
	public Departamento obtenerDepartamento(int id) {
		logger.debug("Departamento del usuario ");

		String SQL = "select d.dep_id, d.dep_nombre from departamento d WHERE d.dep_id = ?;";

		Departamento departamentoUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Departamento>(){

			@Override
			public Departamento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Departamento departamentoRow = new Departamento();
					departamentoRow.setId(rs.getInt("dep_id"));
					departamentoRow.setNombre(rs.getString("dep_nombre"));						
					
					return departamentoRow;
				}			
				return null;
			}		
		});
		return departamentoUsuario;	
	}

	@Override
	public Departamento obtenerDepartamentoPorIdDeUsuario(int id) {

		logger.debug("Departamento del usuario ");

		String SQL = "select d.dep_id, d.dep_nombre from departamento d "
				+ "INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Departamento departamentoUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Departamento>(){

			@Override
			public Departamento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Departamento departamentoRow = new Departamento();
					departamentoRow.setId(rs.getInt("dep_id"));
					departamentoRow.setNombre(rs.getString("dep_nombre"));				
					
					return departamentoRow;
				}			
				return null;
			}		
		});
		return departamentoUsuario;	
		
	}
	
}
