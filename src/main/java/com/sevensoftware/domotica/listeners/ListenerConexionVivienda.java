/**
 * 
 */
package com.sevensoftware.domotica.listeners;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevensoftware.domotica.dao.DispositivoItemDao;
import com.sevensoftware.domotica.dao.DispositivoItemValorDao;
import com.sevensoftware.domotica.dao.EventoDao;
import com.sevensoftware.domotica.dao.TipoEventoDao;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.DispositivoItemValor;
import com.sevensoftware.domotica.entities.Evento;
import com.sevensoftware.domotica.entities.LiveData;
import com.sevensoftware.domotica.entities.Message;
import com.sevensoftware.domotica.entities.TMedicion;
import com.sevensoftware.domotica.entities.TipoEvento;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.services.DispositivoItemService;
import com.sevensoftware.domotica.services.EventoService;
import com.sevensoftware.domotica.util.Constantes;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author LUIS
 *
 */
@Component
public class ListenerConexionVivienda {
		
	private static SimpMessagingTemplate template;
	
	private static final int ID_ELECTROVALVULA_RIEGO = 10;
	
	@Autowired
	@Qualifier("dataSourceControlador")
	DataSource dataSourceDomotica;
	
	@Autowired
	DispositivoItemDao dispositivoItemDao;
	
	//@Autowired
	//private static DispositivoItemService dispositivoItemService;
	
	@Autowired
	DispositivoItemValorDao dispositivoItemValorDao;
	
	@Autowired
	TipoEventoDao tipoEventoDao;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	EventoDao eventoDao;
	
	
	private static final int TIPO_EVENTO_NOTIFICACION = 1;
	private static final int TIPO_EVENTO_ADVERTENCIA = 2;
	private static final int TIPO_EVENTO_ALERTA = 3;
	private static final int TIPO_EVENTO_RECORDATORIO = 4;
	
	private static final int CONFIRMADO = 1;
	private static final int NO_CONFIRMADO = 0;
	
