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
import com.sevensoftware.domotica.dao.NodoDao;
import com.sevensoftware.domotica.entities.Categoria;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Categoria;

/**
 * @author LUIS
 *
 */
@Repository("categoriaDao")
public class CategoriaDaoImpl implements CategoriaDao{
	
	private Logger logger = Logger.getLogger(CategoriaDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;
		
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}

	@Override
	public Boolean agregarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean modificarCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria obtenerCategoria(int id) {

		logger.debug("Buscando por Id - Categoria .... ");
		logger.debug("Dentro de dao de Categoria");
		

		String SQL = "SELECT c.cat_id, c.cat_nombre, c.cat_descripcion FROM categoria c WHERE c.cat_id = ?;";

		Categoria categoriaResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Categoria>() {

					@Override
					public Categoria extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
																	
							Categoria categoriaRow = new Categoria();
							categoriaRow.setId(rs.getInt("cat_id"));
							categoriaRow.setNombre(rs.getString("cat_nombre"));
							categoriaRow.setDescripcion(rs.getString("cat_descripcion"));
																			
							logger.debug("CATEGORIA SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + categoriaRow.getId()
									
									+ categoriaRow.getNombre());

							return categoriaRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por categoria .... ");
		System.out.println("Saliendo del dao de categoria");
		return categoriaResponse;
		
	}

	@Override
	public List<Categoria> obtenerCategorias() {
		
		String sql = "SELECT cat.cat_id, cat.cat_nombre, cat.cat_descripcion FROM categoria cat;";
		List<Categoria> listaCategorias = jdbcTemplateObject.query(sql, new RowMapper<Categoria>() {

			public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
				Categoria tipoDispositivo = new Categoria();
				tipoDispositivo.setId(rs.getInt("cat_id"));
				tipoDispositivo.setNombre(rs.getString("cat_nombre"));	
				tipoDispositivo.setDescripcion(rs.getString("cat_descripcion"));										
				return tipoDispositivo;
			}
		});
		return listaCategorias;	
		
	}
	
	
	

}
