/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.EventoDao;
import com.sevensoftware.domotica.dao.TipoEventoDao;
import com.sevensoftware.domotica.entities.Coordenada;
import com.sevensoftware.domotica.entities.Evento;
import com.sevensoftware.domotica.entities.TipoEvento;
import com.sevensoftware.domotica.exception.DAOException;

/**
 * @author LUIS
 *
 */
@Repository("eventoDao")
public class EventoDaoImpl implements EventoDao{
	
	private Logger logger = Logger.getLogger(ViviendaDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	
	@Autowired
	TipoEventoDao tipoEventoDao;
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	

	@Override
	public int agregarEvento(Evento evento) {
		
		logger.debug("--- AGREGANDO NUEVA EVENTO ------");
		
		final String SQL1 = "INSERT INTO evento (eve_fecha_hora, eve_tipo_evento, eve_mensaje, eve_confirmado) VALUES (?, ?, ?, ?);";
		
		
		LocalDateTime localDate = LocalDateTime.now();	
		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int resultado = jdbcTemplateObject.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
				pstm.setObject(1, localDate);
				pstm.setInt(2, evento.getTipoEvento().getId());
				pstm.setString(3, evento.getMensaje());
				pstm.setInt(4, evento.getConfirmado());
								
				return pstm;
			}
		}, keyHolder);	
		
		int key = 0;
		
		
		if (resultado > 0) {
			logger.debug("Se inserto en la tabla EVENTO");			
			
			if (keyHolder.getKeys().size() == 1) {
				key = keyHolder.getKey().intValue();					
			} else if (keyHolder.getKeys().size() > 1) {
				logger.debug("------- Se encuentra mas de una llave ----");
				key = Integer.parseInt( String.valueOf(keyHolder.getKeys().get("eve_id")));					
			} else {
				logger.debug("------- No se pudo extraer la llave ----");				
			}
			
			return key;			
		}else{
			logger.debug("No se pudo insertar en la EVENTO");
			
			//throw new DAOException("No se pudo insertar el EVENTO");
			//return false;
		}
		System.out.println("EN AGREGAR EVENTO: LLAVE GENERADA: " + key);
		return key;
		
	}




	@Override
	public Boolean buscarEvento(Evento evento, int confirmado) {
		
		logger.debug("------- Buscando EXISTENCIA EVENTO ");
		
		String sql = "SELECT COUNT(*) as cant FROM evento evt "
				+ "WHERE evt.eve_tipo_evento = ? AND evt.eve_mensaje = ? AND evt.eve_confirmado = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{evento.getTipoEvento().getId(), evento.getMensaje(), confirmado}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public Boolean buscarAlertas(Evento evento, int confirmado) {
		
		logger.debug("------- Buscando EXISTENCIA ALERTAS ");
		
		String sql = "SELECT COUNT(*) as cant FROM evento evt "
				+ "WHERE evt.eve_tipo_evento = ? AND evt.eve_confirmado = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{evento.getTipoEvento().getId(), confirmado}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
		
	}




	@Override
	public Boolean modificarEvento(int id, Evento evento) {
		
		logger.debug("--- DENTRO DE MODIFICAR EVENTO --------");	
		
		String SQL1 = "UPDATE evento e SET eve_fecha_hora = ?, eve_tipo_evento = ?, eve_mensaje = ?, eve_confirmado = ? WHERE e.eve_id = ?;";

		int resultado1 = jdbcTemplateObject.update(SQL1, evento.getFechaHora(), evento.getTipoEvento().getId(), evento.getMensaje(), evento.getConfirmado(), id);

		if (resultado1 > 0) {
			logger.debug("--- EVENTO MODIFICADO -----");
			return true;
		} else {
			logger.debug("No se pudo MODIFICAR el EVENTO");
			return false;
			//throw new DAOException("No se pudo MODIFICAR el EVENTO");
		}
		
	}




	@Override
	public Evento obtenerEvento(int id) {
		logger.debug("------- Buscando EVENTO POR ID :)");
		
		String sql = "SELECT eve_id, eve_fecha_hora, eve_tipo_evento, eve_mensaje, eve_confirmado FROM evento evt "
				+ "WHERE evt.eve_id = ?;";

		Evento evento = jdbcTemplateObject.query(sql, new Object[] { id }, new ResultSetExtractor<Evento>(){

			@Override
			public Evento extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					
					System.out.println("SI ENCONTRO OBJETO, MENSAJE = " + rs.getString("eve_mensaje"));
					
					Evento eventoRow = new Evento();
					eventoRow.setId(rs.getInt("eve_id"));
					LocalDateTime localDateTime = rs.getObject("eve_fecha_hora", LocalDateTime.class);
					System.out.println("localDateTime= " + localDateTime);
					eventoRow.setFechaHora(localDateTime);	
					TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(rs.getInt("eve_tipo_evento"));
					eventoRow.setTipoEvento(tipoEvento);
					eventoRow.setMensaje(rs.getString("eve_mensaje"));
					eventoRow.setConfirmado(rs.getInt("eve_confirmado"));
					
					return eventoRow;
				}	
				System.out.println("NO DEVOLVIO NINGUNO");
				return null;
			}		
		});
		return evento;
	}




	
	
	

}
