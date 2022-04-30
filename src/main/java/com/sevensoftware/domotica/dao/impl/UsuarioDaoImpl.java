/**
 * 
 */
package com.sevensoftware.domotica.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.dao.CiudadDao;
import com.sevensoftware.domotica.dao.DepartamentoDao;
import com.sevensoftware.domotica.dao.EstadoDao;
import com.sevensoftware.domotica.dao.GeneroDao;
import com.sevensoftware.domotica.dao.PaisDao;
import com.sevensoftware.domotica.dao.PerfilDao;
import com.sevensoftware.domotica.dao.TipoIdentificacionDao;
import com.sevensoftware.domotica.dao.UsuarioDao;
import com.sevensoftware.domotica.entities.Ciudad;
import com.sevensoftware.domotica.entities.CustomUserDetail;
import com.sevensoftware.domotica.entities.Departamento;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.Genero;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.Persona;
import com.sevensoftware.domotica.entities.Perfil;
import com.sevensoftware.domotica.entities.TipoIdentificacion;
import com.sevensoftware.domotica.entities.Usuario;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.util.Encriptar;



/**
 * @author LUIS
 * @version 1.0
 */
@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	private Logger logger = Logger.getLogger(UsuarioDaoImpl.class);

	@Autowired
	@Qualifier("dataSourceDomotica")
	DataSource dataSourceDomotica;

	@Autowired
	TipoIdentificacionDao tipoIdentificacionDao;

	@Autowired
	EstadoDao estadoDao;

	@Autowired
	GeneroDao generoDao;

	@Autowired
	CiudadDao ciudadDao;
	
	@Autowired
	DepartamentoDao departamentoDao;
	
	@Autowired
	PaisDao paisDao;	
	
	@Autowired
	PerfilDao perfilDao;

	private JdbcTemplate jdbcTemplateObject;
	

	@Autowired
	public void setDataSource() {
		this.jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}

	@Override
	public Usuario buscarPorNombre(String nombre) {
		logger.debug("Buscando por Nombre - Usuario .... ");
		logger.debug("Dentro de dao de usuario");
		
		Departamento departamento = departamentoDao.obtenerDepartamento(nombre);		
		Pais pais = paisDao.obtenerPais(nombre);
		Perfil perfil = perfilDao.buscarPerfil(nombre);
		
		String SQL = "SELECT p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, p.per_fecha_nacimiento, "
				+ "p.per_telefono, p.per_direccion, u.usu_estado, p.per_genero, p.per_tipo_identificacion, p.per_ciudad, "
				+ "u.usu_id ,u.usu_login, u.usu_password  FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "WHERE u.usu_login = ?;";

		Usuario usuarioResponse = jdbcTemplateObject.query(SQL, new Object[] { nombre },
				new ResultSetExtractor<Usuario>() {

					@Override
					public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("usu_estado"));
							TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacionPorId(rs.getInt("per_tipo_identificacion"));	
							Genero genero = generoDao.obtenerGeneroPorId(rs.getInt("per_genero"));
							Ciudad ciudad = ciudadDao.obtenerCiudadPorId(rs.getInt("per_ciudad"));
							
							
							Usuario usuarioRow = new Usuario();
							usuarioRow.setId(rs.getInt("usu_id"));
							usuarioRow.setUsername(rs.getString("usu_login"));
							usuarioRow.setPassword(rs.getString("usu_password"));
							
							usuarioRow.setTipoIdentificacion(tipoIdentificacion);
							usuarioRow.setGenero(genero);
							usuarioRow.setEstado(estado);
							usuarioRow.setCiudad(ciudad);
							usuarioRow.setDepartamento(departamento);
							usuarioRow.setPais(pais);	
							usuarioRow.setPerfil(perfil);
							
							usuarioRow.setIdentificacion(rs.getString("per_identificacion"));
							usuarioRow.setNombre(rs.getString("per_nombres"));
							usuarioRow.setApellido(rs.getString("per_apellidos"));
							usuarioRow.setEmail(rs.getString("per_email"));
							usuarioRow.setFechaNacimiento(rs.getDate("per_fecha_nacimiento"));
							usuarioRow.setTelefono(rs.getString("per_telefono"));
							usuarioRow.setDireccion(rs.getString("per_direccion"));
							
							logger.debug("USUARIO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + usuarioRow.getId()
									+ " Nombre= " + usuarioRow.getNombre() + ", IDENTIFICACION= "
									+ usuarioRow.getIdentificacion());

							return usuarioRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por Usuario .... ");
		System.out.println("Saliendo del dao de usuario");
		return usuarioResponse;
	}


	@Override
	public boolean agregarUsuario(Usuario usuario) { // string es la tabla 
		
		logger.debug("--- AGREGANDO NUEVO USUARIO - PERSONA ------");

		try {
			
			final String SQL1 = "INSERT INTO persona (per_identificacion, per_tipo_identificacion, per_nombres, per_apellidos, per_fecha_nacimiento, "
					+ "per_ciudad, per_email, per_telefono, per_direccion, per_genero) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplateObject.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, usuario.getIdentificacion());
					pstm.setInt(2, usuario.getTipoIdentificacion().getId());
					pstm.setString(3, usuario.getNombre().toUpperCase());
					pstm.setString(4, usuario.getApellido().toUpperCase());
					pstm.setDate(5, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
					pstm.setInt(6, usuario.getCiudad().getId());
					pstm.setString(7, usuario.getEmail());
					pstm.setString(8, usuario.getTelefono());
					pstm.setString(9, usuario.getDireccion());
					pstm.setInt(10, usuario.getGenero().getId());
					//pstm.setInt(11, usuario.getEstado().getId());
					
					return pstm;
				}
			}, keyHolder);
	
			
			if (resultado > 0) {
				logger.debug("--- PERSONA AGREGADA ----");
				int key = 0;
				
				if (keyHolder.getKeys().size() == 1) {
					key = keyHolder.getKey().intValue();					
				} else if (keyHolder.getKeys().size() > 1) {
					logger.debug("------- Se encuentra mas de una llave ----");
					key = Integer.parseInt( String.valueOf(keyHolder.getKeys().get("per_id")));					
				} else {
					logger.debug("------- No se pudo extraer la llave ----");
					return false;
				}
				
				
				final String id = Integer.toString(key);
				logger.debug("Llave primaria de persona -----> " + key);
				logger.debug("------Encriptando Password-----");
				String encriptedPassword = Encriptar.toBCryptPasswordEncoder(usuario.getPassword());	
				logger.debug("**** CLAVE USUARIO ENCRYPTADA **** = " + encriptedPassword);
				
				logger.debug("--ANTES DE INSERTAR EL USUARIO -----");
				String SQL2 = "INSERT INTO usuario (usu_login, usu_password, usu_persona, usu_estado) VALUES(?, ?, ?, ?);";
				
				int resultado2 = jdbcTemplateObject.update(SQL2, usuario.getUsername(), encriptedPassword, Integer.parseInt(id), 
						usuario.getEstado().getId());						
				
				
				if (resultado2 > 0) {
					logger.debug("---NUEVO USUARIO INSERTADO :)))------");
					
					Usuario usuarioObj = buscarPorNombre(usuario.getUsername());
					
					
					logger.debug("Insertando en la tabla perfiles");
					String SQL3 = "INSERT INTO perfil_usuario (perfu_perfil, perfu_usuario, perfu_estado) VALUES (?, ?, ?);";
										
					int resultado3 = jdbcTemplateObject.update(SQL3, usuario.getPerfil().getId(), usuarioObj.getId(), 1);
					
					if (resultado3 > 0) {				
						return true;
					}else{
						logger.debug("No se pudo insertar en la tabla administrador");
						throw new DAOException("No pudo insertar el administrador");				
						//return false;
					}
					
				}else{
					return false;
				}							
			}
			return false;		
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
		
	}


	@Override
	public JSONRespuesta listarTablaUsuario(String search, int start, int length, int draw, int posicion,
			String direccion) {

		JSONRespuesta respuesta = new JSONRespuesta();
		
		String[] campos = { "u.usu_id", "p.per_identificacion", "p.per_nombres", "p.per_apellidos", "u.usu_login" };

		int fin = start + length - 1;

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql
				+ "FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_estado IN (1, 2) ";

		int count = jdbcTemplateObject.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? ";
			sql = sql + "OR p.per_apellidos LIKE ? OR u.usu_login LIKE ? ) ";

			filtrados = jdbcTemplateObject.queryForObject(sql, new Object[] { "&" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println("***************1: " + sql);
		
			
		sql = "SELECT usu_id, per_identificacion, per_nombres, per_apellidos, usu_login "
				+ "from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber, "
				+ "u.usu_id, p.per_identificacion, p.per_nombres, p.per_apellidos, u.usu_login FROM persona p "
				+ "LEFT JOIN usuario u ON (u.usu_persona = p.per_id) "
				+ "WHERE u.usu_estado IN (1, 2) "
				+ "AND (p.per_identificacion LIKE ? OR p.per_nombres LIKE ? OR p.per_apellidos LIKE ? "
				+ "OR u.usu_login LIKE ? )) ";		
		sql = sql + "as tabla where tabla.RowNumber between ? and ? ";
		
		
		System.out.println("2: " + sql);

		List<Usuario> listaUsu = jdbcTemplateObject.query(sql, 
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Usuario>() {

					public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
						Usuario usuario = buscarPorNombre(rs.getString("usu_login"));
						usuario.setPassword(null);
						return usuario;							
					
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaUsu);

		return respuesta;

	}

	@Override
	public boolean modificarUsuario(int id, Usuario usuario) {
		
		logger.debug("--- DENTRO DE MODIFICAR USUARIO --------");
		
		String SQL1 = "UPDATE persona SET per_identificacion = ?, per_tipo_identificacion = ?, per_nombres = ?, per_apellidos = ?, "
				+ "per_fecha_nacimiento = ?, per_ciudad = ?, per_email = ?, per_telefono = ?, per_direccion = ?, per_genero = ? "
				+ "FROM usuario WHERE usuario.usu_id = ? AND usuario.usu_persona = persona.per_id;";
		
		int resultado1 = jdbcTemplateObject.update(SQL1, usuario.getIdentificacion(), usuario.getTipoIdentificacion().getId(), 
				usuario.getNombre(), usuario.getApellido(), usuario.getFechaNacimiento(), usuario.getCiudad().getId(), 
				usuario.getEmail(), usuario.getTelefono(), usuario.getDireccion(), usuario.getGenero().getId(), usuario.getId());
		
		if (resultado1 > 0) {
			logger.debug("--- PERSONA MODIFICADA :)))------");
			
			logger.debug("------Encriptando Password-----");
			String encriptedPassword = Encriptar.toBCryptPasswordEncoder(usuario.getPassword());	
			logger.debug("**** CLAVE USUARIO ENCRYPTADA **** = " + encriptedPassword);
						
			String SQL2 = "UPDATE usuario SET usu_login = ?, usu_password = ?, usu_persona = ?, usu_estado = ? WHERE usu_login = ?;";
			
			int idPersona = obtenerIdPersona(usuario.getId());
			
			int resultado2 = jdbcTemplateObject.update(SQL2, usuario.getUsername(), encriptedPassword, idPersona, 
					usuario.getEstado().getId(), usuario.getUsername());
			
			if (resultado2 > 0) {
				logger.debug("--- USUARIO MODIFICADO :)))------");
				
				String SQL3 = "UPDATE perfil_usuario SET perfu_perfil = ? WHERE perfu_usuario = ?;";
				
				int resultado3 = jdbcTemplateObject.update(SQL3, usuario.getPerfil().getId(), usuario.getId());
				
				if (resultado3 > 0) {
					logger.debug("--- PERFIL MODIFICADO MODIFICADO :)))------");
					return true;
				}else{
					return false;
				}		
				
			}else{
				return false;
			}		
		}else{
			return false;
		}	
		
	}

	@Override
	public boolean eliminarUsuario(int id) {
		
		logger.debug("--- DENTRO DE ELIMINAR USUARIO --------");
		
		int idEliminado = 3;
		
		logger.debug("--- CODIGO ELIMINADO SACADO:)))------");
						
		String SQL1 = "UPDATE usuario SET usu_estado = ? WHERE usuario.usu_id = ?;";
						
		int resultado1 = jdbcTemplateObject.update(SQL1, idEliminado, id );
			
		if (resultado1 > 0) {
			logger.debug("--- USUARIO CAMBIADO DE ESTADO A ELIMINADO :)))------");
			return true;
		}else{
			return false;
		}			
	}
	
	@Override
	public Boolean buscarPorEmail(String email) {
		
		logger.debug("------- Buscando al usuario por email");
		
		String sql = "SELECT COUNT(*) as cant FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE p.per_email = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{email}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}	
	}
	
	@Override
	public Boolean buscarPorIdentificacion(String identificacion) {
		
		logger.debug("------- Buscando al usuario por Identificacion");
		
		String sql = "SELECT COUNT(*) as cant FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE p.per_identificacion = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{identificacion}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}		
	}
	
	
	public int obtenerIdPersona(int id){
		
		logger.debug("Listado de estados por entidad");

		String SQL = "SELECT p.per_id FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "WHERE u.usu_id = ?";

		int idPersona = jdbcTemplateObject.query(SQL, new Object[]{id}, new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (rs.next()) {
					int id = rs.getInt("per_id");					
					return id;
				}				
				return null;
			}			
		});
		return idPersona;		
	}

	
	@Override
	public Boolean buscarPorUsername(String username) {
		
		logger.debug("------- Buscando al usuario por username");
		
		String sql = "SELECT COUNT(*) as cant FROM persona p "
				+ "INNER JOIN usuario u ON u.usu_persona = p.per_id WHERE u.usu_login = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{username}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}		
		
	}

	@Override
	public Boolean buscarUsuario(int id) {
		
		logger.debug("------- Buscando al usuario por username");
		
		String sql = "SELECT COUNT(*) as cant FROM usuario u WHERE u.usu_id = ?;";

		int count = jdbcTemplateObject.queryForObject(sql, new Object[]{id}, Integer.class);
		
		if (count > 0) {
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		
		logger.debug("Buscando por Id - Usuario .... ");
		logger.debug("Dentro de dao de usuario");
		

		String SQL = "SELECT p.per_id, p.per_identificacion, p.per_nombres, p.per_apellidos, p.per_email, p.per_fecha_nacimiento, "
				+ "p.per_telefono, p.per_direccion, u.usu_estado, p.per_genero, p.per_tipo_identificacion, p.per_ciudad, "
				+ "u.usu_id ,u.usu_login, u.usu_password  FROM persona p INNER JOIN usuario u ON u.usu_persona = p.per_id "
				+ "WHERE u.usu_id = ?;";

		Usuario usuarioResponse = jdbcTemplateObject.query(SQL, new Object[] { id },
				new ResultSetExtractor<Usuario>() {

					@Override
					public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {

						if (rs.next()) {
							
							Estado estado = estadoDao.obtenerEstadoPorId(rs.getInt("usu_estado"));
							TipoIdentificacion tipoIdentificacion = tipoIdentificacionDao.obtenerTipoIdentificacionPorId(rs.getInt("per_tipo_identificacion"));	
							Genero genero = generoDao.obtenerGeneroPorId(rs.getInt("per_genero"));
							Ciudad ciudad = ciudadDao.obtenerCiudadPorId(rs.getInt("per_ciudad"));
							Departamento departamento = departamentoDao.obtenerDepartamentoPorIdDeUsuario(id);
							Pais pais = paisDao.obtenerPaisPorIdDeUsuario(id);
							
							
							Usuario usuarioRow = new Usuario();
							usuarioRow.setId(rs.getInt("usu_id"));
							usuarioRow.setUsername(rs.getString("usu_login"));
							//usuarioRow.setPassword(rs.getString("usu_password"));
							usuarioRow.setPassword(null);
							
							usuarioRow.setTipoIdentificacion(tipoIdentificacion);
							usuarioRow.setGenero(genero);
							usuarioRow.setEstado(estado);
							usuarioRow.setCiudad(ciudad);
							usuarioRow.setDepartamento(departamento);
							usuarioRow.setPais(pais);						
							
							usuarioRow.setIdentificacion(rs.getString("per_identificacion"));
							usuarioRow.setNombre(rs.getString("per_nombres"));
							usuarioRow.setApellido(rs.getString("per_apellidos"));
							usuarioRow.setEmail(rs.getString("per_email"));
							usuarioRow.setFechaNacimiento(rs.getDate("per_fecha_nacimiento"));
							usuarioRow.setTelefono(rs.getString("per_telefono"));
							usuarioRow.setDireccion(rs.getString("per_direccion"));
							
							logger.debug("USUARIO SACADO DE LA BASE DE DATOS=>>>>" + "Id = " + usuarioRow.getId()
									+ " Nombre= " + usuarioRow.getNombre() + ", IDENTIFICACION= "
									+ usuarioRow.getIdentificacion());

							return usuarioRow;
						}
						return null;
					}
				});

		logger.debug("Termino de Buscar por Usuario .... ");
		System.out.println("Saliendo del dao de usuario");
		return usuarioResponse;
		
	}

	
		
}
