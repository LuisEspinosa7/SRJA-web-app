/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.impl.PerfilDaoImpl;
import com.sevensoftware.domotica.dao.PerfilDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.Perfil;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.entities.Usuario;

/**
 * @author LUIS
 *
 */
@Repository("perfilDao")
public class PerfilDaoImpl implements PerfilDao{
	
	private Logger logger = Logger.getLogger(PerfilDaoImpl.class);
	
	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}	
	

	@Override
	public Set<Perfil> buscarPerfilesPorUsername(String nombre) {
		
		logger.debug("perfiles del usuario ");

		String SQL = "SELECT p.perf_id, p.perf_nombre FROM perfil p "
				+ "INNER JOIN perfil_usuario pu ON pu.perfu_perfil = p.perf_id "
				+ "INNER JOIN usuario u ON u.usu_id = pu.perfu_usuario "
				+ "WHERE u.usu_login = ?;";

		List<Perfil> perfilesUsuario = jdbcTemplateObject.query(SQL, new Object[] { nombre }, new RowMapper<Perfil>(){

			@Override
			public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
				Perfil perfilRow = new Perfil();
				perfilRow.setId(rs.getInt("perf_id"));
				perfilRow.setPerfil(rs.getString("perf_nombre"));
				return perfilRow;
			}			
		});
				
		Set<Perfil> perfiles = new HashSet<Perfil>(perfilesUsuario);		
		return perfiles;		
	}


	@Override
	public List<Perfil> buscarPerfiles() {
		String sql = "SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p;";
		List<Perfil> listaPerfiles = jdbcTemplateObject.query(sql, new RowMapper<Perfil>() {

			public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
				Perfil perfilRow = new Perfil();
				perfilRow.setId(rs.getInt("perf_id"));
				perfilRow.setPerfil(rs.getString("perf_nombre"));			
				return perfilRow;
			}
		});
		return listaPerfiles;
	}


	@Override
	public Perfil buscarPerfil(String nombre) {
		
		String SQL = "SELECT p.perf_id, p.perf_nombre, p.perf_descripcion FROM perfil p "
				+ "INNER JOIN perfil_usuario pu ON pu.perfu_perfil = p.perf_id "
				+ "INNER JOIN usuario u ON u.usu_id = pu.perfu_usuario "
				+ "WHERE u.usu_login = ?;";
		
		Perfil perfilResponse = jdbcTemplateObject.query(SQL, new Object[] { nombre },
				new ResultSetExtractor<Perfil>() {

					@Override
					public Perfil extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {												
							
							Perfil perfilRow = new Perfil();
							perfilRow.setId(rs.getInt("perf_id"));
							perfilRow.setPerfil(rs.getString("perf_nombre"));							
						
							
							logger.debug("PERFIL SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + perfilRow.getId()
									+ " PERFIL= " + perfilRow.getPerfil());

							return perfilRow;
						}
						return null;
					}
				});
		
		logger.debug("Termino de Buscar por PERFIL .... ");
		System.out.println("Saliendo del dao de PERFIL");
		return perfilResponse;
		
	}
	
	
}
