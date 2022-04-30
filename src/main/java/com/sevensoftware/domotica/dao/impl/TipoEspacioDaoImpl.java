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

import com.sevensoftware.domotica.dao.TipoEspacioDao;
import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.TipoNodo;

/**
 * @author LUIS
 *
 */
@Repository("tipoEspacioDao")
public class TipoEspacioDaoImpl implements TipoEspacioDao{
	
	private Logger logger = Logger.getLogger(NodoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
		
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	

	@Override
	public boolean agregarTipoEspacio(TipoEspacio tipoNodo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarTipoEspacio(int id, TipoEspacio tipoNodo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarTipoEspacio(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONRespuesta listarTablaTipoEspacio(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean buscarTipoEspacio(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoEspacio> listarTipoEspacios() {
		String sql = "SELECT te.te_id, te.te_nombre, te.te_descripcion FROM tipo_espacio te";
		List<TipoEspacio> listaTipoEspacios = jdbcTemplateObject.query(sql, new Object[] {},new RowMapper<TipoEspacio>() {

			public TipoEspacio mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				TipoEspacio tipoEspacioRow = obtenerTipoEspacio(rs.getInt("te_id"));
				return tipoEspacioRow;
				
			}
		});
		return listaTipoEspacios;
	}

	@Override
	public TipoEspacio obtenerTipoEspacio(int id) {
		
		logger.debug("tipo espacio ");

		String SQL = "SELECT te.te_id, te.te_nombre, te.te_descripcion FROM tipo_espacio te WHERE te.te_id = ?;";

		TipoEspacio tipoEspacio = jdbcTemplateObject.query(SQL, new Object[] {id}, new ResultSetExtractor<TipoEspacio>(){

			@Override
			public TipoEspacio extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					TipoEspacio tipoEspacioRow = new TipoEspacio();
					tipoEspacioRow.setId(rs.getInt("te_id"));
					tipoEspacioRow.setNombre(rs.getString("te_nombre"));
					tipoEspacioRow.setDescripcion(rs.getString("te_descripcion"));
					return tipoEspacioRow;
				}			
				return null;
			}		
		});
		return tipoEspacio;
		
	}
	

}
