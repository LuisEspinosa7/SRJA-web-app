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

import com.sevensoftware.domotica.dao.EspacioDao;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.TipoEspacioDao;
import com.sevensoftware.domotica.dao.ViviendaDao;
import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Nodo;
import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.TipoNodo;
import com.sevensoftware.domotica.entities.Vivienda;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("espacioDao")
public class EspacioDaoImpl implements EspacioDao {
	
	private Logger logger = Logger.getLogger(NodoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	TipoEspacioDao tipoEspacioDao;
	
	@Autowired
	ViviendaDao viviendaDao;
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	
	
	@Override
	public boolean agregarEspacio(Espacio espacio) {
		
		logger.debug("--- AGREGANDO NUEVA ESPACIO ------");
		
		String SQL = "INSERT INTO espacio (esp_nombre, esp_descripcion, esp_ancho, esp_largo, esp_estado, esp_tipo_espacio, esp_vivienda) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		int resultado = jdbcTemplateObject.update(SQL, espacio.getNombre().toUpperCase(), espacio.getDescripcion().toUpperCase(), espacio.getAncho(),
				espacio.getLargo(), espacio.getEstado().getId(), espacio.getTipoEspacio().getId(), espacio.getVivienda().getId());
		
		if (resultado > 0) {
			logger.debug("Se inserto el ESPACIO");
			return true;			
		}else{
			return false;					
		}
		
		
	}

	@Override
	public boolean modificarEspacio(int id, Espacio espacio) {

		logger.debug("--- DENTRO DE MODIFICAR ESPACIO --------");		
	
			
		String SQL = "UPDATE espacio esp SET esp_nombre = ?, esp_descripcion = ?, "
					+ "esp_ancho = ?, esp_largo = ?, esp_estado = ?, esp_tipo_espacio = ?, esp_vivienda = ?"
					+ "WHERE esp.esp_id = ?;";
			
		int resultado1 = jdbcTemplateObject.update(SQL, espacio.getNombre(), espacio.getDescripcion(), espacio.getAncho(), espacio.getLargo(),
				espacio.getEstado().getId(), espacio.getTipoEspacio().getId(), espacio.getVivienda().getId(), id);
			
		if (resultado1 > 0) {
			logger.debug("--- ESPACIO MODIFICADa -----");
			return true;				
		}else{
			logger.debug("No se pudo MODIFICAR el ESPACIO");
			throw new DAOException("No se pudo MODIFICAR el ESPACIO");
		}
			
		
	}

	@Override
	public boolean eliminarEspacio(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR ESPACIO --------");
		
		int idEliminado = 5;
										
		String SQL1 = "UPDATE espacio SET esp_estado = ? WHERE esp_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, idEliminado, id);
			
		if (resultado1 > 0) {
			logger.debug("--- ESPACIO CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public JSONRespuesta listarTablaEspacio(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug("Dentro de listar ESPACIOS en datatables");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "esp.esp_id", "esp.esp_nombre", "esp.esp_ancho", "esp.esp_largo", "est.est_nombre", "te.te_nombre", "viv.viv_direccion"};

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM espacio esp "
				+ "INNER JOIN tipo_espacio te ON esp.esp_tipo_espacio = te.te_id "
				+ "INNER JOIN estado est ON esp.esp_estado = est.est_id "
				+ "INNER JOIN vivienda viv ON esp.esp_vivienda = viv.viv_id "
				+ "WHERE esp.esp_estado IN (1, 2, 7) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		logger.debug("Cantidad de ESPACIOS =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (esp.esp_nombre LIKE ? OR te.te_nombre LIKE ? ";
			sql = sql + " OR est.est_nombre LIKE ? )";
					

		filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT esp_id, esp_nombre, esp_ancho, esp_largo, est_nombre, te_nombre, viv_direccion "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "esp.esp_id, esp.esp_nombre, esp.esp_ancho, esp.esp_largo, est.est_nombre, te.te_nombre, viv.viv_direccion FROM espacio esp "
				+ "INNER JOIN tipo_espacio te ON esp.esp_tipo_espacio = te.te_id "
				+ "INNER JOIN estado est ON esp.esp_estado = est.est_id "
				+ "INNER JOIN vivienda viv ON esp.esp_vivienda = viv.viv_id "
				+ "WHERE esp.esp_estado IN (1, 2, 7) "
				+ "AND (esp.esp_nombre LIKE ? OR te.te_nombre LIKE ? OR est.est_nombre LIKE ? ))";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<Espacio> listaEspacio = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Espacio>() {					

					public Espacio mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("Iterando ");
						Espacio espacio = obtenerEspacio(rs.getInt("esp_id"));
						return espacio;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaEspacio);

		return respuesta;
		
	}

	@Override
	public Boolean buscarEspacio(int id) {
		logger.debug("------- Buscando ESPACIO por id");
		
		String sql = "SELECT COUNT(*) as cant FROM espacio esp WHERE esp.esp_id = ? AND esp.esp_estado IN (1, 2, 7)";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean buscarEspacioPorNombre(String nombre) {
		logger.debug("------- Buscando ESPACIO por nombre");
		
		String sql = "SELECT COUNT(*) as cant FROM espacio esp WHERE esp.esp_nombre = ? AND esp.esp_estado IN (1, 2, 7)";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{nombre}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Espacio> listarEspacios() {
		String sql = "select esp.esp_id, esp.esp_nombre, esp.esp_descripcion, esp.esp_ancho, esp.esp_largo, esp.esp_estado, esp.esp_tipo_espacio, esp.esp_vivienda from espacio esp";
		List<Espacio> listaEspacios = jdbcTemplateObject.query(sql, new Object[] {},new RowMapper<Espacio>() {

			public Espacio mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Espacio espacioRow = obtenerEspacio(rs.getInt("esp_id"));
				return espacioRow;
				
			}
		});
		return listaEspacios;
	}

	@Override
	public Espacio obtenerEspacio(int id) {
		
		logger.debug("Buscando por Id - Esp .... ");
		logger.debug("Dentro de dao de ESPACIO");
		

		String SQL = "SELECT e.esp_id, e.esp_nombre, e.esp_descripcion, e.esp_estado, e.esp_tipo_espacio, e.esp_ancho, e.esp_largo, e.esp_vivienda FROM espacio e WHERE e.esp_id = ?;";

		Espacio espacioResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Espacio>() {

					@Override
					public Espacio extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("esp_estado"));
							TipoEspacio tipoEspacio = tipoEspacioDao.obtenerTipoEspacio(rs.getInt("esp_tipo_espacio"));
							Vivienda vivienda = viviendaDao.obtenerVivienda(rs.getInt("esp_vivienda"));
							
																				
							Espacio espacioRow = new Espacio();
							espacioRow.setId(rs.getInt("esp_id"));
							espacioRow.setNombre(rs.getString("esp_nombre"));
							espacioRow.setDescripcion(rs.getString("esp_descripcion"));
							espacioRow.setAncho(rs.getDouble("esp_ancho"));
							espacioRow.setLargo(rs.getDouble("esp_largo"));
							
							espacioRow.setEstado(estado);
							espacioRow.setTipoEspacio(tipoEspacio);
							espacioRow.setVivienda(vivienda);
							
							logger.debug("ESPACIO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + espacioRow.getId()
									+ ", NOMBRE= " + espacioRow.getNombre());

							return espacioRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por espacio .... ");
		System.out.println("Saliendo del dao de espacio");
		return espacioResponse;
		
	}


	

	
	
	
	
	
	
}
