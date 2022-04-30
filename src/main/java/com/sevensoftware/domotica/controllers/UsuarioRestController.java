/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sevensoftware.domotica.services.UsuarioServicio;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.EmptyCollectionException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Usuario;


/**
 * @author LUIS
 * @version 1.0
 */
@RestController
public class UsuarioRestController {
	
	private static final Logger logger = Logger.getLogger(UsuarioRestController.class);

	private static final String url = "/api/usuarios";
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	/**
	 * Listar datos de usuarios según la estructura que requiere DataTable de JQuery 
	 * @param search
	 *            parametro a buscar en la consulta
	 * @param start
	 *            limite de inicio de los registros
	 * @param length
	 *            limite de fin hasta donde se consultaran los registros
	 * @param draw
	 *            número de veces que se realiza la consulta
	 * @param posicion
	 *            posición de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion
	 *            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de usuarios segun los parametros enviados
	 */
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public JSONRespuesta getUsuarios(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listUsuarios = new JSONRespuesta();
		listUsuarios = usuarioServicio.listarTablaUsuario(search, start, length, draw, posicion, direccion);
		return listUsuarios;
	}

	
	/**
	 * Metodo POST usuario para agregarlos
	 * 
	 * @param usuario
	 *            objeto que contiene los parametros del usuario
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = url, method = RequestMethod.POST, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> adicionarUsuario(@RequestBody Usuario usuario) {
				
		usuarioServicio.agregarUsuario(usuario);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(Respuesta.INSCRIPCION_USUARIO_EXITOSA);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Metodo PUT usuario para poder modificar el usuario
	 * 
	 * @param id
	 *            id de la persona a modificar
	 * @param usuario
	 *            objeto que contiene los parametros del usuario
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> modificarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
		
		usuarioServicio.modificarUsuario(id, usuario);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(Respuesta.MODIFICACION_USUARIO_EXITOSA);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	

	/**
	 * Motodo DELETE Usuario para eliminar una Usuario
	 * 
	 * @param id
	 *            id de la Usuario que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> eliminarUsuario(@PathVariable int id) {

		usuarioServicio.eliminarUsuario(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(Respuesta.ELIMINACION_USUARIO_EXITOSA);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);	
		
	}

	/**
	 * 
	 * @param mensaje
	 *            texto que se quiere retornar
	 * @return retorna de ResponseEntity un objeto JSON con el mensaje de
	 *         respuesta erroneo
	 */
	public ResponseEntity<Respuesta> returnMensaje(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		respuesta.setExito(false);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Respuesta> handleObjectNotFound(ObjectNotFoundException onf){		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(onf.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ObjectAlreadyExistException.class)
	public ResponseEntity<Respuesta> handleObjectAlreadyExist(ObjectAlreadyExistException oae){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(oae.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.CONFLICT);	
	}
		
	@ExceptionHandler(EmptyCollectionException.class)
	public ResponseEntity<Respuesta> handleEmptyCollection(EmptyCollectionException ece){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(ece.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ValueNotPermittedException.class)
	public ResponseEntity<Respuesta> handleValueNotPermitted(ValueNotPermittedException vnpe){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(vnpe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DAOException.class)
	public ResponseEntity<Respuesta> handleDAOException(DAOException daoe){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setTipo(Respuesta.TIPO_RESPUESTA);
		respuesta.setMensaje(daoe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
