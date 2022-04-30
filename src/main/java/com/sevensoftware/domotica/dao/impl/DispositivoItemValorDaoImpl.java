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
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.TipoValorDao;
import com.sevensoftware.domotica.entities.DispositivoItemValor;
import com.sevensoftware.domotica.entities.TipoValor;

/**
 * @author LUIS
 *
 */
@Repository("dispositivoItemValorDao")
public class DispositivoItemValorDaoImpl implements DispositivoItemValorDao{
	
	private Logger logger = Logger.getLogger(DispositivoItemValorDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
	
	@Autowired
	EstadoDao estadoDao;
	
	
	@Autowired
	TipoValorDao tipoValorDao;
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	

	@Override
	public List<DispositivoItemValor> listarDispositivoItemValores(int dispositivoItemId) {
		
		String sql = "SELECT div.div_id, div.div_tipo_valor, div.div_valor "
				+ "FROM dispositivo_item di, dispositivo_item_valor div, tipo_valor tv, unidad u "
				+ "WHERE di.di_id = div.div_dispositivo_item AND tv.tiv_id = div.div_tipo_valor AND u.uni_id = tv.tiv_unidad AND  di.di_id = ?;";
		
		List<DispositivoItemValor> listaDispositivoItemValor = jdbcTemplateObject.query(sql, new Object[] { dispositivoItemId } ,new RowMapper<DispositivoItemValor>() {

			public DispositivoItemValor mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				DispositivoItemValor actuadorValorRow = obtenerDispositivoItemValor(rs.getInt("div_id"));
				return actuadorValorRow;
								
			}
		});
		return listaDispositivoItemValor;	
	}

	@Override
	public DispositivoItemValor obtenerDispositivoItemValor(int id) {
		
		logger.debug("Buscando por Id - DispositivoItemValor .... ");
		logger.debug("Dentro de dao de DispositivoItemValor");
		

		String SQL = "SELECT div.div_id, div.div_tipo_valor, div.div_valor FROM dispositivo_item_valor div WHERE div.div_id = ?;";

		DispositivoItemValor actuadorValorResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<DispositivoItemValor>() {

					@Override
					public DispositivoItemValor extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
														
							TipoValor tipoValor = tipoValorDao.obtenerTipoValor(rs.getInt("div_tipo_valor"));
														
							DispositivoItemValor actuadorValorRow = new DispositivoItemValor();
							actuadorValorRow.setId(rs.getInt("div_id"));
							actuadorValorRow.setValor(rs.getDouble("div_valor"));
							actuadorValorRow.setTipoValor(tipoValor);
							
																							
							
							logger.debug("ACTUADOR VALOR SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + actuadorValorRow.getId()
									+ " TIPO VALOR = "
									+ actuadorValorRow.getTipoValor().getNombre());

							return actuadorValorRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por DispositivoItemValor .... ");
		System.out.println("Saliendo del dao de DispositivoItemValor");
		return actuadorValorResponse;
		
		
	}



	
	
	@Override
	public boolean modificarDispositivoItemValor(int id, DispositivoItemValor actuadorValor) {
		
		
		String SQL1 = "UPDATE dispositivo_item_valor div SET div_valor = ? "
				+ "WHERE div.div_tipo_valor = ? AND div.div_dispositivo_item = ? AND div.div_id = ?;";
		
		int resultado1 = jdbcTemplateObject.update(SQL1, actuadorValor.getValor(), actuadorValor.getTipoValor().getId(), 
				id, actuadorValor.getId());
		
		if (resultado1 > 0) {
			logger.debug("--- DispositivoItemValor MODIFICADo -----");
			return true;				
		}else{
			logger.debug("No se pudo MODIFICAR el DispositivoItemValor");
			//throw new DAOException("No se pudo MODIFICAR el AFILIACION");
			return false;
		}
		
	}

}
