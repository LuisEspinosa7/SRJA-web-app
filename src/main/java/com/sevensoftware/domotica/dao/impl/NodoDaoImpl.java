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
import com.sevensoftware.domotica.dao.NodoDao;
import com.sevensoftware.domotica.dao.TipoNodoDao;

import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Nodo;
import com.sevensoftware.domotica.entities.TipoNodo;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("nodoDao")
public class NodoDaoImpl implements NodoDao {
	
	private Logger logger = Logger.getLogger(NodoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	TipoNodoDao tipoNodoDao;
	
	@Autowired
	EspacioDao espacioDao;	
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	

	@Override
	public boolean agregarNodo(Nodo nodo) {
		
		logger.debug("--- AGREGANDO NUEVA NODO ------");
		
		try {
							
			String SQL1 = "INSERT INTO nodo (nod_codigo, nod_nombre, nod_estado, nod_tipo, nod_espacio) VALUES (?, ?, ?, ?, ?);";
				
			int resultado1 = jdbcTemplateObject.update(SQL1, nodo.getCodigo(), nodo.getNombre().toUpperCase(), nodo.getEstado().getId(), 
					nodo.getTipoNodo().getId(), nodo.getEspacio().getId());
				
				if (resultado1 > 0) {
					logger.debug("Se inserto el nodo");
					return true;			
				}else{
					return false;					
				}					
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean modificarNodo(int id, Nodo nodo) {
		
		logger.debug("--- DENTRO DE MODIFICAR nodo --------");	
		
		String SQL1 = "UPDATE nodo SET nod_codigo = ?, nod_nombre = ?, nod_estado = ?, nod_tipo = ?, nod_espacio = ? WHERE nod_id = ?;";
			
		int resultado1 = jdbcTemplateObject.update(SQL1, nodo.getCodigo(), nodo.getNombre().toUpperCase(), nodo.getEstado().getId(), nodo.getTipoNodo().getId(), 
					nodo.getEspacio().getId(),id);
			
			if (resultado1 > 0) {
				logger.debug("--- nodo MODIFICADa -----");
				return true;				
			}else{
				logger.debug("No se pudo MODIFICAR el nodo");
				throw new DAOException("No se pudo MODIFICAR el nodo");
			}
		
	}

	@Override
	public boolean eliminarNodo(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR nodo --------");
		
		int idEliminado = 5;
										
		String SQL1 = "UPDATE nodo SET nod_estado = ? WHERE nod_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, idEliminado, id);
			
		if (resultado1 > 0) {
			logger.debug("--- nodo CAMBIADO DE ESTADO A DESCONTINUO ------");
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public JSONRespuesta listarTablaNodo(String search, int start, int length, int draw, int posicion, String direccion) {
		
		logger.debug("Dentro de listar nodos en datatables");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "nod.nod_id", "nod.nod_codigo", "nod.nod_nombre", "est.est_nombre", "tin.tin_nombre", "esp.esp_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM nodo nod "
				+ "INNER JOIN tipo_nodo tin ON nod.nod_tipo = tin.tin_id "
				+ "INNER JOIN espacio esp ON esp.esp_id = nod.nod_espacio  "
				+ "INNER JOIN estado est ON nod.nod_estado = est.est_id "				
				+ "WHERE nod.nod_estado IN (1, 7) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		logger.debug("Cantidad de nodo =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND ( nod.nod_codigo LIKE ? OR nod.nod_nombre LIKE ? OR tin.tin_nombre LIKE ? ";
			sql = sql + " OR est.est_nombre LIKE ? OR esp.esp_nombre LIKE ? )";
					

		filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%", "&" + search + "%", "%" + search + "%",
					"%" + search + "%" }, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT nod_id, nod_codigo, nod_nombre, est_nombre, tin_nombre, esp_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "nod.nod_id, nod.nod_codigo, nod.nod_nombre, est.est_nombre, tin.tin_nombre, esp.esp_nombre FROM nodo nod "
				+ "INNER JOIN tipo_nodo tin ON nod.nod_tipo = tin.tin_id "
				+ "INNER JOIN espacio esp ON esp.esp_id = nod.nod_espacio "
				+ "INNER JOIN estado est ON nod.nod_estado = est.est_id "
				+ "WHERE nod.nod_estado IN (1, 7) "
				+ "AND (nod.nod_codigo LIKE ? OR nod.nod_nombre LIKE ? OR tin.tin_nombre LIKE ? OR est.est_nombre LIKE ? OR esp.esp_nombre LIKE ? ))";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<Nodo> listaNodo = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Nodo>() {					

					public Nodo mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("Iterando ");
						Nodo nodo = obtenerNodo(rs.getInt("nod_id"));
						return nodo;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaNodo);

		return respuesta;	
		
	}

	

	@Override
	public Nodo obtenerNodo(int id) {

		logger.debug("Buscando por Id - Nodo .... ");
		logger.debug("Dentro de dao de Nodo");
		

		String SQL = "SELECT n.nod_id, n.nod_codigo, n.nod_nombre, n.nod_estado, n.nod_tipo, n.nod_espacio FROM nodo n WHERE n.nod_id = ?;";

		Nodo nodoResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Nodo>() {

					@Override
					public Nodo extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("nod_estado"));
							TipoNodo tipoNodo = tipoNodoDao.obtenerTipoNodo(rs.getInt("nod_tipo"));
							Espacio espacio = espacioDao.obtenerEspacio(rs.getInt("nod_espacio"));
													
							Nodo nodoRow = new Nodo();
							nodoRow.setId(rs.getInt("nod_id"));
							nodoRow.setCodigo(rs.getString("nod_codigo"));
							nodoRow.setNombre(rs.getString("nod_nombre"));
							nodoRow.setEstado(estado);
							nodoRow.setEspacio(espacio);
							nodoRow.setTipoNodo(tipoNodo);
							
							logger.debug("NODO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + nodoRow.getId()
									+ " Codigo= " + nodoRow.getCodigo() + ", NODO= "
									+ nodoRow.getNombre());

							return nodoRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por NODO .... ");
		System.out.println("Saliendo del dao de NODO");
		return nodoResponse;
		
	}

	@Override
	public Boolean buscarNodo(int id) {
		
		logger.debug("------- BUSCANDO EXISTENCIA DE NODO ------");
		
		String sql = "SELECT COUNT(*) as cant FROM nodo nod WHERE nod.nod_id = ? AND nod.nod_estado IN (1, 7)";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
				
		if (count > 0) {
			return true;
		}else{
			return false;	
		}
		
	}

	@Override
	public List<Nodo> listarNodos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean buscarNodo(Nodo nodo) {
		
		logger.debug("------- Buscando nodo ");
		
		String sql = "SELECT COUNT(*) from nodo nod "
				+ "WHERE nod_estado IN (1, 7) AND nod.nod_codigo = ? OR (nod.nod_nombre = ? AND nod.nod_espacio = ?);";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{nodo.getCodigo(), nodo.getNombre(), 
				nodo.getEspacio().getId()}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}		
		
	}
	
	
	
	
	
}
