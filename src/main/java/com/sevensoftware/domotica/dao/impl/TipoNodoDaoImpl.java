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

import com.sevensoftware.domotica.dao.TipoNodoDao;
import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.TipoNodo;

/**
 * @author LuisLlanos
 *
 */
@Repository("tipoNodoDao")
public class TipoNodoDaoImpl implements TipoNodoDao {
	
	private Logger logger = Logger.getLogger(TipoNodoDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	

	@Override
	public List<TipoNodo> obtenerTiposNodo() {
		
		String sql = "SELECT tin.tin_id, tin.tin_nombre, tin.tin_descripcion FROM tipo_nodo tin;";
		List<TipoNodo> listaTipoNodos = jdbcTemplateObject.query(sql, new RowMapper<TipoNodo>() {

			public TipoNodo mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoNodo tipoNodo = new TipoNodo();
				tipoNodo.setId(rs.getInt("tin_id"));
				tipoNodo.setNombre(rs.getString("tin_nombre"));	
				tipoNodo.setDescripcion(rs.getString("tin_descripcion"));										
				return tipoNodo;
			}
		});
		return listaTipoNodos;		
	}
	

	@Override
	public TipoNodo obtenerTipoNodo(int id) {
		
		logger.debug("Obtiene el tipo nodo  ");

		String SQL = "SELECT tin.tin_id, tin.tin_nombre, tin.tin_descripcion FROM tipo_nodo tin WHERE tin.tin_id = ?;";

		TipoNodo tipoNodo = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<TipoNodo>(){

			@Override
			public TipoNodo extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					TipoNodo tipoNodoRow = new TipoNodo();
					tipoNodoRow.setId(rs.getInt("tin_id"));
					tipoNodoRow.setNombre(rs.getString("tin_nombre"));	
					tipoNodoRow.setDescripcion(rs.getString("tin_descripcion"));	
					return tipoNodoRow;
				}			
				return null;
			}		
		});
		return tipoNodo;		
	}
	
	

}
