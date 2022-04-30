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

import com.sevensoftware.domotica.dao.CoordenadaDao;
import com.sevensoftware.domotica.entities.Coordenada;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("coordenadaDao")
public class CoordenadaDaoImpl implements CoordenadaDao{
	
	private Logger logger = Logger.getLogger(CoordenadaDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	@Override
	public Boolean agregarCoordenada(Coordenada coordenada) {

		logger.debug("--- AGREGANDO NUEVA COODENADA ------");
		
		String SQL1 = "INSERT INTO coordenada (coo_latitud, coo_longitud, coo_altitud) VALUES (?, ?, ?);";
		
		int resultado1 = jdbcTemplateObject.update(SQL1, coordenada.getLatitud(), coordenada.getLongitud(), coordenada.getAltitud());
		
		if (resultado1 > 0) {
			logger.debug("Se inserto la COORDENADA");
			return true;			
		}else{
			return false;
		}	
	}


	@Override
	public Coordenada obtenerCoordenada(int id) {
		
		logger.debug("Obtiene la coordenada  ");

		String SQL = "SELECT coo.coo_id, coo.coo_latitud, coo.coo_longitud, coo.coo_altitud FROM coordenada coo WHERE coo.coo_id = ?;";

		Coordenada coordenada = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Coordenada>(){

			@Override
			public Coordenada extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Coordenada coordenadaRow = new Coordenada();
					coordenadaRow.setId(rs.getInt("coo_id"));
					coordenadaRow.setLatitud(rs.getString("coo_latitud"));	
					coordenadaRow.setLongitud(rs.getString("coo_longitud"));
					coordenadaRow.setAltitud(rs.getString("coo_altitud"));
					return coordenadaRow;
				}			
				return null;
			}		
		});
		return coordenada;		
	}


	@Override
	public Boolean modificarCoordenada(Coordenada coordenada) {
		
		logger.debug("--- MODIFICANDO NUEVA COODENADA ------");
		
		String SQL1 = "UPDATE coordenada SET coo_latitud = ?, coo_longitud = ?, coo_altitud = ? WHERE coo_id = ?;";
		
		int resultado1 = jdbcTemplateObject.update(SQL1, coordenada.getLatitud(), coordenada.getLongitud(), coordenada.getAltitud(), coordenada.getId());
		
		if (resultado1 > 0) {
			logger.debug("Se modifico la COORDENADA");
			return true;			
		}else{
			return false;
		}
		
	}	
	

}
