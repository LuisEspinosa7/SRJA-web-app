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

import com.sevensoftware.domotica.dao.UnidadDao;
import com.sevensoftware.domotica.entities.Unidad;
import com.sevensoftware.domotica.entities.Unidad;

/**
 * @author LUIS
 *
 */
@Repository("unidadDao")
public class UnidadDaoImpl implements UnidadDao{

	private Logger logger = Logger.getLogger(UnidadDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	@Override
	public Unidad obtenerUnidad(int id) {
		
		logger.debug("Buscando por Id - Unidad .... ");
		logger.debug("Dentro de dao de Unidad");
		

		String SQL = "SELECT u.uni_id, u.uni_nombre, u.uni_acronimo, u.uni_descripcion FROM unidad u WHERE u.uni_id = ?;";

		Unidad unidadResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Unidad>() {

					@Override
					public Unidad extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
																												
							Unidad unidadRow = new Unidad();
							unidadRow.setId(rs.getInt("uni_id"));
							unidadRow.setNombre(rs.getString("uni_nombre"));
							unidadRow.setAcronimo(rs.getString("uni_acronimo"));
							unidadRow.setDescripcion(rs.getString("uni_descripcion"));
														
							logger.debug("UNIDAD SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + unidadRow.getId()
									+ " UNIDAD = "
									+ unidadRow.getNombre());

							return unidadRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por Unidad .... ");
		System.out.println("Saliendo del dao de Unidad");
		return unidadResponse;
		
	}
	
	
	
	
	
	
	

}
