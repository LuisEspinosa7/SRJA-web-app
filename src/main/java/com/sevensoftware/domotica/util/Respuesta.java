package com.sevensoftware.domotica.util;

public class Respuesta {

	public static final int EJECUCION_OK = 1;
	public static final int EJECUCION_ERROR = 0;
	
	public static final int USUARIO_EXISTE = 2;	
	public static final int INSCRIPCION_USUARIO_OK = 3;
	
	public static final String OPERACION_EJECUTADA_EXITOSAMENTE = "La operacion fue realizada exitosamente.";
	public static final String ERROR_EJECUTAR_OPERACION = "Ocurrio un error en la ejecucion de la operacion.";		
	
	public static final String INSCRIPCION_USUARIO_EXITOSA = "La inscripcion se realizo correctamente.";
	public static final String USUARIO_EXISTE_ERROR = "El usuario ya existe.";	
	
	boolean exito;
	String mensaje;
	Object body;

	public Respuesta() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Respuesta(String string) {
		// TODO Auto-generated constructor stub
	}		

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
