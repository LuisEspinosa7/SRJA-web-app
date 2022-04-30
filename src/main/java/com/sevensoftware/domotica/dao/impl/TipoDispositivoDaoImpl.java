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

import com.sevensoftware.domotica.dao.TipoDispositivoDao;
import com.sevensoftware.domotica.entities.Categoria;
import com.sevensoftware.domotica.entities.TipoDispositivo;
import com.sevensoftware.domotica.entities.TipoDispositivo;

/**
 * @author LUIS
 *
 */
@Repository("tipoDispositivoDao")
public class TipoDispositivoDaoImpl implements TipoDispositivoDao {

	private Logger logger = Logger.getLogger(TipoDispositivoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
		
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	
	
	@Override
	public Boolean agregarTipoDispositivo(TipoDispositivo tipoDispositivo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean modificarTipoDispositivo(TipoDispositivo tipoDispositivo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoDispositivo obtenerTipoDispositivo(int id) {
		
		logger.debug("Buscando por Id - Tipo Dispositivo .... ");
		logger.debug("Dentro de dao de Tipo Dispositivo");
		

		String SQL = "SELECT td.td_id, td.td_nombre, td.td_descripcion FROM tipo_dispositivo td WHERE td.td_id = ?;";

		TipoDispositivo tipoDispositivoResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<TipoDispositivo>() {

					@Override
					public TipoDispositivo extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
																	
							TipoDispositivo tipoDispositivoRow = new TipoDispositivo();
							tipoDispositivoRow.setId(rs.getInt("td_id"));
							tipoDispositivoRow.setNombre(rs.getString("td_nombre"));
							tipoDispositivoRow.setDescripcion(rs.getString("td_descripcion"));
																			
							logger.debug("CATEGORIA SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + tipoDispositivoRow.getId()
									
									+ tipoDispositivoRow.getNombre());

							return tipoDispositivoRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por tipo  dispositivo .... ");
		System.out.println("Saliendo del dao de tipo  dispositivo");
		return tipoDispositivoResponse;
		
	}


	@Override
	public List<TipoDispositivo> obtenerTiposDispositivo() {
		
		String sql = "SELECT td.td_id, td.td_nombre, td.td_descripcion FROM tipo_dispositivo td;";
		List<TipoDispositivo> listaTipoDispositivos = jdbcTemplateObject.query(sql, new RowMapper<TipoDispositivo>() {

			public TipoDispositivo mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoDispositivo tipoDispositivo = new TipoDispositivo();
				tipoDispositivo.setId(rs.getInt("td_id"));
				tipoDispositivo.setNombre(rs.getString("td_nombre"));	
				tipoDispositivo.setDescripcion(rs.getString("td_descripcion"));										
				return tipoDispositivo;
			}
		});
		return listaTipoDispositivos;	
	}
	

}
