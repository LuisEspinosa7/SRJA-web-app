/**
 * 
 */
package com.sevensoftware.domotica.controllers;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.sevensoftware.domotica.dto.Respuesta;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.lang.InterruptedException;

/**
 * @author LUIS
 *
 */

class ThreadDemo extends Thread {
	private Thread t;
	private String threadName;

	static final String ioserver = "http://192.168.0.11:5000/";

	ThreadDemo(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);

		/*
		 * Threads para cada usuario
		 */
		if (threadName.equalsIgnoreCase("Thread-1")) {
			System.out.println("Iniciando Conexion Usuario 1");

			try {

				usuario1("Usuario1");

			} catch (Exception e) {
				System.out.println("Thread " + threadName + " interrupted.");
			}
			System.out.println("Thread " + threadName + " exiting.");

		} else if (threadName.equalsIgnoreCase("Thread-2")) {
			System.out.println("Iniciando Conexion Usuario 2");

			try {

				usuario2("Usuario2");

			} catch (Exception e) {
				System.out.println("Thread " + threadName + " interrupted.");
			}
			System.out.println("Thread " + threadName + " exiting.");

		} else if (threadName.equalsIgnoreCase("Thread-4")) {
			System.out.println(" Verificando Conexion constante");

			try {

				Socket socket = IO.socket(ioserver + "vivienda");
				socket.emit("verify_connection");

				socket.on("conexion_verified", new Emitter.Listener() {

					@Override
					public void call(Object... args) {
						String s = args[1].toString();
						String message;

						try {
							JSONObject data = new JSONObject(s);
							message = data.getJSONObject("data").getString("message");
							System.out.println("Message From Server =  " + message);

							if (!message.equalsIgnoreCase("Connection Alive")) {
								System.out.println("No hay Conexion con Flask Raspberry Pi");
							} else {
								System.out.println("Conexion Alive ");
								// Se emite la respuesta al cliente de angular
								// socket.emit("add user");

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

				});

			} catch (Exception e) {
				System.out.println("Thread " + threadName + " interrupted.");
			}
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

	// ------ PROCEDIMIENTO PARA USUARIO 1
	static void usuario1(final String username) {
		System.out.println("--------- Iniciando Proceso Usuario  N° 1 --------");
		try {

			IO.Options options = new IO.Options();
			options.forceNew = true;
			options.reconnection = false;

			String namespace = "vivienda";

			Socket socket = IO.socket(ioserver + namespace, options);

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connecting...");
				}
			}).on("conexion established", new Emitter.Listener() {

				@Override
				public void call(Object... args) {
					// System.out.println(args[1].toString());
					String s = args[1].toString();
					// System.out.println("=> " + s);
					String message;
					try {
						JSONObject data = new JSONObject(s);
						// System.out.println(data);
						message = data.getJSONObject("data").getString("message");
						System.out.println("Message From Server =  " + message);

						if (!message.equalsIgnoreCase("Connected")) {
							System.out.println("No Fue posible establecer comunicacion");
						} else {
							System.out.println("Adicionando Usuario:  " + username + "......");
							socket.emit("add user", username);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on("user joined", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					String s = args[0].toString();
					String message;
					String username;
					String numUsers;

					try {
						JSONObject data = new JSONObject(s);
						message = data.getString("message");
						System.out.println("Message From Server =  " + message);
						username = data.getString("username");
						System.out.println("User From Server =  " + username);
						numUsers = data.getString("numUsers");
						System.out.println("Numero de Usuarios =  " + numUsers);

						if (!message.equalsIgnoreCase("user joined")) {
							System.out.println("No Fue posible Adicionar el usuario");
						} else {
							System.out.println("Usuario " + username + " Adicionado Correctamente. ");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect timeout");
				}
			}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect error");
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("disconnect");
				}
			});

			socket.connect();
			try {
				// 60 SEGUNDOS DE PRUEBA
				Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
			socket.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----- PROCEDIMIENTO USUARIO 2
	static void usuario2(final String username) {
		System.out.println("--------- Iniciando Proceso Usuario  N° 2 --------");
		try {

			IO.Options options = new IO.Options();
			options.forceNew = true;
			options.reconnection = false;

			String namespace = "vivienda";

			Socket socket = IO.socket(ioserver + namespace, options);

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connecting...");
				}
			}).on("conexion established", new Emitter.Listener() {

				@Override
				public void call(Object... args) {
					// System.out.println(args[1].toString());
					String s = args[1].toString();
					// System.out.println("=> " + s);
					String message;
					try {
						JSONObject data = new JSONObject(s);
						// System.out.println(data);
						message = data.getJSONObject("data").getString("message");
						System.out.println("Message From Server =  " + message);

						if (!message.equalsIgnoreCase("Connected")) {
							System.out.println("No Fue posible establecer comunicacion");
						} else {
							System.out.println("Adicionando Usuario:  " + username + "......");
							socket.emit("add user", username);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on("user joined", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					String s = args[0].toString();
					String message;
					String username;
					String numUsers;

					try {
						JSONObject data = new JSONObject(s);
						message = data.getString("message");
						System.out.println("Message From Server =  " + message);
						username = data.getString("username");
						System.out.println("User From Server =  " + username);
						numUsers = data.getString("numUsers");
						System.out.println("Numero de Usuarios =  " + numUsers);

						if (!message.equalsIgnoreCase("user joined")) {
							System.out.println("No Fue posible Adicionar el usuario");
						} else {
							System.out.println("Usuario " + username + " Adicionado Correctamente. ");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect timeout");
				}
			}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect error");
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("disconnect");
				}
			});

			socket.connect();
			try {
				// 60 SEGUNDOS DE PRUEBA
				Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
			socket.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

public class SocketClient {

	static final String ioserver = "http://192.168.0.13:5000/";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws URISyntaxException {

		/**
		 * http://www.programcreek.com/java-api-examples/index.php?api=com.github.nkzawa.socketio.client.Socket
		 * http://nkzawa.tumblr.com/post/46850605422/connecting-to-a-socketio-server-from-android
		 * https://github.com/socketio/socket.io-client-java
		 * https://socket.io/blog/native-socket-io-and-android/
		 * 
		 * 
		 */

		/**
		 * ThreadDemo T1 = new ThreadDemo("Thread-1"); T1.start();
		 * 
		 * try { // 12 SEGUNDOS DE PRUEBA Thread.sleep(9000); } catch
		 * (InterruptedException e) { }
		 * 
		 * ThreadDemo T2 = new ThreadDemo("Thread-2"); T2.start();
		 * 
		 * try { // 12 SEGUNDOS DE PRUEBA Thread.sleep(9000); } catch
		 * (InterruptedException e) { }
		 * 
		 **/

		/**
		 * ThreadDemo T3 = new ThreadDemo("Thread-3"); T3.start();
		 **/

		/**
		 * try { // 12 SEGUNDOS DE PRUEBA Thread.sleep(25000); } catch
		 * (InterruptedException e) { }
		 **/
		usuario3("Usuario3");

		// usuario4("Usuario4");
	}

	// ------ PROCEDIMIENTO PARA USUARIO 3
	static void usuario3(final String username) {
		System.out.println("--------- Iniciando Proceso Usuario  N° 3 --------");
		try {

			// IO.Options options = new IO.Options();
			// options.forceNew = true;
			// options.reconnection = false;

			String namespace = "vivienda";

			// Socket socket = IO.socket(ioserver + namespace, options);
			Socket socket = IO.socket(ioserver + namespace);

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connecting...");
				}
			})
			
			
			.on("conexion established", new Emitter.Listener() {

				@Override
				public void call(Object... args) {
					// System.out.println(args[1].toString());
					String s = args[1].toString();
					// System.out.println("=> " + s);
					String message;
					try {
						JSONObject data = new JSONObject(s);
						// System.out.println(data);
						message = data.getJSONObject("data").getString("message");
						System.out.println("Message From Server =  " + message);

						if (!message.equalsIgnoreCase("Connected")) {
							System.out.println("No Fue posible establecer comunicacion");
						} else {
							System.out.println(" Comunicacion Establecida! ");
							//System.out.println("Adicionando Usuario:  " + username + "......");
							//socket.emit("add user", username);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			})
			/**
			.on("user joined", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					String s = args[0].toString();
					String message;
					String username;
					String numUsers;

					try {
						JSONObject data = new JSONObject(s);
						message = data.getString("message");
						System.out.println("Message From Server =  " + message);
						username = data.getString("username");
						System.out.println("User From Server =  " + username);
						numUsers = data.getString("numUsers");
						System.out.println("Numero de Usuarios =  " + numUsers);

						if (!message.equalsIgnoreCase("user joined")) {
							System.out.println("No Fue posible Adicionar el usuario");
						} else {
							System.out.println("Usuario " + username + " Adicionado Correctamente. ");
							System.out.println(
									"Realizando Peticion de Thread en Background del usuario:  " + username + "......");
							// AQUI
							System.out.println("ESPERANDO PARA INICIAR THREAD");
							try {
								// 60 SEGUNDOS DE PRUEBA
								Thread.sleep(5000);
							} catch (InterruptedException e) {
							}

							socket.emit("thread devices");

							System.out.println("ESPERANDO RESPUESTA THREAD");
							try {
								// 5 SEGUNDOS DE PRUEBA
								Thread.sleep(2000);
							} catch (InterruptedException e) {
							}

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on("thread created", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					String s = args[0].toString();
					String message;
					String username;

					try {
						JSONObject data = new JSONObject(s);
						message = data.getString("message");
						System.out.println("Message From Server =  " + message);
						username = data.getString("username");
						System.out.println("User From Server =  " + username);

						if (!message.equalsIgnoreCase("Thread Created")) {
							System.out.println("No Fue posible Crear el Thread");
						} else {
							System.out.println("Thread Creado Correctamente. ");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on("devices_info", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					System.out.println("LLEGARON LOS DATOS DE LOS DISPOSITIVOS");

					String s = args[0].toString();
					String bombillo1;
					String bombillo2;
					String time;

					try {
						JSONObject data = new JSONObject(s);
						bombillo1 = data.getString("bombillo1");
						System.out.println("Bombillo 1 =  " + bombillo1);
						bombillo2 = data.getString("bombillo2");
						System.out.println("Bombillo 2 =  " + bombillo2);
						time = data.getString("time");
						System.out.println("Time =  " + time);

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}).on("conexion_verified", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("Conexion Verificada");
				}
			})
			**/
			
			/**
			.on("nodes_info", new Emitter.Listener() {

				@Override
				public void call(Object... args) {

					System.out.println("LLEGARON LOS DATOS DE LOS DISPOSITIVOS");

					String s = args[0].toString();
					System.out.println(s);
					String humedad;
					
					String temperatura;
					String lluvia;
					String litrosPorMinuto;
					String humedadTierra1;
					String humedadTierra2;
					String temperaturaTierra;
					
					try {
						JSONObject data = new JSONObject(s);
						humedad = data.getString("humedad");
						System.out.println("Humedad =  " + humedad);
						
						bombillo2 = data.getString("bombillo2");
						System.out.println("Bombillo 2 =  " + bombillo2);
						time = data.getString("time");
						System.out.println("Time =  " + time);
						

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			})
			**/
			
			
			
			.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect timeout");
				}
			}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("connect error");
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					System.out.println("disconnect");
				}
			});

			socket.connect();
			
			try {
				// 60 SEGUNDOS DE PRUEBA
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
			
			//socket.emit("thread nodes");
			
			int numero = 10;
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://192.168.0.13:5000/domotica/api/nodos";			
			
			while (numero > 0) {
				
				try {
					// 1 SEGUNDO DE PRUEBA
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
				
				//Peticion datos de nodo riego 1
				Respuesta respuesta = restTemplate.getForObject(url + "/4", Respuesta.class);
				//assertThat(foo.getName(), notNullValue());
				//assertThat(foo.getId(), is(1L));
				System.out.println(respuesta.getBody());
				numero--; 
			}
			
			
			
			/**
			int numero = 10;
			
			while (numero > 0) {
				
				try {
					// 1 SEGUNDO DE PRUEBA
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				
				socket.emit("verify_connection");
				numero--; 
			}
			**/
			
			
			try {
				// 60 SEGUNDOS DE PRUEBA
				Thread.sleep(120000);
			} catch (InterruptedException e) {
			}
			socket.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
