/**
 * 
 */
package com.sevensoftware.domotica.threads;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevensoftware.domotica.controllers.ViviendaController;
import com.sevensoftware.domotica.dao.impl.MedicionDaoImpl;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.Message;
import com.sevensoftware.domotica.entities.TMedicion;
import com.sevensoftware.domotica.exception.DAOException;
import com.sevensoftware.domotica.services.MedicionService;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import java.sql.Connection;
import java.sql.Statement;


/**
 * @author LUIS
 *
 */

@Component
public class ThreadConexionVivienda extends Thread{
	/**
	
	@Autowired
	@Qualifier("dataSourceMediciones")
	DataSource dataSourceMediciones;
	
	private Thread t;
	private String threadName;
		

	//static final String ioserver = "http://192.168.0.11:5000/";
	static final String ioserver = "http://192.168.0.13:5000/";
	
	public ThreadConexionVivienda() {
		
	}
	
	private SimpMessagingTemplate template;
	
	@Autowired
    public ThreadConexionVivienda(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	public ThreadConexionVivienda(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}
	
	
	private static JdbcTemplate jdbcTemplateMedicionesObject;

	@Autowired
	public void setDataSource() {
		jdbcTemplateMedicionesObject = new JdbcTemplate(dataSourceMediciones);
	}
	
	
	
	public static void getNodosInfo() throws ConnectException{
        //Obteniendo Informacion de Nodos
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://192.168.0.13:5000/domotica/api/nodos";			
		
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

						jdbcTemplateMedicionesObject.batchUpdate(SQL1, new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								TMedicion medicion = mediciones.get(i);
								ps.setInt(1, medicion.getDispositivoItemId());
								ps.setInt(2, medicion.getTipoMedicion());
								ps.setDate(3, new Date(System.currentTimeMillis()));
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
    
	
		
	
	public void run() {
		System.out.println("Running " + threadName);

		
		if (threadName.equalsIgnoreCase("Thread-Ping"))  {
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
							System.out.println("CONECTADO");
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
										// Se emite la respuesta al cliente de angular
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
										// Se emite la respuesta al cliente de angular
										System.out.println("Enviando Notificacion de Emergencia!");											
										System.out.println("Enviando Respuesta al Cliente Angular");
										
										Respuesta respuesta = new Respuesta();
										respuesta.setTipo("Notificacion");
										respuesta.setMensaje("Enviando notificacion");
										
										Message message = new Message();
										message.setMessage("Emergencia");										
										respuesta.setBody(message);																	
										//template.convertAndSend("/topic/viviendaconexion", respuesta);	
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
																						
							//template.convertAndSend("/topic/viviendaconexion", respuestaN);
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
																						
							//template.convertAndSend("/topic/viviendaconexion", respuestaN);
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
																						
							//template.convertAndSend("/topic/viviendaconexion", respuestaN);
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
																						
							//template.convertAndSend("/topic/viviendaconexion", respuestaN);
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
																						
							//template.convertAndSend("/topic/viviendaconexion", respuestaN);
						}
					});
					
					socket.connect();
					
					try {
						System.out.println("ESPERANDO 5 SEGUNDOS PARA LA CONEXION");
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

				IO.Options options = new IO.Options();
				options.forceNew = true;
				options.reconnection = true;
				
				String namespace = "vivienda";
				Socket socket = IO.socket(ioserver + namespace, options);			
									
					
				socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
					@Override
					public void call(Object... args) {
						System.out.println("CONECTADO PARA RECIBIR MEDICIONES");
						
						if (socket.connected()) {
							try {
								getNodosInfo();
								
							}
							catch (ConnectException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}

					}
				}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
					@Override
					public void call(Object... args) {
						System.out.println("DISCONNECTED DE LAS MEIDICIONES");
						
					}
				});

				socket.connect();

				try {
					System.out.println("ESPERANDO 9 SEGUNDOS PARA LA CONEXION");
					Thread.sleep(9000);
				} catch (InterruptedException e) {
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
	**/

}
