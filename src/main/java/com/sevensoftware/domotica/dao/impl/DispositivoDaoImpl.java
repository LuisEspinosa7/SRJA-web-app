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

import com.sevensoftware.domotica.dao.CategoriaDao;
import com.sevensoftware.domotica.dao.DispositivoDao;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.DispositivoDao;
import com.sevensoftware.domotica.dao.TipoDispositivoDao;
import com.sevensoftware.domotica.entities.Categoria;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.TipoDispositivo;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.entities.Usuario;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("dispositivoDao")
public class DispositivoDaoImpl implements DispositivoDao {
	

	
	private Logger logger = Logger.getLogger(DispositivoDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	TipoDispositivoDao tipoDispositivoDao;
	
	@Autowired
	CategoriaDao categoriaDao;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	

	@Override
	public JSONRespuesta listarTablaDispositivo(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug("Dentro de listar dispositivos en datatables");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "disp.disp_id", "disp.disp_nombre", "est.est_nombre", "td.td_nombre", "cat.cat_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM dispositivo disp "
				+ "INNER JOIN tipo_dispositivo td ON disp.disp_tipo_dispositivo = td.td_id "
				+ "INNER JOIN categoria cat ON cat.cat_id = disp.disp_categoria  "
				+ "INNER JOIN estado est ON disp.disp_estado = est.est_id "				
				+ "WHERE disp.disp_estado IN (1, 7) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		logger.debug("Cantidad de dispositivo =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND ( disp.disp_nombre LIKE ? OR td.td_nombre LIKE ? ";
			sql = sql + " OR est.est_nombre LIKE ? OR cat.cat_nombre LIKE ? )";
					

		filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%", "&" + search + "%", "%" + search + "%"}, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT disp_id, disp_nombre, est_nombre, td_nombre, cat_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "disp.disp_id, disp.disp_nombre, est.est_nombre, td.td_nombre, cat.cat_nombre FROM dispositivo disp "
				+ "INNER JOIN tipo_dispositivo td ON disp.disp_tipo_dispositivo = td.td_id "
				+ "INNER JOIN categoria cat ON cat.cat_id = disp.disp_categoria  "
				+ "INNER JOIN estado est ON disp.disp_estado = est.est_id "
				+ "WHERE disp.disp_estado IN (1, 7) "
				+ "AND (disp.disp_nombre LIKE ? OR td.td_nombre LIKE ? OR est.est_nombre LIKE ? OR cat.cat_nombre LIKE ? ))";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<Dispositivo> listaDispositivo = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Dispositivo>() {					

					public Dispositivo mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("Iterando ");
						Dispositivo dispositivo = obtenerDispositivo(rs.getInt("disp_id"));
						return dispositivo;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaDispositivo);

		return respuesta;	
		
		
	}

	@Override
	public boolean agregarDispositivo(Dispositivo dispositivo) {
		
		logger.debug("--- AGREGANDO NUEVA DISPOSITIVO ------");
		
		try {
							
			String SQL1 = "INSERT INTO dispositivo (disp_nombre, disp_estado, disp_tipo_dispositivo, disp_categoria) VALUES (?, ?, ?, ?);";
				
			int resultado1 = jdbcTemplateObject.update(SQL1, dispositivo.getNombre().toUpperCase(), dispositivo.getEstado().getId(), 
					dispositivo.getTipoDispositivo().getId(), dispositivo.getCategoria().getId());
				
				if (resultado1 > 0) {
					logger.debug("Se inserto el dispositivo");
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
	public boolean modificarDispositivo(int id, Dispositivo dispositivo) {

		logger.debug("--- DENTRO DE MODIFICAR dispositivo --------");	
		
		String SQL1 = "UPDATE dispositivo SET disp_nombre = ?, disp_estado = ?, disp_tipo_dispositivo = ?, disp_categoria = ? WHERE disp_id = ?;";
			
		int resultado1 = jdbcTemplateObject.update(SQL1, dispositivo.getNombre().toUpperCase(), dispositivo.getEstado().getId(), dispositivo.getTipoDispositivo().getId(), 
					dispositivo.getCategoria().getId(),id);
			
			if (resultado1 > 0) {
				logger.debug("--- dispositivo MODIFICADa -----");
				return true;				
			}else{
				logger.debug("No se pudo MODIFICAR el dispositivo");
				throw new DAOException("No se pudo MODIFICAR el dispositivo");
			}		
		
	}



	@Override
	public Dispositivo obtenerDispositivo(int id) {
		
		logger.debug("Buscando por Id - Dispositivo .... ");
		logger.debug("Dentro de dao de Dispositivo");
		

		String SQL = "SELECT d.disp_id, d.disp_nombre, d.disp_tipo_dispositivo, d.disp_estado, d.disp_categoria FROM dispositivo d WHERE d.disp_id = ?;";

		Dispositivo dispositivoResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Dispositivo>() {

					@Override
					public Dispositivo extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("disp_estado"));
							Categoria categoria = categoriaDao.obtenerCategoria(rs.getInt("disp_categoria"));
							TipoDispositivo tipoDispositivo = tipoDispositivoDao.obtenerTipoDispositivo(rs.getInt("disp_tipo_dispositivo"));
							
							Dispositivo dispositivoRow = new Dispositivo();
							dispositivoRow.setId(rs.getInt("disp_id"));
							dispositivoRow.setNombre(rs.getString("disp_nombre"));
							dispositivoRow.setTipoDispositivo(tipoDispositivo);
							dispositivoRow.setCategoria(categoria);
							dispositivoRow.setEstado(estado);
														
							logger.debug("DISPOSITIVO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + dispositivoRow.getId()
									
									+ dispositivoRow.getNombre());

							return dispositivoRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por dispositivo .... ");
		System.out.println("Saliendo del dao de dispositivo");
		return dispositivoResponse;
		
	}


	@Override
	public boolean eliminarDispositivo(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR dispositivo --------");
		
		int idEliminado = 5;
										
		String SQL1 = "UPDATE dispositivo SET disp_estado = ? WHERE disp_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, idEliminado, id);
			
		if (resultado1 > 0) {
			logger.debug("--- dispositivo CAMBIADO DE ESTADO A DESCONTINUO ------");
			return true;
		}else{
			return false;
		}
		
	}


	@Override
	public List<Dispositivo> listarDispositivo() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Boolean buscarDispositivo(int id) {
		logger.debug("------- BUSCANDO EXISTENCIA DE DISPOSITIVO ------");
		
		String sql = "SELECT COUNT(*) as cant FROM dispositivo disp WHERE disp.disp_id = ? AND disp.disp_estado IN (1, 7)";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
				
		if (count > 0) {
			return true;
		}else{
			return false;	
		}
	}


	@Override
	public Boolean buscarDispositivo(Dispositivo dispositivo) {
		
		logger.debug("------- Buscando dispositivo ");
		
		String sql = "SELECT COUNT(*) from dispositivo disp "
				+ "WHERE disp.disp_estado IN (1, 7) AND disp.disp_nombre = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{dispositivo.getNombre()}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
		
	}
	
	
	
	
	
	
}
