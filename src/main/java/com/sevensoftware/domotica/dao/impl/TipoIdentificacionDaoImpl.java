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

import com.sevensoftware.domotica.dao.TipoIdentificacionDao;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.TipoIdentificacion;

/**
 * @author LUIS
 *
 */
@Repository("tipoIdentificacionDao")
public class TipoIdentificacionDaoImpl implements TipoIdentificacionDao{
	
	private Logger logger = Logger.getLogger(TipoIdentificacionDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	
	

	@Override
	public TipoIdentificacion obtenerTipoIdentificacion(String username) {
		logger.debug("Obteniendo tipo de identificacion ");

		String SQL = "SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo FROM tipo_identificacion ti "
				+ "INNER JOIN persona p ON p.per_tipo_identificacion = ti.tii_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		TipoIdentificacion tipoIdentificacion = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<TipoIdentificacion>() {

			@Override
			public TipoIdentificacion extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (rs.next()) {
					TipoIdentificacion tipoIdentificacionRow = new TipoIdentificacion();
					tipoIdentificacionRow.setId(rs.getInt("tii_id"));
					tipoIdentificacionRow.setNombre(rs.getString("tii_nombre"));
					tipoIdentificacionRow.setAcronimo(rs.getString("tii_acronimo"));
					return tipoIdentificacionRow;
				}		
				return null;
			}			
		});
		return tipoIdentificacion;
	}



	@Override
	public List<TipoIdentificacion> obtenerTiposIdentificacion() {

		String sql = "SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo  FROM tipo_identificacion ti;";
		List<TipoIdentificacion> listaTipoIdentificacion = jdbcTemplateObject.query(sql, new RowMapper<TipoIdentificacion>() {

			public TipoIdentificacion mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
				tipoIdentificacion.setId(rs.getInt("tii_id"));
				tipoIdentificacion.setNombre(rs.getString("tii_nombre"));	
				tipoIdentificacion.setAcronimo(rs.getString("tii_acronimo"));										
				return tipoIdentificacion;
			}
		});
		return listaTipoIdentificacion;	
	}



	@Override
	public TipoIdentificacion obtenerTipoIdentificacion(int id) {
		logger.debug("Obteniendo tipo de identificacion ");

		String SQL = "SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo FROM tipo_identificacion ti "
				+ "INNER JOIN persona p ON p.per_tipo_identificacion = ti.tii_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		TipoIdentificacion tipoIdentificacion = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<TipoIdentificacion>() {

			@Override
			public TipoIdentificacion extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (rs.next()) {
					TipoIdentificacion tipoIdentificacionRow = new TipoIdentificacion();
					tipoIdentificacionRow.setId(rs.getInt("tii_id"));
					tipoIdentificacionRow.setNombre(rs.getString("tii_nombre"));
					tipoIdentificacionRow.setAcronimo(rs.getString("tii_acronimo"));
					return tipoIdentificacionRow;
				}		
				return null;
			}			
		});
		return tipoIdentificacion;
	}



	@Override
	public TipoIdentificacion obtenerTipoIdentificacionPorId(int id) {
		logger.debug("Obteniendo tipo de identificacion ");

		String SQL = "SELECT ti.tii_id, ti.tii_nombre, ti.tii_acronimo FROM tipo_identificacion ti WHERE ti.tii_id = ?;";

		TipoIdentificacion tipoIdentificacion = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<TipoIdentificacion>() {

			@Override
			public TipoIdentificacion extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (rs.next()) {
					TipoIdentificacion tipoIdentificacionRow = new TipoIdentificacion();
					tipoIdentificacionRow.setId(rs.getInt("tii_id"));
					tipoIdentificacionRow.setNombre(rs.getString("tii_nombre"));
					tipoIdentificacionRow.setAcronimo(rs.getString("tii_acronimo"));
					return tipoIdentificacionRow;
				}		
				return null;
			}			
		});
		return tipoIdentificacion;
	}
	
	

}
