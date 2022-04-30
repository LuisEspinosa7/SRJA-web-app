/**
 * 
 */
package com.sevensoftware.domotica.services.impl;


import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sevensoftware.domotica.dao.UsuarioDao;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Usuario;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.services.UsuarioServicio;
import com.sevensoftware.domotica.dto.Respuesta;

/**
 * @author LUIS
 *
 */
@Service("usuarioService")
public class UsuarioServicioImpl implements UsuarioServicio {
	
	private Logger logger = Logger.getLogger(UsuarioServicioImpl.class);

	@Autowired
	UsuarioDao usuarioDao;

	
	@Override
	public void agregarUsuario(Usuario usuario) {	
		
		logger.debug("--- EN AGREGAR USUARIO -----");		
		
		logger.debug("Iniciando Validaciones de Inscripcion de usuario");
		
		
		if (usuario.getIdentificacion() == null || usuario.getNombre() == null || usuario.getApellido() == null ||
				usuario.getEmail() == null || usuario.getCiudad() == null || usuario.getDepartamento() == null || usuario.getPais() == null ||
				usuario.getFechaNacimiento() == null || usuario.getTelefono() == null || usuario.getDireccion() == null || 
				usuario.getTipoIdentificacion() == null || usuario.getEstado() == null || usuario.getGenero() == null || usuario.getPassword() == null ||
				usuario.getUsername() == null) {
			throw new ValueNotPermittedException(Respuesta.VALOR_NULL_ERROR);
		}else{
					
			//Buscamos al usuario por identificacion, email y username
			Boolean identificacion = usuarioDao.buscarPorIdentificacion(usuario.getIdentificacion());
			Boolean email = usuarioDao.buscarPorEmail(usuario.getEmail());
			Boolean username = usuarioDao.buscarPorUsername(usuario.getUsername());
			
			if (identificacion || email || username) {
				throw new ObjectAlreadyExistException("El usuario ya esta registrado");
			}else{
				
				// Aqui ya podemos guardar el usuario
				try {
					boolean returnInsercion = usuarioDao.agregarUsuario(usuario);
					
					if (!returnInsercion) {				
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}			
					
				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
				}				
				
			}		
			
		}	
		
	}

	@Override
	public void modificarUsuario(int id, Usuario usuario) {
		
		logger.debug("--- EN MODIFICAR USUARIO -----");
		
		
		logger.debug("Iniciando Validaciones de Inscripcion de usuario");
		
		if (id > 0) {		
			
			if (usuario.getId() == 0 || usuario.getIdentificacion() == null || usuario.getNombre() == null || usuario.getApellido() == null ||
					usuario.getEmail() == null || usuario.getCiudad() == null || usuario.getDepartamento() == null || usuario.getPais() == null ||
					usuario.getFechaNacimiento() == null || usuario.getTelefono() == null || usuario.getDireccion() == null || 
					usuario.getTipoIdentificacion() == null || usuario.getEstado() == null || usuario.getGenero() == null || usuario.getPassword() == null ||
					usuario.getUsername() == null) {
				throw new ValueNotPermittedException(Respuesta.VALOR_NULL_ERROR);
			}else{
				
				// Aqui se debe validar con expresiones regulares
				
				
				//Buscamos al usuario por identificacion, email y username
				Boolean identificacion = usuarioDao.buscarPorIdentificacion(usuario.getIdentificacion());
				Boolean email = usuarioDao.buscarPorEmail(usuario.getEmail());
				Boolean username = usuarioDao.buscarPorUsername(usuario.getUsername());
				
				if (!identificacion && !email && !username) {
					throw new ObjectNotFoundException("El usuario no se encuentra registrado");
				}else{					
					// Aqui ya podemos modificar el usuario
					try {
						boolean returnInsercion = usuarioDao.modificarUsuario(id, usuario);
						
						if (!returnInsercion) {				
							throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
						}			
						
					} catch (DAOException daoe) {
						daoe.printStackTrace();
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}				
					
				}		
				
			}
			
			
		}else{			
			throw new ValueNotPermittedException(Respuesta.VALOR_ID_NULL_ERROR);
		}
						
		logger.debug("--- SALIR DE MODIFICAR USUARIO -----");		
	}

	@Override
	public void eliminarUsuario(int id) {
		
		logger.debug("--- EN ELIMINAR USUARIO -----");
		
		
		if (id > 0) {
			
			
			Boolean existePorId = usuarioDao.buscarUsuario(id);
			
			if (!existePorId) {
				throw new ObjectNotFoundException("El usuario no se encuentra registrado");
			}else{					
				// Aqui ya podemos modificar el usuario
				try {
					boolean returnInsercion = usuarioDao.eliminarUsuario(id);
					
					if (!returnInsercion) {				
						throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
					}			
					
				} catch (DAOException daoe) {
					daoe.printStackTrace();
					throw new DAOException(Respuesta.ERROR_EJECUTAR_OPERACION);			
				}			
				
			}			
			
		}else{			
			throw new ValueNotPermittedException(Respuesta.VALOR_ID_NULL_ERROR);
		}
				
		logger.debug("--- SALIR DE ELIMINAR USUARIO -----");		
	}

	@Override
	public List<Usuario> listarUsuario(String criterio, String acronimo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONRespuesta listarTablaUsuario(String search, int start, int length, int draw, int posicion,
			String direccion) {

		return usuarioDao.listarTablaUsuario(search, start, length, draw, posicion, direccion);
	}

}
