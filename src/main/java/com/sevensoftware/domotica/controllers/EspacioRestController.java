/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.sevensoftware.domotica.services.EspacioService;
import com.sevensoftware.domotica.util.Respuesta;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.EmptyCollectionException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Espacio;


/**
 * @author LUIS
 * @version 1.0
 */
@RestController
public class EspacioRestController {
	
	private static final Logger logger = Logger.getLogger(EspacioRestController.class);

	private static final String url = "/api/espacios";
	
	@Autowired
	EspacioService espacioServicio;
	
	/**
	 * Listar datos de espacios seg�n la estructura que requiere DataTable de JQuery 
	 * @param search
	 *            parametro a buscar en la consulta
	 * @param start
	 *            limite de inicio de los registros
	 * @param length
	 *            limite de fin hasta donde se consultaran los registros
	 * @param draw
	 *            n�mero de veces que se realiza la consulta
	 * @param posicion
	 *            posici�n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion
	 *            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de espacios segun los parametros enviados
	 */
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public JSONRespuesta getEspacios(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listEspacios = new JSONRespuesta();
		listEspacios = espacioServicio.listarTablaEspacios(search, start, length, draw, posicion, direccion);
		return listEspacios;
	}
	
	
	@RequestMapping(value = url + "/lista" , method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN", "ROLE_INTEGRANTE", "ROLE_PROPIETARIO" })
	public List<Espacio> getListaEspacios() {

		List<Espacio> listaDeEspacios = new ArrayList<Espacio>();
		listaDeEspacios = espacioServicio.listarEspacios();
		return listaDeEspacios;
	}

	
	/**
	 * Metodo POST espacio para agregar
	 * 
	 * @param espacio
	 *            objeto que contiene los parametros del espacio
	 * @return retorna mensaje de operaci�n exitosa o ocurrio un error.
	 */
	@RequestMapping(value = url, method = RequestMethod.POST, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> adicionarEspacio(@RequestBody Espacio espacio) {
				
		espacioServicio.agregarEspacio(espacio);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Metodo PUT espacio para poder modificar el espacio
	 * 
	 * @param id
	 *            id deL espacio a modificar
	 * @param espacio
	 *            objeto que contiene los parametros del espacio
	 * @return retorna mensaje de operaci�n exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> modificarEspacio(@PathVariable int id, @RequestBody Espacio espacio) {
		
		espacioServicio.modificarEspacio(id, espacio);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	

	/**
	 * Motodo DELETE Espacio para eliminar una Espacio
	 * 
	 * @param id
	 *            id de la Espacio que se quiere eliminar
	 * @return retorna mensaje de operaci�n exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> eliminarEspacio(@PathVariable int id) {

		espacioServicio.eliminarEspacio(id);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
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
		respuesta.setMensaje(onf.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ObjectAlreadyExistException.class)
	public ResponseEntity<Respuesta> handleObjectAlreadyExist(ObjectAlreadyExistException oae){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setMensaje(oae.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.CONFLICT);	
	}
		
	@ExceptionHandler(EmptyCollectionException.class)
	public ResponseEntity<Respuesta> handleEmptyCollection(EmptyCollectionException ece){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setMensaje(ece.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ValueNotPermittedException.class)
	public ResponseEntity<Respuesta> handleValueNotPermitted(ValueNotPermittedException vnpe){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setMensaje(vnpe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DAOException.class)
	public ResponseEntity<Respuesta> handleDAOException(DAOException daoe){
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(false);
		respuesta.setMensaje(daoe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
