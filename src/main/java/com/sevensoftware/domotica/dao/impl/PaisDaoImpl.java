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

import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.Pais;
//import com.sevensoftware.domotica.web.controllers.PaisRestController;

/**
 * @author LUIS
 *
 */
@Repository("paisDao")
public class PaisDaoImpl implements PaisDao {

	private Logger logger = Logger.getLogger(PaisDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}

	@Override
	public boolean agregarPais(Pais pais) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarPais(int id, Pais pais) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarPais(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Pais> listarPais() {
		
		String sql = "select pai_id, pai_nombre from pais order by pai_nombre;";

		List<Pais> listaPais = jdbcTemplateObject.query(sql, new RowMapper<Pais>() {

			public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
				Pais pais = new Pais();
				pais.setid(rs.getInt("pai_id"));
				pais.setNombre(rs.getString("pai_nombre"));
				return pais;
			}
		});

		return listaPais;
	}

	@Override
	public Pais buscarPais(int id) {
		logger.debug("pais del departamento ");

		String SQL = "select pai_id, pai_nombre from pais where pai_id = ?;";

		Pais paisObjeto = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Pais>(){

			@Override
			public Pais extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Pais paisRow = new Pais();
					paisRow.setid(rs.getInt("pai_id"));
					paisRow.setNombre(rs.getString("pai_nombre"));
					return paisRow;
				}			
				return null;
			}		
		});
		return paisObjeto;
	}

	@Override
	public Pais obtenerPais(String username) {
		logger.debug("Pais del usuario ");

		String SQL = "select pa.pai_id, pa.pai_nombre from pais pa "
				+ "INNER JOIN departamento d ON d.dep_pais = pa.pai_id "
				+ "INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		Pais paisUsuario = jdbcTemplateObject.query(SQL, new Object[] { username }, new ResultSetExtractor<Pais>(){

			@Override
			public Pais extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Pais paisRow = new Pais();
					paisRow.setid(rs.getInt("pai_id"));
					paisRow.setNombre(rs.getString("pai_nombre"));
					
					//Departamento departamento = departamentoDao.buscarDepartamento(rs.getInt("ciu_departamento"));				
					//Pais pais = paisDao.buscarPais(departamento.getId());						
					//departamento.setPais(pais);
					//ciudadRow.setDepartamento(departamento);
					
					return paisRow;
				}			
				return null;
			}		
		});
		return paisUsuario;	
	}

	@Override
	public Pais obtenerPais(int id) {
		logger.debug("Pais del usuario ");

		String SQL = "select pa.pai_id, pa.pai_nombre from pais pa WHERE pa.pai_id = ?;";

		Pais paisUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Pais>(){

			@Override
			public Pais extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Pais paisRow = new Pais();
					paisRow.setid(rs.getInt("pai_id"));
					paisRow.setNombre(rs.getString("pai_nombre"));				
					
					return paisRow;
				}			
				return null;
			}		
		});
		return paisUsuario;	
	}
	
	@Override
	public Pais obtenerPaisPorIdDeUsuario(int id) {
		logger.debug("Pais del usuario ");

		String SQL = "select pa.pai_id, pa.pai_nombre from pais pa "
				+ "INNER JOIN departamento d ON d.dep_pais = pa.pai_id "
				+ "INNER JOIN cuidad c ON c.ciu_departamento = d.dep_id "
				+ "INNER JOIN persona p ON p.per_ciudad = c.ciu_id "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_id = ?;";

		Pais paisUsuario = jdbcTemplateObject.query(SQL, new Object[] { id }, new ResultSetExtractor<Pais>(){

			@Override
			public Pais extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if(rs.next()){
					Pais paisRow = new Pais();
					paisRow.setid(rs.getInt("pai_id"));
					paisRow.setNombre(rs.getString("pai_nombre"));				
					
					return paisRow;
				}			
				return null;
			}		
		});
		return paisUsuario;	
	}

}
