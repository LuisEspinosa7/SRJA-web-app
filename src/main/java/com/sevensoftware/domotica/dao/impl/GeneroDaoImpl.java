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

import com.sevensoftware.domotica.dao.GeneroDao;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.TipoIdentificacion;

/**
 * @author LUIS
 *
 */
@Repository("generoDao")
public class GeneroDaoImpl implements GeneroDao{
	
	private Logger logger = Logger.getLogger(GeneroDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	

	@Override
	public Genero obtenerGenero(String username) {
		logger.debug("genero del usuario ");

		String SQL = "SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g "
				+ "INNER JOIN persona p ON p.per_genero = g.gen_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		Genero generoUsuario = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<Genero>(){

			@Override
			public Genero extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Genero generoRow = new Genero();
					generoRow.setId(rs.getInt("gen_id"));
					generoRow.setNombre(rs.getString("gen_nombre"));
					generoRow.setAcronimo(rs.getString("gen_acronimo") == null ? ' ' : rs.getString(3).charAt(0));
					return generoRow;
				}			
				return null;
			}		
		});
		return generoUsuario;
	}

	@Override
	public List<Genero> obtenerGeneros() {
		
		String sql = "SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g;";
		List<Genero> listaGeneros = jdbcTemplateObject.query(sql, new RowMapper<Genero>() {

			public Genero mapRow(ResultSet rs, int rowNum) throws SQLException {
				Genero generoRow = new Genero();
				generoRow.setId(rs.getInt("gen_id"));
				generoRow.setNombre(rs.getString("gen_nombre"));	
				generoRow.setAcronimo(rs.getString("gen_acronimo") == null ? ' ' : rs.getString("gen_acronimo").charAt(0));								
				return generoRow;
			}
		});
		return listaGeneros;		
	}

	@Override
	public Genero obtenerGenero(int id) {
		logger.debug("genero del usuario ");

		String SQL = "SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g "
				+ "INNER JOIN persona p ON p.per_genero = g.gen_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Genero generoUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Genero>(){

			@Override
			public Genero extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Genero generoRow = new Genero();
					generoRow.setId(rs.getInt("gen_id"));
					generoRow.setNombre(rs.getString("gen_nombre"));
					generoRow.setAcronimo(rs.getString("gen_acronimo") == null ? ' ' : rs.getString(3).charAt(0));
					return generoRow;
				}			
				return null;
			}		
		});
		return generoUsuario;
	}

	@Override
	public Genero obtenerGeneroPorId(int id) {
		logger.debug("genero del usuario ");

		String SQL = "SELECT g.gen_id, g.gen_nombre, g.gen_acronimo FROM genero g WHERE g.gen_id = ?;";

		Genero generoUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Genero>(){

			@Override
			public Genero extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Genero generoRow = new Genero();
					generoRow.setId(rs.getInt("gen_id"));
					generoRow.setNombre(rs.getString("gen_nombre"));
					generoRow.setAcronimo(rs.getString("gen_acronimo") == null ? ' ' : rs.getString(3).charAt(0));
					return generoRow;
				}			
				return null;
			}		
		});
		return generoUsuario;
	}
	

}
