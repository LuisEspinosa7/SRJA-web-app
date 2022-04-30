/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.MedicionDao;
import com.sevensoftware.domotica.dao.TipoEspacioDao;
import com.sevensoftware.domotica.entities.TMedicion;
import com.sevensoftware.domotica.exception.DAOException;


/**
 * @author LUIS
 *
 */
@Repository("medicionDao")
public class MedicionDaoImpl implements MedicionDao{
	
	private Logger logger = Logger.getLogger(MedicionDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	//@Autowired
	//TipoMedicionDao tipoMedicionDao;

	//@Autowired
	//TipoMedicionDao tipoMedicionDao;
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	
	@Override
	public Boolean agregarMedicion(int dispositivoItem, int tipoMedicion, int tipoValor, double valor) {
		
		logger.debug("--- AGREGANDO NUEVA MEDICION ------");
		
		String SQL1 = "INSERT INTO medicion (med_dispositivo_item, med_tipo_medicion, med_fecha_hora, med_tipo_valor, med_valor) VALUES (?, ?, ?, ?, ?);";
		
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		
		int resultado1 = jdbcTemplateObject.update(SQL1, dispositivoItem, tipoMedicion, currentTimestamp, tipoValor, valor);
		
		if (resultado1 > 0) {
			logger.debug("Se inserto en la tabla MEDICION");
			return true;			
		}else{
			logger.debug("No se pudo insertar en la MEDICION");
			throw new DAOException("No se pudo insertar el MEDICION");
			//return false;
		}	
		
	}


	@Override
	public void agregarMedicionNodo(final List<TMedicion> mediciones) {
		
		System.out.println("AGREGANDO MEDICION...................");
		
		/**
		String SQL1 = "INSERT INTO medicion (med_dispositivo_item, med_tipo_medicion, med_fecha_hora, med_tipo_valor, med_valor) VALUES (?, ?, ?, ?, ?);";

		jdbcTemplateObject.batchUpdate(SQL1, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TMedicion medicion = mediciones.get(i);
				ps.setInt(1, medicion.getDispositivoItemId());
				ps.setInt(2, medicion.getTipoMedicion());
				ps.setDate(3, new Date(System.currentTimeMillis()));
				ps.setInt(4, medicion.getTipoValor());
				ps.setDouble(5, medicion.getValor());								
			}

			@Override
			public int getBatchSize() {
				return mediciones.size();
			}
		});
		**/

	}
		

}