	@Autowired
    public ListenerConexionVivienda(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	private static JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource() {
		jdbcTemplateObject = new JdbcTemplate(dataSourceDomotica);
	}
	
	

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {		
		//System.out.println("############### CONTEXTO CREATED, READY FOR CONECTING WITH HOUSES ##########");
		System.out.println(" Iniciando Thread de INFORMACION DE CONEXION ");
		ThreadVivienda TCV1 = new ThreadVivienda("Thread-Conexion"); 
		TCV1.start();
		
				
		System.out.println("Iniciando Thread de INFORMACION DE NODOS (MEDICIONES)");
		ThreadVivienda TCV2 = new ThreadVivienda("Thread-Mediciones"); 
		TCV2.start();
		
		System.out.println("Iniciando Thread de CONTROL DE VALOR DE SENSORES");
		ThreadVivienda TCV3 = new ThreadVivienda("Thread-Control"); 
		TCV3.start();
	
		System.out.println("Iniciando Thread de REVISIONES DE ALERTAS");
		ThreadVivienda TCV4 = new ThreadVivienda("Thread-Revision"); 
		TCV4.start();		
		
	}
	
	
	public static void getNodosInfo() throws ConnectException{
        //Obteniendo Informacion de Nodos
		RestTemplate restTemplate = new RestTemplate();
		//String url = "http://192.168.0.14:5000/domotica/api/nodos";			
		String url = Constantes.URL_VIVIENDA + "domotica/api/nodos";
		
		while (true) {	
			
			try {
				// 10 SEGUNDO DE ESPERA
				System.out.println("ESPERANDO 10 SEGUNDOS PARA SOLICITAR DATOS DE NODO");
				Thread.sleep(10000);
				//Peticion datos de nodo riego 1
				//String result = restTemplate.getForObject(url, String.class);
								
				ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
				//assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
				System.out.println("JSON RECIBIDO= " + response.getBody());				
				
				//Parseando el JSON
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response.getBody());
				
				JsonNode nodos = root.path("nodos");
				System.out.println("IMPRIMIENDO LISTA DE NODOS= " + nodos);				
				
				for(JsonNode nodo : nodos){
					
					JsonNode data = nodo.path("data");
					if (data.isMissingNode()) {
						System.out.println("NO EXISTE EL DATA");
					}else{
						System.out.println("IMPRIMIENDO DATA DE NODO = " + nodo.path("id"));
						System.out.println(data);
						
						List<TMedicion> mediciones = new ArrayList<TMedicion>();
						
						for(JsonNode dato : data){
							System.out.println("Imprimiendo Dato: " + dato);
							
							// SACANDO PROPIEDADES PARA INSERTAR EN LA DB
							int dispositivoItem = dato.path("dispositivoItemId").asInt();
							int tipoMedicion = dato.path("tipoMedicion").asInt();
							int tipoValor = dato.path("tipoValor").asInt();
							double valor = dato.path("valor").asDouble();
														
							System.out.println("----- Se guardaran los siguientes valores ------");
							System.out.println("** Dispositivo Item Id =  " + dispositivoItem);
							System.out.println("** Tipo Medicion Id =  " + tipoMedicion);
							System.out.println("** Tipo Valor Id =  " + tipoValor);
							System.out.println("** Valor =  " + valor);
							
							System.out.println("------------------------------------------------");
							TMedicion medicion = new TMedicion();
							medicion.setDispositivoItemId(dispositivoItem);
							medicion.setTipoMedicion(tipoMedicion);
							medicion.setTipoValor(tipoValor);
							medicion.setValor(valor);
							
							mediciones.add(medicion);						
						}
						
						System.out.println("Insertando Mediciones ........");
						
						String SQL1 = "INSERT INTO medicion (med_dispositivo_item, med_tipo_medicion, med_fecha_hora, med_tipo_valor, med_valor) VALUES (?, ?, ?, ?, ?);";

						jdbcTemplateObject.batchUpdate(SQL1, new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								TMedicion medicion = mediciones.get(i);
								ps.setInt(1, medicion.getDispositivoItemId());
								ps.setInt(2, medicion.getTipoMedicion());
								
								LocalDateTime localDate = LocalDateTime.now();
								ps.setObject(3, localDate);
								
								//ps.setDate(3, new Date(System.currentTimeMillis()));
								ps.setInt(4, medicion.getTipoValor());
								ps.setDouble(5, medicion.getValor());								
							}

							@Override
							public int getBatchSize() {
								return mediciones.size();
							}
						});
						
									
					}					

				}				
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
    }
	
	
	
	
	public static void modificarDispositivoItemActuadorInThread(DispositivoItem dispositivoItem){
		
		System.out.println("Antes de SACAR LOS VALORES ");
		List<DispositivoItemValor> valores = dispositivoItem.getValores();		
		
		for(DispositivoItemValor dispositivoItemValor : valores) {
			System.out.println("Modificando un actuador valor");
			
			System.out.println("Id: " + dispositivoItemValor.getId());
			System.out.println("Valor: " + dispositivoItemValor.getValor());		
			
			String SQL1 = "UPDATE dispositivo_item_valor div SET div_valor = ? "
					+ "WHERE div.div_tipo_valor = ? AND div.div_dispositivo_item = ? AND div.div_id = ?;";
			
			int resultado1 = jdbcTemplateObject.update(SQL1, dispositivoItemValor.getValor(), dispositivoItemValor.getTipoValor().getId(), 
					dispositivoItem.getId(), dispositivoItemValor.getId());
			
			if (resultado1 > 0) {
				System.out.println("--- DispositivoItemValor MODIFICADo -----");		
			}else{
				System.out.println("No se pudo MODIFICAR el DispositivoItemValor");
			}
			
			
		}
		
		
	}	
	
	public static void cambiarEstadoActuador(DispositivoItem dispositivoItem, Evento evento){
		
		RestTemplate restTemplate = new RestTemplate();
		//String url = "http://192.168.0.14:5000/domotica/api/dispositivosItems/actuadores/{id}";
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
				 
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (response == null) {
			System.out.println("********* LA RESPUESTA FUE NULL");
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Evento");
			respuesta.setMensaje("Evento!");
			TipoEvento tipoEvento = new TipoEvento(2, "ADVERTENCIA", "ADVERTENCIA");			
			evento.setTipoEvento(tipoEvento);
			evento.setMensaje("NO SE PUDO ECENDER EL RIEGO");
			respuesta.setBody(evento);																																
			template.convertAndSend("/topic/eventos", respuesta);
		}
		
		// SI 304 NO MODIFICADO ENTONCES 
		if (HttpStatus.NOT_MODIFIED.equals(response.getStatusCode())) {			
			System.out.println("********* LA RESPUESTA FUE 304");
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Evento");
			respuesta.setMensaje("Evento!");
			TipoEvento tipoEvento = new TipoEvento(2, "ADVERTENCIA", "ADVERTENCIA");			
			evento.setTipoEvento(tipoEvento);
			evento.setMensaje("NO SE PUDO ECENDER EL RIEGO");
			respuesta.setBody(evento);																																
			template.convertAndSend("/topic/eventos", respuesta);
		}		
		
		// SI 304 NO MODIFICADO ENTONCES 
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(response.getStatusCode())) {			
			System.out.println("********* LA RESPUESTA FUE 500");
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Evento");
			respuesta.setMensaje("Evento!");
			TipoEvento tipoEvento = new TipoEvento(2, "ADVERTENCIA", "ADVERTENCIA");			
			evento.setTipoEvento(tipoEvento);
			evento.setMensaje("NO SE PUDO ECENDER EL RIEGO");
			respuesta.setBody(evento);																																
			template.convertAndSend("/topic/eventos", respuesta);
		}	
		
