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

import com.sevensoftware.domotica.dao.DispositivoItemValorDao;
import com.sevensoftware.domotica.dao.DispositivoDao;
import com.sevensoftware.domotica.dao.DispositivoItemDao;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.NodoDao;
import com.sevensoftware.domotica.entities.DispositivoItemValor;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.LiveData;
import com.sevensoftware.domotica.entities.Nodo;
import com.sevensoftware.domotica.exception.DAOException;


/**
 * @author LUIS
 *
 */
@Repository("dispositivoItemDao")
public class DispositivoItemDaoImpl implements DispositivoItemDao { 
	
	private Logger logger = Logger.getLogger(DispositivoItemDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceControlador")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	NodoDao nodoDao;
	
	@Autowired
	DispositivoDao dispositivoDao;
	
	@Autowired
	DispositivoItemValorDao dispositivoItemValorDao;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	
	
	@Override
	public boolean agregarDispositivoItem(DispositivoItem dispositivoItem) {
		
		logger.debug("--- AGREGANDO NUEVA DISPOSITIVO ------");
		
		try {
							
			String SQL1 = "INSERT INTO dispositivo_item (di_codigo, di_estado, di_dispositivo, di_nodo) VALUES (?, ?, ?, ?);";
				
			int resultado1 = jdbcTemplateObject.update(SQL1, dispositivoItem.getCodigo().toUpperCase(), dispositivoItem.getEstado().getId(), 
					dispositivoItem.getDispositivo().getId(), dispositivoItem.getNodo().getId());
				
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
	public boolean modificarDispositivoItem(int id, DispositivoItem dispositivoItem) {
		
		logger.debug("--- DENTRO DE MODIFICAR DispositivoItem --------");
		
		String SQL1 = "UPDATE dispositivo_item SET di_codigo = ?, di_estado = ?, di_dispositivo = ?, di_nodo = ? WHERE di_id = ?;";
		
		int resultado1 = jdbcTemplateObject.update(SQL1, dispositivoItem.getCodigo(), dispositivoItem.getEstado().getId(), dispositivoItem.getDispositivo().getId(), 
				dispositivoItem.getNodo().getId(), id);
		
		if (resultado1 > 0) {
			logger.debug("--- AFILIACION MODIFICADa -----");
			return true;				
		}else{
			logger.debug("No se pudo MODIFICAR la AFILIACION");
			throw new DAOException("No se pudo MODIFICAR el AFILIACION");
		
		}
		
	}

	@Override
	public boolean eliminarDispositivoItem(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR dispositivo --------");
		
		int idEliminado = 5;
										
		String SQL1 = "UPDATE dispositivo_item SET di_estado = ? WHERE di_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, idEliminado, id);
			
		if (resultado1 > 0) {
			logger.debug("--- dispositivo CAMBIADO DE ESTADO A DESCONTINUO ------");
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public JSONRespuesta listarTablaDispositivoItem(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug("Dentro de listar dispositivos item en datatables");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "di.di_id", "di.di_codigo", "est.est_nombre", "disp.disp_nombre", "nod.nod_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM dispositivo_item di "
				+ "INNER JOIN dispositivo disp ON di.di_dispositivo = disp.disp_id "
				+ "INNER JOIN nodo nod ON nod.nod_id = di.di_nodo  "
				+ "INNER JOIN estado est ON disp.disp_estado = est.est_id "				
				+ "WHERE di.di_estado IN (1, 7) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		logger.debug("Cantidad de dispositivo =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND ( di.di_codigo LIKE ? OR disp.disp_nombre LIKE ? ";
			sql = sql + " OR est.est_nombre LIKE ? OR nod.nod_nombre LIKE ? )";
					

		filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%", "&" + search + "%", "%" + search + "%"}, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT di_id, di_codigo, est_nombre, disp_nombre, nod_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "di.di_id, di.di_codigo, est.est_nombre, disp.disp_nombre, nod.nod_nombre FROM dispositivo_item di "
				+ "INNER JOIN dispositivo disp ON di.di_dispositivo = disp.disp_id "
				+ "INNER JOIN nodo nod ON nod.nod_id = di.di_nodo  "
				+ "INNER JOIN estado est ON disp.disp_estado = est.est_id "	
				+ "WHERE di.di_estado IN (1, 7) "
				+ "AND (di.di_codigo LIKE ? OR disp.disp_nombre LIKE ? OR est.est_nombre LIKE ? OR nod.nod_nombre LIKE ? ))";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<DispositivoItem> listaDispositivosItem = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<DispositivoItem>() {					

					public DispositivoItem mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("Iterando ");
						DispositivoItem dispositivoItem = obtenerDispositivoItem(rs.getInt("di_id"));
						return dispositivoItem;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaDispositivosItem);

		return respuesta;	
		
	}

	@Override
	public Boolean buscarDispositivoItem(int id) {
		logger.debug("------- Buscando al DispositivoItem por id");
		
		String sql = "SELECT COUNT(*) as cant FROM dispositivo_item di WHERE di.di_id = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public List<DispositivoItem> listarDispositivoItem() {
		String sql = " SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di";
		List<DispositivoItem> listaDispositivoItem = jdbcTemplateObject.query(sql, new RowMapper<DispositivoItem>() {

			public DispositivoItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DispositivoItem dispositivoItemRow = obtenerDispositivoItem(rs.getInt("di_id"));
				return dispositivoItemRow;
								
			}
		});
		return listaDispositivoItem;
	}

	@Override
	public DispositivoItem obtenerDispositivoItem(int id) {
		
		logger.debug("Buscando por Id - DispositivoItem .... ");
		logger.debug("Dentro de dao de DispositivoItem");
		

		String SQL = "SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di WHERE di.di_id = ?;";

		DispositivoItem dispositivoItemResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<DispositivoItem>() {

					@Override
					public DispositivoItem extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("di_estado"));
							Nodo nodo = nodoDao.obtenerNodo(rs.getInt("di_nodo"));
							Dispositivo dispositivo = dispositivoDao.obtenerDispositivo(rs.getInt("di_dispositivo"));
							List<DispositivoItemValor> valores = dispositivoItemValorDao.listarDispositivoItemValores(rs.getInt("di_id"));
							
							
							DispositivoItem dispositivoItemRow = new DispositivoItem();
							dispositivoItemRow.setId(rs.getInt("di_id"));
							dispositivoItemRow.setCodigo(rs.getString("di_codigo"));
							dispositivoItemRow.setDispositivo(dispositivo);
							dispositivoItemRow.setNodo(nodo);
							dispositivoItemRow.setEstado(estado);
							dispositivoItemRow.setValores(valores);
																							
							
							logger.debug("USUARIO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + dispositivoItemRow.getId()
									+ " Codigo= " + dispositivoItemRow.getCodigo() + ", DISPOSITIVO= "
									+ dispositivoItemRow.getDispositivo().getNombre());

							return dispositivoItemRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por dispositivoItem .... ");
		System.out.println("Saliendo del dao de dispositivoItem");
		return dispositivoItemResponse;
		
	}




	@Override
	public List<DispositivoItem> listarDispositivosItems(String tipoDispositivo, int espacioId) {
		String sql = "SELECT di.di_id, di.di_codigo, di.di_dispositivo, di.di_nodo, di.di_estado FROM dispositivo_item di, dispositivo d, tipo_dispositivo td, nodo n, espacio e "
				+ "WHERE d.disp_id = di.di_dispositivo AND td.td_id = d.disp_tipo_dispositivo AND di.di_nodo = n.nod_id AND n.nod_espacio = e.esp_id "
				+ "AND td.td_nombre = ? AND e.esp_id = ?; ";
		List<DispositivoItem> listaDispositivoItem = jdbcTemplateObject.query(sql, new Object[] { tipoDispositivo, espacioId } ,new RowMapper<DispositivoItem>() {

			public DispositivoItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DispositivoItem dispositivoItemRow = obtenerDispositivoItem(rs.getInt("di_id"));
				return dispositivoItemRow;
								
			}
		});
		return listaDispositivoItem;
	}




	@Override
	public Boolean buscarDispositivoItem(int id, DispositivoItem dispositivoItem) {
		
		logger.debug("------- Buscando DispositivoItem por id y  datos");
		
		String sql = "SELECT COUNT(*) as cant FROM dispositivo_item di "
				+ "WHERE di.di_id = ? AND di.di_codigo = ? AND di.di_dispositivo = ? AND di.di_nodo = ?  AND di.di_estado IN (1, 2);";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id, dispositivoItem.getCodigo(), dispositivoItem.getDispositivo().getId(), dispositivoItem.getNodo().getId()}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
		
	}




	@Override
	public boolean modificarDispositivoItemValores(int id, DispositivoItem dispositivoItem) {
		
		logger.debug("--- DENTRO DE MODIFICAR DispositivoItem Actuador VALORES--------");
		
		List<DispositivoItemValor> valores = dispositivoItem.getValores();
		
		
		for(DispositivoItemValor dispositivoItemValor : valores) {
			System.out.println("Modificando un actuador valor");
			
			System.out.println("Id: " + dispositivoItemValor.getId());
			System.out.println("Valor: " + dispositivoItemValor.getValor());			
			
			dispositivoItemValorDao.modificarDispositivoItemValor(id, dispositivoItemValor);	
		}
		
		return true;
				
	}




	@Override
	public LiveData getLiveDataDispositivoItem(int dispositivoItemId, int tipoValorId) {
		
		logger.debug("OBTENIENDO ULTIMO VALOR DEL SENSOR .... ");
		logger.debug("Dentro de dao de DispositivoItem");
		

		String SQL = "SELECT med_valor FROM (SELECT med_id, med_dispositivo_item, med_tipo_valor, med_valor, med_fecha_hora, MAX(med_fecha_hora) OVER (PARTITION BY med_dispositivo_item, med_tipo_valor) as MaxDataDate "
				+ " FROM medicion) x WHERE med_fecha_hora = MaxDataDate AND med_dispositivo_item = ? AND med_tipo_valor = ?;";

		LiveData liveDataResponse = jdbcTemplateObject.query(SQL, new Object[] { dispositivoItemId, tipoValorId },
				new ResultSetExtractor<LiveData>() {

					@Override
					public LiveData extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							LiveData liveDataRow = new LiveData();
							liveDataRow.setData(rs.getDouble("med_valor"));
																											
							
							logger.debug("ULTIMO VALOR SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + dispositivoItemId
									+  " VALOR = " + liveDataRow.getData());

							return liveDataRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar ULTIMO VALOR DEL SENSOR .... ");
		System.out.println("Saliendo del dao de dispositivoItem");
		return liveDataResponse;		
	}




	@Override
	public Boolean buscarDispositivoItem(DispositivoItem dispositivoItem) {
		
		logger.debug("------- Buscando DispositivoItem por codigo");
		
		String sql = "SELECT COUNT(*) as cant FROM dispositivo_item di "
				+ "WHERE di.di_codigo = ? AND di.di_estado IN (1, 5, 7);";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{dispositivoItem.getCodigo()}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
		
	}
	

}
