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

import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Genero;

/**
 * @author LUIS
 *
 */
@Repository("estadoDao")
public class EstadoDaoImpl implements EstadoDao{
	
	private Logger logger = Logger.getLogger(EstadoDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	

	@Override
	public Estado obtenerEstado(String username) {
		logger.debug("estado del usuario ");

		String SQL = "SELECT e.est_id, e.est_nombre FROM estado e "
				+ "INNER JOIN usuario u ON u.usu_estado = e.est_id WHERE u.usu_login = ?;";

		Estado estadoUsuario = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<Estado>(){

			@Override
			public Estado extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Estado estadoRow = new Estado();
					estadoRow.setId(rs.getInt(1));
					estadoRow.setNombre(rs.getString(2));
					return estadoRow;
				}			
				return null;
			}		
		});
		return estadoUsuario;
	}


	@Override
	public List<Estado> listarEstados() {
		
		String sql = " select e.est_id, e.est_nombre, e.est_descripcion from estado e;";
		List<Estado> listaEstados = jdbcTemplateObject.query(sql, new RowMapper<Estado>() {

			public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
				Estado estadoRow = new Estado();
				estadoRow.setId(rs.getInt("est_id"));
				estadoRow.setNombre(rs.getString("est_nombre"));											
				return estadoRow;
			}
		});
		return listaEstados;	
		
	}


	@Override
	public List<Estado> buscarEstadosPorEntidad(String entidad) {
		
		logger.debug("Listado de estados por entidad ------------------");

		String SQL = "SELECT est.est_id, est.est_nombre FROM estado est "
				+ "INNER JOIN estado_entidad ee ON ee.ee_estado = est.est_id "
				+ "INNER JOIN entidad e ON e.ent_id = ee.ee_entidad "
				+ "WHERE e.ent_nombre = ?;";

		List<Estado> estados = jdbcTemplateObject.query(SQL, new Object[]{entidad}, new RowMapper<Estado>() {
			public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
				Estado estadoRow = new Estado();
				estadoRow.setId(rs.getInt(1));
				estadoRow.setNombre(rs.getString(2));
				return estadoRow;
			}
		});
		return estados;		
	}


	@Override
	public Estado obtenerEstado(int id) {
		
		logger.debug("estado del usuario ");

		String SQL = "SELECT e.est_id, e.est_nombre FROM estado e "
				+ "INNER JOIN persona p ON p.per_estado = e.est_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Estado estadoUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Estado>(){

			@Override
			public Estado extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Estado estadoRow = new Estado();
					estadoRow.setId(rs.getInt("est_id"));
					estadoRow.setNombre(rs.getString("est_nombre"));
					return estadoRow;
				}			
				return null;
			}		
		});
		return estadoUsuario;
		
	}


	@Override
	public Estado obtenerEstadoPorId(int id) {
		
		logger.debug("Obtiene el estado  ");

		String SQL = "SELECT e.est_id, e.est_nombre FROM estado e WHERE e.est_id = ?;";

		Estado estado = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Estado>(){

			@Override
			public Estado extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Estado estadoRow = new Estado();
					estadoRow.setId(rs.getInt("est_id"));
					estadoRow.setNombre(rs.getString("est_nombre"));
					return estadoRow;
				}			
				return null;
			}		
		});
		return estado;
		
	}
	
	
	
	
	
	
}
