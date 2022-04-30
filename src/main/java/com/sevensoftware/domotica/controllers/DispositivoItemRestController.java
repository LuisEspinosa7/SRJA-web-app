/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sevensoftware.domotica.dao.DispositivoItemDao;
import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.Estado;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.LiveData;
import com.sevensoftware.domotica.services.DispositivoItemService;
import com.sevensoftware.domotica.services.EstadoService;
import com.sevensoftware.domotica.util.Constantes;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.exception.EmptyCollectionException;
import com.sevensoftware.domotica.exception.ObjectAlreadyExistException;
import com.sevensoftware.domotica.exception.ObjectNotFoundException;
import com.sevensoftware.domotica.exception.ValueNotPermittedException;
import com.sevensoftware.domotica.dto.Respuesta;



/**
 * @author LUIS
 *
 */
@RestController
public class DispositivoItemRestController {
	
	private static final Logger logger = Logger.getLogger(DispositivoItemRestController.class);
	
	private static final String url = "/api/dispositivosItem";
	
	private SimpMessagingTemplate template;
	
	@Autowired
	private DispositivoItemService dispositivoItemService;
	
	@Autowired
	private DispositivoItemDao dispositivoItemDao;
	
	
	@Autowired
    public DispositivoItemRestController(SimpMessagingTemplate template) {
        this.template = template;
    }
	

	
	
	@RequestMapping(value = url, method = RequestMethod.GET)
	public List<DispositivoItem> listarDispositivoItems() {
		return dispositivoItemService.listarDispositivoItem(); 
	}
	
	//SE DEBE PROPORCIONAR EL ID DEL ESPACIO DEL CUAL SE REQUIEREN LOS DISPOSITIVOS ITEMS
	@RequestMapping(value = url + "/actuadores/{espacioId}", method = RequestMethod.GET)
	@Secured({ "ROLE_ADMIN", "ROLE_PROPIETARIO", "ROLE_INTEGRANTE" })
	public List<DispositivoItem> listarDispositivoItemsActuadores(@PathVariable int espacioId) {
		return dispositivoItemDao.listarDispositivosItems("ACTUADOR", espacioId);
	}		
	
	
	@RequestMapping(value = url + "/liveData/{dispositivoItemId}/{tipoValorId}", method = RequestMethod.GET)
	@Secured({ "ROLE_ADMIN", "ROLE_PROPIETARIO", "ROLE_INTEGRANTE" })
	public LiveData liveDataDispositivoItem(@PathVariable int dispositivoItemId, @PathVariable int tipoValorId) {
		return dispositivoItemDao.getLiveDataDispositivoItem(dispositivoItemId, tipoValorId);
	}
	
	
	
