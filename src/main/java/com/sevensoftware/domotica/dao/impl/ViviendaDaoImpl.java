/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.CiudadDao;
import com.sevensoftware.domotica.dao.CoordenadaDao;
import com.sevensoftware.domotica.dao.DepartamentoDao;
import com.sevensoftware.domotica.dao.EspacioDao;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.dao.TipoNodoDao;
import com.sevensoftware.domotica.dao.ViviendaDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Coordenada;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Espacio;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.TipoEspacio;
import com.sevensoftware.domotica.entities.Vivienda;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("viviendaDao")
public class ViviendaDaoImpl implements ViviendaDao {
	
	private static final int ESTADO_ELIMINADO = 3;
	
	private Logger logger = Logger.getLogger(ViviendaDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	@Autowired
	CoordenadaDao coordenadaDao;
		
	@Autowired
	CiudadDao ciudadDao;
	
	@Autowired
	DepartamentoDao departamentoDao;
	
	@Autowired
	PaisDao paisDao;
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	

	@Override
	public boolean agregarVivienda(Vivienda vivienda) {
		
		logger.debug("--- AGREGANDO NUEVA VIVIENDA ------");
		
		try {
			final String SQL1 = "INSERT INTO coordenada (coo_latitud, coo_longitud, coo_altitud) VALUES (?, ?, ?);";
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplateObject.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, vivienda.getCoordenada().getLatitud());
					pstm.setString(2, vivienda.getCoordenada().getLongitud());
					pstm.setString(3, vivienda.getCoordenada().getAltitud());					
					return pstm;
				}
			}, keyHolder);
			
			if (resultado > 0) {
				
				logger.debug("--- COORDENADA AGREGADA ----");
				int key = 0;
				
				if (keyHolder.getKeys().size() == 1) {
					key = keyHolder.getKey().intValue();					
				} else if (keyHolder.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt( String.valueOf(keyHolder.getKeys().get("coo_id")));					
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					return false;
				}
				
				final String id = Integer.toString(key);				
				
				String SQL2 = "INSERT INTO vivienda (viv_codigo, viv_direccion, viv_coordenada, viv_barrio, viv_ciudad, viv_estado) VALUES (?, ?, ?, ?, ?, ?);";
				
				int resultado2 = jdbcTemplateObject.update(SQL2, vivienda.getCodigo().toUpperCase(), vivienda.getDireccion().toUpperCase(), Integer.parseInt(id), 
						vivienda.getBarrio(), vivienda.getCiudad().getId(), vivienda.getEstado().getId());
				
				if (resultado2 > 0) {
					logger.debug("Se inserto LA VIVIENDA");
					return true;			
				}else{
					return false;					
				}				
				
			}else{
				return false;
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
		
	}

	@Override
	public boolean modificarVivienda(int id, Vivienda vivienda) {
		logger.debug("--- DENTRO DE MODIFICAR VIVIENDA --------");	
		
		// Modificar la coordenada
		Boolean coordenadaModificada = coordenadaDao.modificarCoordenada(vivienda.getCoordenada());		
		
		if (coordenadaModificada) {
			
			String SQL1 = "UPDATE vivienda v SET viv_codigo = ?, viv_direccion = ?, viv_coordenada = ?, viv_barrio = ?, viv_ciudad = ?, viv_estado = ? WHERE v.viv_id = ?;";
			
			int resultado1 = jdbcTemplateObject.update(SQL1, vivienda.getCodigo(), vivienda.getDireccion(), vivienda.getCoordenada().getId(), 
					vivienda.getBarrio(), vivienda.getCiudad().getId(), vivienda.getEstado().getId(), id);
			
			if (resultado1 > 0) {
				logger.debug("--- VIVIENDA MODIFICADA -----");
				return true;				
			}else{
				logger.debug("No se pudo MODIFICAR el VIVIENDA");
				throw new DAOException("No se pudo MODIFICAR el VIVIENDA");
			}
			
		}else{
			return false;
		}
	}

	@Override
	public boolean eliminarVivienda(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR VIVIENDA --------");
		
												
		String SQL1 = "UPDATE vivienda SET viv_estado = ? WHERE viv_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, ESTADO_ELIMINADO, id);
			
		if (resultado1 > 0) {
			logger.debug("--- VIVIENDA CAMBIADO DE ESTADO A ELIMINADO ------");
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public JSONRespuesta listarTablaVivienda(String search, int start, int length, int draw, int posicion,
			String direccion) {
		
		logger.debug("Dentro de listar VIVIENDAS en datatables");

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "viv.viv_id", "viv.viv_codigo", "viv.viv_direccion", "viv.viv_barrio", "est.est_nombre" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM vivienda viv "
				+ "INNER JOIN cuidad c ON viv.viv_ciudad = c.ciu_id "
				+ "INNER JOIN coordenada coo ON coo.coo_id = viv.viv_coordenada "
				+ "INNER JOIN estado est ON viv.viv_estado = est.est_id "				
				+ "WHERE viv.viv_estado IN (1, 2, 7) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		logger.debug("Cantidad de VIVIENDAS =" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (viv.viv_codigo LIKE ? OR viv.viv_direccion LIKE ? ";
			sql = sql + " OR viv.viv_barrio LIKE ? OR est.est_nombre LIKE ? )";
					

		filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT viv_id, viv_codigo, viv_direccion, viv_barrio, est_nombre "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "viv.viv_id, viv.viv_codigo, viv.viv_direccion, viv.viv_barrio, est.est_nombre FROM vivienda viv "
				+ "INNER JOIN cuidad c ON viv.viv_ciudad = c.ciu_id "
				+ "INNER JOIN coordenada coo ON coo.coo_id = viv.viv_coordenada "
				+ "INNER JOIN estado est ON viv.viv_estado = est.est_id "
				+ "WHERE viv.viv_estado IN (1, 2, 7) "
				+ "AND (viv.viv_codigo LIKE ? OR viv.viv_direccion LIKE ? OR viv.viv_barrio LIKE ? OR est.est_nombre LIKE ? ))";	
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<Vivienda> listaVivienda = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Vivienda>() {					

					public Vivienda mapRow(ResultSet rs, int rowNum) throws SQLException {
						logger.debug("Iterando ");
						Vivienda vivienda = obtenerVivienda(rs.getInt("viv_id"));
						return vivienda;					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaVivienda);

		return respuesta;	
		
	}

	

	@Override
	public List<Vivienda> listarViviendas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vivienda obtenerVivienda(int id) {
		
		logger.debug("Buscando por Id - Vivienda .... ");
		logger.debug("Dentro de dao de Vivienda");
		

		String SQL = "SELECT v.viv_id, v.viv_codigo, v.viv_direccion, v.viv_coordenada, v.viv_barrio, v.viv_ciudad, v.viv_estado, d.dep_id, p.pai_id "
				+ "FROM vivienda v, cuidad c, departamento d, pais p WHERE v.viv_ciudad = c.ciu_id AND d.dep_id = c.ciu_departamento AND p.pai_id = d.dep_pais AND v.viv_id = ?;";

		Vivienda viviendaResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Vivienda>() {

					@Override
					public Vivienda extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("viv_estado"));
							Coordenada coordenada = coordenadaDao.obtenerCoordenada(rs.getInt("viv_coordenada"));
							Ciudad ciudad = ciudadDao.obtenerCiudadPorId(rs.getInt("viv_ciudad"));
							Departamento departamento = departamentoDao.obtenerDepartamento(rs.getInt("dep_id"));
							Pais pais = paisDao.obtenerPais(rs.getInt("pai_id"));
							
																				
							Vivienda viviendaRow = new Vivienda();
							viviendaRow.setId(rs.getInt("viv_id"));
							viviendaRow.setCodigo(rs.getString("viv_codigo"));
							viviendaRow.setDireccion(rs.getString("viv_direccion"));
							
							viviendaRow.setBarrio(rs.getString("viv_barrio"));
													
							viviendaRow.setEstado(estado);
							viviendaRow.setCoordenada(coordenada);							
							viviendaRow.setCiudad(ciudad);
							viviendaRow.setDepartamento(departamento);
							viviendaRow.setPais(pais);							
							
							logger.debug("VIVIENDA SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + viviendaRow.getId()
									+ ", DIRECCION= " + viviendaRow.getDireccion());

							return viviendaRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por vivienda .... ");
		System.out.println("Saliendo del dao de vivienda");
		return viviendaResponse;		
	}

	
	@Override
	public Boolean buscarVivienda(int id) {
		logger.debug("------- Buscando al vivienda por id");
		
		String sql = "SELECT COUNT(*) as cant FROM vivienda v WHERE v.viv_id = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
	}	
	
	
	@Override
	public Boolean buscarPorCodigo(String codigo) {
		logger.debug("------- Buscando al vivienda por codigo");
		
		String sql = "SELECT COUNT(*) as cant FROM vivienda v WHERE v.viv_codigo = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{codigo}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
	}


	@Override
	public Boolean buscarPorDireccion(String direccion) {
		logger.debug("------- Buscando al vivienda por direccion");
		
		String sql = "SELECT COUNT(*) as cant FROM vivienda v WHERE v.viv_direccion = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{direccion}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
	}
	
}
