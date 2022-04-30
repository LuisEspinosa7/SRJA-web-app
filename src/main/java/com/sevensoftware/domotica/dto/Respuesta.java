package com.sevensoftware.domotica.dto;

public class Respuesta {
	
	public static final String OPERACION_EJECUTADA_EXITOSAMENTE = "La operacion fue realizada exitosamente.";
	public static final String ERROR_EJECUTAR_OPERACION = "Ocurrio un error en la ejecucion de la operacion, comuniquese con mantenimiento.";		
	
	public static final String INSCRIPCION_USUARIO_EXITOSA = "La inscripcion se realizo correctamente.";
	public static final String USUARIO_EXISTE_ERROR = "El usuario ya existe.";
	public static final String INSCRIPCION_USUARIO_ERROR = "No se pudo inscribir el usuario.";
	
	
	public static final String MODIFICACION_USUARIO_EXITOSA = "La modificacion se realizo correctamente.";
	public static final String USUARIO_NO_EXISTE_ERROR = "El usuario no existe.";
	public static final String MODIFICACION_USUARIO_ERROR = "No se pudo modificar el usuario.";
	
	public static final String ELIMINACION_USUARIO_EXITOSA = "La eliminacion se realizo correctamente.";
	public static final String ELIMINACION_USUARIO_ERROR = "No se pudo eliminar el usuario.";
	
	public static final String VALOR_NULL_ERROR = "No se permiten valores nulos.";
	
	public static final String VALOR_ID_NULL_ERROR = "El id Nulo no esta permitido.";
	
	
	public static final String TIPO_RESPUESTA = "Respuesta.";
	public static final String TIPO_ALERTA = "Alerta.";
	public static final String TIPO_DATOS = "Datos.";
	

	boolean exito;
	private String tipo;
	private String mensaje;
	private Object body;

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}
	
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}

	

}