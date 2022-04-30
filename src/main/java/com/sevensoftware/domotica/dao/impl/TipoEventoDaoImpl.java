/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.TipoEventoDao;
import com.sevensoftware.domotica.entities.TipoEvento;
import com.sevensoftware.domotica.entities.TipoNodo;

/**
 * @author LUIS
 *
 */
@Repository("tipoEventoDao")
public class TipoEventoDaoImpl implements TipoEventoDao{

	private Logger logger = Logger.getLogger(TipoEventoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	
	
	@Override
	public TipoEvento obtenerTipoEvento(int id) {
		
		logger.debug("tipo evento del evento ");

		String SQL = "SELECT tie.tie_id, tie.tie_nombre, tie.tie_descripcion FROM tipo_evento tie WHERE tie.tie_id = ?;";

		TipoEvento tipoEvento = jdbcTemplateObject.query(SQL, new Object[] {id}, new ResultSetExtractor<TipoEvento>(){

			@Override
			public TipoEvento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					TipoEvento tipoEventoRow = new TipoEvento();
					tipoEventoRow.setId(rs.getInt("tie_id"));
					tipoEventoRow.setNombre(rs.getString("tie_nombre"));
					tipoEventoRow.setDescripcion(rs.getString("tie_descripcion"));
					return tipoEventoRow;
				}			
				return null;
			}		
		});
		return tipoEvento;
		
	}
	
	
	

}