		// SI 200 ENTONCES 
		if (HttpStatus.OK.equals(response.getStatusCode())) {
			System.out.println("********* LA RESPUESTA FUE 200");
			System.out.println("--------- CAMBIANDO ESTADO ACTUADOR -----------------");
			
			int idActuador = dispositivoItem.getId();
			System.out.println("ID DISPOSITIVO ITEM A CAMBIAR = " + idActuador);
			System.out.println("VALOR DEL DISPOSITIVO ITEM CAMBIADO = " + dispositivoItem.getValores().get(0).getValor());
			//dispositivoItemService.modificarDispositivoItemActuador(idActuador, dispositivoItem);
			modificarDispositivoItemActuadorInThread(dispositivoItem);
			
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			System.out.println("--------- ENVIANDO EVENTO A CLIENTE -----------------");
			Respuesta respuesta = new Respuesta();
			respuesta.setTipo("Evento");
			respuesta.setMensaje("Evento!");
			respuesta.setBody(evento);
			template.convertAndSend("/topic/eventos", respuesta);
			
			System.out.println("--------- ENVIADO CAMBIO DEL ACTUADOR AL TOPIC -----------------");
			Respuesta respuesta2 = new Respuesta();
			respuesta2.setTipo("Respuesta");
			respuesta2.setMensaje("Dispositivo item modificado");				
			respuesta2.setBody(dispositivoItem);																																
			template.convertAndSend("/topic/dispositivositem/actuadores", respuesta2);			
			
		}	
				
		
		
		
		
	}
	
	
	
	class ThreadVivienda extends Thread {
		private Thread t;
		private String threadName;

		//static final String ioserver = "http://192.168.0.14:5000/";
		static final String ioserver = Constantes.URL_VIVIENDA;
		
		ThreadVivienda(String name) {
			threadName = name;
			System.out.println("Creating " + threadName);
		}

		public void run() {
			System.out.println("Running " + threadName);

			/*
			 * Threads para cada usuario
			 */
			if (threadName.equalsIgnoreCase("Thread-Conexion")) {
				System.out.println(" Verificando Conexion constante");

				try {

					IO.Options options = new IO.Options();
					options.forceNew = true;
					options.reconnection = true;

					String namespace = "vivienda";
					Socket socket = IO.socket(ioserver + namespace, options);

					socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("CONECTADO AL SOCKET CONEXION PING PONG");
							
							System.out.println("Conexion Alive");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaA = new Respuesta();
							respuestaA.setTipo("Respuesta");
							respuestaA.setMensaje("Enviando estado de la conexion");

							Message messageA = new Message();
							messageA.setMessage("Connection Alive");
							respuestaA.setBody(messageA);
							template.convertAndSend("/topic/viviendaconexion", respuestaA);
							
							
						}
					}).on("pong_event", new Emitter.Listener() {

						@Override
						public void call(Object... args) {
							System.out.println("Llego PONG al servidor.....");
							String s = args[0].toString();
							System.out.println(s);
							String tipo;
							String message;

							try {
								JSONObject data = new JSONObject(s);
								tipo = data.getJSONObject("data").getString("tipo");
								System.out.println("Tipo de Mensaje From Server =  " + tipo);

								message = data.getJSONObject("data").getString("message");
								System.out.println("Message From Server =  " + message);

								if (tipo.equalsIgnoreCase("pong")) {

									if (!message.equalsIgnoreCase("Connection Alive")) {
										System.out.println("No hay Conexion con Flask Raspberry Pi");
									} else {
										System.out.println("Conexion Alive");
										// Se emite la respuesta al cliente de
										// angular
										System.out.println("Enviando Respuesta al Cliente Angular");

										Respuesta respuestaA = new Respuesta();
										respuestaA.setTipo("Respuesta");
										respuestaA.setMensaje("Enviando estado de la conexion");

										Message messageA = new Message();
										messageA.setMessage("Connection Alive");
										respuestaA.setBody(messageA);
										template.convertAndSend("/topic/viviendaconexion", respuestaA);

									}

								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}).on("notification_event", new Emitter.Listener() {

						@Override
						public void call(Object... args) {
							System.out.println("Llego NOTIFICACION del servidor.....");
							String s = args[0].toString();
							System.out.println(s);
							String tipo;
							String mensaje;

							try {
								JSONObject data = new JSONObject(s);
								tipo = data.getJSONObject("data").getString("tipo");
								System.out.println("Tipo de Mensaje From Server =  " + tipo);

								mensaje = data.getJSONObject("data").getString("message");
								System.out.println("Notificacion From Server =  " + mensaje);

								if (tipo.equalsIgnoreCase("notificacion")) {

									if (mensaje.equalsIgnoreCase("Emergencia")) {
										// Se emite la respuesta al cliente de
										// angular
										System.out.println("Enviando Notificacion de Emergencia!");
										System.out.println("Enviando Respuesta al Cliente Angular");

										Respuesta respuesta = new Respuesta();
										respuesta.setTipo("Notificacion");
										respuesta.setMensaje("Enviando notificacion");

										Message message = new Message();
										message.setMessage("Emergencia");
										respuesta.setBody(message);
										template.convertAndSend("/topic/viviendaconexion", respuesta);
									}
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("connect timeout");
							System.out.println("DISCONNECTED");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaN = new Respuesta();
							respuestaN.setTipo("Respuesta");
							respuestaN.setMensaje("Enviando estado de la conexion");

							Message messageN = new Message();
							messageN.setMessage("Connection Lost");
							respuestaN.setBody(messageN);

							template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("DISCONNECTED");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaN = new Respuesta();
							respuestaN.setTipo("Respuesta");
							respuestaN.setMensaje("Enviando estado de la conexion");

							Message messageN = new Message();
							messageN.setMessage("Connection Lost");
							respuestaN.setBody(messageN);

							template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					}).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("DISCONNECTED");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaN = new Respuesta();
							respuestaN.setTipo("Respuesta");
							respuestaN.setMensaje("Enviando estado de la conexion");

							Message messageN = new Message();
							messageN.setMessage("Connection Lost");
							respuestaN.setBody(messageN);

							template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					}).on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("DISCONNECTED");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaN = new Respuesta();
							respuestaN.setTipo("Respuesta");
							respuestaN.setMensaje("Enviando estado de la conexion");

							Message messageN = new Message();
							messageN.setMessage("Connection Lost");
							respuestaN.setBody(messageN);

							template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
						@Override
						public void call(Object... args) {
							System.out.println("DISCONNECTED");
							System.out.println("Enviando Respuesta al Cliente Angular");

							Respuesta respuestaN = new Respuesta();
							respuestaN.setTipo("Respuesta");
							respuestaN.setMensaje("Enviando estado de la conexion");

							Message messageN = new Message();
							messageN.setMessage("Connection Lost");
							respuestaN.setBody(messageN);

							template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					});

					socket.connect();

					try {
						System.out.println("ESPERANDO 9 SEGUNDOS PARA LA CONEXION");
						Thread.sleep(9000);
					} catch (InterruptedException e) {
					}
					
					
					while (true) {
						
						try {
							// 3 SEGUNDO DE ESPERA
							System.out.println("5 SEGUNDO DE ESPERA");
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						socket.emit("ping_event");						
					}
					
					
					
				} catch (Exception e) {
					System.out.println("Thread " + threadName + " interrupted.");
				}

				System.out.println("Termino iteracion de PING");
				System.out.println("Thread " + threadName + " exiting.");

			} else if (threadName.equalsIgnoreCase("Thread-Mediciones"))  {
				// THREAD PARA MEDICIONES ----------------------
				
				System.out.println(" Obteniendo Mediciones");

				try {
					
					getNodosInfo();									

				} catch (Exception e) {
					System.out.println("Thread " + threadName + " interrupted.");
				}
				
				System.out.println("Termino iteracion de PING");
				System.out.println("Thread " + threadName + " exiting.");
				
			}else if (threadName.equalsIgnoreCase("Thread-Control"))  {
				// THREAD PARA MEDICIONES ----------------------
				
				System.out.println(" Revisando Mediciones");

				try {
					
					//Revisamos cada valor y lo analizamos
					
					while (true) {
						System.out.println("REVISANDO ULTIMOS VALORES......");
						//************************  Valores de Riego
						// Temperatura
						//LiveData lastDataSoilTemperature = dispositivoItemDao.getLiveDataDispositivoItem(5, 11);
						LiveData lastDataSoilHumidity1a = dispositivoItemDao.getLiveDataDispositivoItem(6, 10);
						LiveData lastDataSoilHumidity1b = dispositivoItemDao.getLiveDataDispositivoItem(7, 10);
						LiveData lastDataRain = dispositivoItemDao.getLiveDataDispositivoItem(8, 8);
						
						//double temperaturaTierra = lastDataSoilTemperature.getData();						
						
						int humedadTierra1a = (int) ((lastDataSoilHumidity1a.getData() * 100) / 1023) ;
						int humedadTierra1b = (int) ((lastDataSoilHumidity1b.getData() * 100) / 1023) ;
						
						System.out.println("HUMEDAD TIERRA 1= " + humedadTierra1a);
						System.out.println("HUMEDAD TIERRA 2= " + humedadTierra1b);
						
						
						int lluvia = (int) lastDataRain.getData();						
						
						DispositivoItem dispositivoItem = dispositivoItemDao.obtenerDispositivoItem(ID_ELECTROVALVULA_RIEGO);
						
						List<DispositivoItemValor> valores = dispositivoItemValorDao.listarDispositivoItemValores(10);
						int valorRiego = (int) valores.get(0).getValor();
						
						
						if (valorRiego == 1 && lluvia == 1) {
							System.out.println("DEBERIAS CERRAR LA LLAVE, PORQUE ESTA LLOVIENDO!!!!!!!!!");
							// ADVERTENCIA PARA CERRAR LA LLAVE PORQUE ESTA LLOVIENDO
							Evento evento = new Evento();
							TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(TIPO_EVENTO_NOTIFICACION);							
							evento.setTipoEvento(tipoEvento);
							evento.setMensaje("LlAVE CERRADA, PORQUE ESTA LLOVIENDO");
							evento.setConfirmado(NO_CONFIRMADO);
							
							System.out.println("ANTES DE AGREGAR EVENTO");
							int idEvento = eventoDao.agregarEvento(evento);
							System.out.println("DEPUES DE AGREGAR EVENTO");
							
							System.out.println("Id REGRESADO EN KEYHOLDER = " + idEvento);
							Thread.sleep(500);
							if (idEvento != 0) {
								System.out.println("CREANDO EVENTO EN DB");
								Evento eventoGuardado = eventoDao.obtenerEvento(idEvento);
								System.out.println("EVENTO CREADO, CREANDO RESPUESTA");

								dispositivoItem.getValores().get(0).setValor(0);
								cambiarEstadoActuador(dispositivoItem, eventoGuardado);
								
								Thread.sleep(1000);
																
							}else{
								throw new DAOException("No se pudo insertar el EVENTO");
							}
														
							
						}else if (humedadTierra1a <= 65 && humedadTierra1b <= 65 && valorRiego == 0 && lluvia == 0) {
							
							System.out.println("DENTRO DE NECEISDAD DE AGUA");
							// ADVERTENCIA PARA ABRIR LA LLAVE PORQUE ESTA NECESITANDO
							Evento evento = new Evento();
							TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(TIPO_EVENTO_NOTIFICACION);							
							evento.setTipoEvento(tipoEvento);
							evento.setMensaje("SE ENCENDIO LA ELECTROVALVULA DE AGUA, PORQUE EL JARDIN NECESITA AGUA");
							evento.setConfirmado(NO_CONFIRMADO);
							
							System.out.println("ANTES DE AGREGAR EVENTO");
							int idEvento = eventoDao.agregarEvento(evento);
							System.out.println("DEPUES DE AGREGAR EVENTO");
							
							System.out.println("Id REGRESADO EN KEYHOLDER = " + idEvento);
							Thread.sleep(500);
							if (idEvento != 0) {
								System.out.println("CREANDO EVENTO EN DB");
								Evento eventoGuardado = eventoDao.obtenerEvento(idEvento);
								System.out.println("EVENTO CREADO, CREANDO RESPUESTA");

								dispositivoItem.getValores().get(0).setValor(1);
								cambiarEstadoActuador(dispositivoItem, eventoGuardado);
								
								Thread.sleep(1000);
																
							}else{
								throw new DAOException("No se pudo insertar el EVENTO");
							}
														
						}else if (humedadTierra1a > 65 && humedadTierra1b > 65 && valorRiego == 1) {
							// ADVERTENCIA PARA CERRAR LA LLAVE PORQUE ESTA ENCHARCADO
							System.out.println("DENTRO DE APAGAR LLAVE");
							Evento evento = new Evento();
							TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(TIPO_EVENTO_NOTIFICACION);							
							evento.setTipoEvento(tipoEvento);
							evento.setMensaje("LLAVE CERRADA, PORQUE ESTA ENCHARCADO");
							evento.setConfirmado(NO_CONFIRMADO);
							
							System.out.println("ANTES DE AGREGAR EVENTO");
							int idEvento = eventoDao.agregarEvento(evento);
							System.out.println("DEPUES DE AGREGAR EVENTO");
							
							System.out.println("Id REGRESADO EN KEYHOLDER = " + idEvento);
							Thread.sleep(500);
							if (idEvento != 0) {
								System.out.println("CREANDO EVENTO EN DB");
								Evento eventoGuardado = eventoDao.obtenerEvento(idEvento);
								System.out.println("EVENTO CREADO, CREANDO RESPUESTA");

								dispositivoItem.getValores().get(0).setValor(0);
								cambiarEstadoActuador(dispositivoItem, eventoGuardado);
								
								Thread.sleep(1000);
																
							}else{
								throw new DAOException("No se pudo insertar el EVENTO");
							}
							
						}
						
						
						Thread.sleep(10000);
					}
											
					
												

				} catch (Exception e) {
					System.out.println("Thread " + threadName + " interrupted.");
				}
				
				System.out.println("Termino iteracion de PING");
				System.out.println("Thread " + threadName + " exiting.");
				
			}else if (threadName.equalsIgnoreCase("Thread-Revision"))  {
				// THREAD PARA revisiones ----------------------
				
				System.out.println(" Revisando ALERTAS");
				
				try {
															
					while (true) {
						System.out.println("REVISANDO ALERTAS ......");						
						
						Evento eventoT = new Evento();
						TipoEvento tipoEventoT = tipoEventoDao.obtenerTipoEvento(TIPO_EVENTO_ALERTA);							
						eventoT.setTipoEvento(tipoEventoT);
						eventoT.setMensaje("");
						eventoT.setConfirmado(NO_CONFIRMADO);
						
						Boolean existe = eventoDao.buscarAlertas(eventoT, NO_CONFIRMADO);
						
						if (existe) {
							
							//ENVIA EVENTO DE RECORDATORIO PARA REVISAR LAS ALERTAS
							Evento evento = new Evento();
							TipoEvento tipoEvento = tipoEventoDao.obtenerTipoEvento(TIPO_EVENTO_RECORDATORIO);							
							evento.setTipoEvento(tipoEvento);
							evento.setMensaje("REVISA LAS ALERTAS, TIENES ALERTAS PENDIENTES!!!");
							evento.setConfirmado(NO_CONFIRMADO);
							
							System.out.println("ANTES DE AGREGAR EVENTO");
							int idEvento = eventoDao.agregarEvento(evento);
							System.out.println("DEPUES DE AGREGAR EVENTO");
							
							System.out.println("Id REGRESADO EN KEYHOLDER = " + idEvento);
							Thread.sleep(500);
							if (idEvento != 0) {
								System.out.println("CREANDO EVENTO EN DB");
								Evento eventoGuardado = eventoDao.obtenerEvento(idEvento);
								System.out.println("EVENTO CREADO, CREANDO RESPUESTA");

								System.out.println("--------- ENVIANDO RECORDATORIO A CLIENTE -----------------");
								Respuesta respuesta = new Respuesta();
								respuesta.setTipo("Evento");
								respuesta.setMensaje("Evento!");
								respuesta.setBody(eventoGuardado);
								template.convertAndSend("/topic/eventos", respuesta);
								
								
								Thread.sleep(1000);
																
							}else{
								throw new DAOException("No se pudo insertar el EVENTO");
							}
							
						}						
						
						
						Thread.sleep(10000);
					}
											
					
												

				} catch (Exception e) {
					System.out.println("Thread " + threadName + " interrupted.");
				}
				
				System.out.println("Termino iteracion de PING");
				System.out.println("Thread " + threadName + " exiting.");
				
				
				
			}else{
				System.out.println("No se reconoce el nombre del Thread");
			}

		}

		public void start() {
			System.out.println("Starting " + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}

	}
	
	
	
	
}