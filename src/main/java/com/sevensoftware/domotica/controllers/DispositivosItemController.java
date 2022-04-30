/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.sevensoftware.domotica.dao.DispositivoItemDao;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.Message;
import com.sevensoftware.domotica.services.DispositivoItemService;

import java.util.List;

import io.socket.client.Socket;

/**
 * @author LUIS
 *
 */
@Controller
public class DispositivosItemController {

	static final String ioserver = "http://localhost:5000/";

	static Socket socket;
	
	private SimpMessagingTemplate template;
	
	@Autowired
	private DispositivoItemService dispositivoItemService;
	
	@Autowired
	private DispositivoItemDao dispositivoItemDao;
	
	@Autowired
    public DispositivosItemController(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	

	@SubscribeMapping("/dispositivositem/actuadores")
    public Respuesta respuestaSuscripcion() {
        System.out.println("Respondiendo");
        System.out.println("Enviando Respuesta al Cliente Angular con DATOS DE DISPOSITIVOS ITEM");
        
        /**
        Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Datos");
		respuesta.setMensaje("Enviando dispositivos items");
		
		// PRIMER ENVIO DE DISPOSITIVOS
		List<DispositivoItem> dispositivosItemList = dispositivoItemDao.listarDispositivosItems("ACTUADOR");				
		respuesta.setBody(dispositivosItemList);																																
		template.convertAndSend("/topic/dispositivositem/actuadores", respuesta);
		**/
		
		//---------
		Respuesta respuesta2 = new Respuesta();
		respuesta2.setTipo("Respuesta");
		respuesta2.setMensaje("Enviando confirmacion de suscripcion");
		
		Message message2 = new Message();
		message2.setMessage("Suscrito Correctamente");
		respuesta2.setBody(message2);
		
		//return respuesta;
		return respuesta2;
	}	
	
	
	/**
	@MessageMapping("/dispositivositem/actuadores")
    @SendTo("/topic/dispositivositem/actuadores")
    public Respuesta dispositivo_event(DispositivoItem dispositivoItem) throws Exception {
        //Thread.sleep(1000); // simulated delay
        
		//RECUPERAR VALORES Y ENVIAR A FLASK PARA ACTUADOR
		List<Double> valores = dispositivoItem.getValores();
		System.out.println(valores);
		//GENERAR CONEXION CON FLASK
		
		
		Respuesta respuesta = new Respuesta();
		respuesta.setTipo("Datos");
		respuesta.setMensaje("Enviando dispositivos items");
        
        List<DispositivoItem> dispositivosItemList = dispositivoItemService.listarDispositivoItem();      
        respuesta.setBody(dispositivosItemList);	
        return respuesta;
    }
    **/
	
	
	
	
	
	/**
	@MessageMapping("/hello")
    @SendTo("/topic/dispositivos_info")
    public Greeting dispositivos_info(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
	**/
	
	
	/**
	
	class ThreadDemo extends Thread {
		private Thread t;
		private String threadName;

		//static final String ioserver = "http://localhost:5000/";

		ThreadDemo(String name) {
			threadName = name;
			System.out.println("Creating " + threadName);
		}

		public void run() {
			System.out.println("Running " + threadName);

			
			if (threadName.equalsIgnoreCase("Thread-Dispositivos"))  {
				System.out.println(" Thread para dispositivos");

				try {

					IO.Options options = new IO.Options();
					options.forceNew = true;
					options.reconnection = true;
					
					String namespace = "vivienda";
					//Socket socket = IO.socket(ioserver + namespace, options);
					socket = IO.socket(ioserver + namespace, options);
						
						socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
							@Override
							public void call(Object... args) {
								System.out.println("CONECTADO");
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

						}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
							@Override
							public void call(Object... args) {
								System.out.println("DISCONNECTED");
							}
						});
						
						socket.connect();
						
						try {
							System.out.println("ESPERANDO 2 SEGUNDOS PARA LA CONEXION");
							Thread.sleep(2000);
						} catch (InterruptedException e) {
						}
											
						
						while (true) {
							
							try {
								// 3 SEGUNDO DE ESPERA
								System.out.println("3 SEGUNDO DE ESPERA");
								Thread.sleep(000);
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
	**/
}
