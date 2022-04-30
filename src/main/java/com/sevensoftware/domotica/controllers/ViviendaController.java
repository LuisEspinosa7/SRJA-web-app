/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.HelloMessage;
import com.sevensoftware.domotica.entities.Message;
import com.sevensoftware.domotica.entities.TMedicion;
import com.sevensoftware.domotica.services.MedicionService;
import com.sevensoftware.domotica.util.Constantes;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
/**
 * @author LUIS
 *
 */
@Controller
public class ViviendaController {
	
	private static final String url = "/api/vivienda";
	//static final String ioserver = "http://192.168.0.11:5000/";
	//static final String ioserver = "http://192.168.0.14:5000/";
	static final String ioserver = Constantes.URL_VIVIENDA;
	
		
	private SimpMessagingTemplate template;
	
	
	@Autowired
    public ViviendaController(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	/**
	@MessageMapping("/topic/viviendaconexion")
	public void respuesta(Message message) throws Exception {
	   System.out.println(message.getMessage());
	   Thread.sleep(13000); // simulated delay
	   
	   	Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje("Enviando respuesta de Conexion");
		respuesta.setBody("{mensaje:'Connection Verificada'}");
		
		this.template.convertAndSend("/topic/vivienda-conexion", respuesta);   
	}
	**/
	
	
	
	/**
	@SubscribeMapping("/viviendaconexion")
    public Respuesta respuestaSuscripcion() {
        System.out.println("Respondiendo");
        
        Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Respuesta");
		respuesta.setMensaje("Enviando respuesta de suscripcion");
		respuesta.setBody("{mensaje:'Suscrito Correctamente'}");
		
		
		//String username = principal.getName();		
		//iniciarConexionInfo(username);
		
		ThreadDemo Tp = new ThreadDemo("Thread-Ping"); 
		Tp.start();
		
		return respuesta;
    }
    **/
		
	
	
	
	
	class ThreadDemo extends Thread {
		private Thread t;
		private String threadName;

		//static final String ioserver = "http://192.168.0.11:5000/";
		//static final String ioserver = "http://192.168.0.14:5000/";
		static final String ioserver = Constantes.URL_VIVIENDA;
		
		ThreadDemo(String name) {
			threadName = name;
			System.out.println("Creating " + threadName);
		}

		public void run() {
			System.out.println("Running " + threadName);

			/*
			 * Threads para cada usuario
			 */
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
								System.out.println("CONECTADO AL SOCKET CONEXION PING PONG");
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
							System.out.println("ESPERANDO 5 SEGUNDOS PARA LA CONEXION");
							Thread.sleep(9000);
						} catch (InterruptedException e) {
						}
						
						
						
						while (true) {
							
							try {
								// 3 SEGUNDO DE ESPERA
								System.out.println("3 SEGUNDO DE ESPERA");
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							System.out.println("EMITIENDO PING....");
							socket.emit("ping_event");						
						}					
					

				} catch (Exception e) {
					System.out.println("Thread " + threadName + " interrupted.");
				}
				
				System.out.println("Termino iteracion de PING");
				System.out.println("Thread " + threadName + " exiting.");

			} else {
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