	/**
	 * Metodo PUT dispositivo item actuado  para poder modificar el dispositivo item
	 * 
	 * @param id
	 *            id de la dispositivo item a modificar
	 * @param usuario
	 *            objeto que contiene los parametros del dispositivo item
	 * @return retorna mensaje de operaci�n exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/actuadores/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN", "ROLE_PROPIETARIO", "ROLE_INTEGRANTE" })
	public ResponseEntity<Respuesta> modificarDispositivoItemActuador(@PathVariable int id, @RequestBody DispositivoItem dispositivoItem) {
		
		
		RestTemplate restTemplate = new RestTemplate();
		String url = Constantes.URL_VIVIENDA + "/domotica/api/dispositivosItems/actuadores/{id}";
		
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List <MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(mediaTypeList);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DispositivoItem> requestEntity = new HttpEntity<>(dispositivoItem, requestHeaders);
		
		 // Create the HTTP PUT request		
		Map<String, Integer> map = new HashMap<String, Integer>();
	    map.put("id", dispositivoItem.getId());	
	    
		ResponseEntity<Respuesta> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Respuesta.class, map);
				 
		if (response == null) {			
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Respuesta");
			respuesta.setMensaje("No se pudo Modificar!");
			respuesta.setBody(null);			
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
		
		// SI 304 NO MODIFICADO ENTONCES 
		if (HttpStatus.NOT_MODIFIED.equals(response.getStatusCode())) {			
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Respuesta");
			respuesta.setMensaje("No se pudo Modificar!");
			respuesta.setBody(null);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NOT_MODIFIED);
		}		
		
		// SI 304 NO MODIFICADO ENTONCES 
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(response.getStatusCode())) {			
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Respuesta");
			respuesta.setMensaje("No se pudo Modificar!");
			respuesta.setBody(null);	
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		// SI 200 ENTONCES 
		if (HttpStatus.OK.equals(response.getStatusCode())) {			
			dispositivoItemService.modificarDispositivoItemActuador(id, dispositivoItem);									
		}	
				
		
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje("Modificado!");
		respuesta.setBody(null);
		
		
		Respuesta respuesta2 = new Respuesta();
		respuesta2.setTipo("Datos");
		respuesta2.setMensaje("Enviando dispositivo item modificado");		
		respuesta2.setBody(dispositivoItem);																																
		template.convertAndSend("/topic/dispositivositem/actuadores", respuesta2);
				
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);

	}
	
	
	
	
	
	/**
	 * Listar datos de dispositivos items la estructura que requiere DataTable de JQuery 
	 * @param search
	 *            parametro a buscar en la consulta
	 * @param start
	 *            limite de inicio de los registros
	 * @param length
	 *            limite de fin hasta donde se consultaran los registros
	 * @param draw
	 *            nï¿½mero de veces que se realiza la consulta
	 * @param posicion
	 *            posiciï¿½n de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion
	 *            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de dispositivos segun los parametros enviados
	 */
	@RequestMapping(value = url, method = RequestMethod.GET, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public JSONRespuesta getDispositivosItems(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listDispositivosItems = new JSONRespuesta();
		listDispositivosItems = dispositivoItemService.listarTablaDispositivosItem(search, start, length, draw, posicion, direccion);
		return listDispositivosItems;
	}
	
	
	
	
	
	/**
	 * Metodo POST dispositivo items items para agregar
	 * 
	 * @param dispositivo items
	 *            objeto que contiene los parametros del dispositivo items
	 * @return retorna mensaje de operaciï¿½n exitosa o ocurrio un error.
	 */
	@RequestMapping(value = url, method = RequestMethod.POST, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> adicionarDispositivoItems(@RequestBody DispositivoItem dispositivoItem) {
				
		dispositivoItemService.agregarDispositivoItem(dispositivoItem);

		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
	}

	/**
	 * Metodo PUT dispositivo item para poder modificar el dispositivo item
	 * 
	 * @param id
	 *            id deL dispositivo item a modificar
	 * @param dispositivo
	 *            objeto que contiene los parametros del dispositivo
	 * @return retorna mensaje de operaciï¿½n exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> modificarDispositivoItem(@PathVariable int id, @RequestBody DispositivoItem dispositivoItem) {
		
		dispositivoItemService.modificarDispositivoItem(id, dispositivoItem);
		
		Respuesta respuesta = new Respuesta();
		respuesta.setExito(true);
		respuesta.setMensaje(Respuesta.OPERACION_EJECUTADA_EXITOSAMENTE);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}
	
	

	/**
	 * Motodo DELETE Dispositivo para eliminar una Dispositivo
	 * 
	 * @param id
	 *            id de la Dispositivo que se quiere eliminar
	 * @return retorna mensaje de operaciï¿½n exitosa o ocurrio un error.
	 */
	
	@RequestMapping(value = url + "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Respuesta> eliminarDispositivo(@PathVariable int id) {

		dispositivoItemService.eliminarDispositivoItem(id);
		
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
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(mensaje);
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Respuesta> handleObjectNotFound(ObjectNotFoundException onf){		
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(onf.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ObjectAlreadyExistException.class)
	public ResponseEntity<Respuesta> handleObjectAlreadyExist(ObjectAlreadyExistException oae){
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(oae.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.CONFLICT);	
	}
		
	@ExceptionHandler(EmptyCollectionException.class)
	public ResponseEntity<Respuesta> handleEmptyCollection(EmptyCollectionException ece){
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(ece.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ValueNotPermittedException.class)
	public ResponseEntity<Respuesta> handleValueNotPermitted(ValueNotPermittedException vnpe){
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(vnpe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DAOException.class)
	public ResponseEntity<Respuesta> handleDAOException(DAOException daoe){
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje(daoe.getMessage());
		respuesta.setBody(null);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	
}