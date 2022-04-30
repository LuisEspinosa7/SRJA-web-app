/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;
import com.sevensoftware.domotica.entities.Usuario;
import com.sevensoftware.domotica.entities.JSONRespuesta;

/**
 * @author LUIS
 * @version 1.0
 */
public interface UsuarioServicio {
	
	/**
	 * Agrega un nuevo Usuario
	 * @param Usuario Datos de Usuario a registrar
	 */
	public void agregarUsuario(Usuario usuario);
	
	/**
	 * Modificar un Usuario
	 * @param Usuario Datos de Usuario a modificar
	 */
	public void  modificarUsuario(int id, Usuario usuario);
	/**
	 * Elimina un Usuario
	 * @param Usuario Datos de Usuario a eliminar
	 */
	public void  eliminarUsuario(int id);
	
	
	/**
	 * Listar las Usuarios
	 * @return Lista de los Usuarios
	 */
	public List<Usuario> listarUsuario(String criterio, String acronimo);
	/**
	 * Lista los Usuarios paginados
	 * @return lista Usuarios
	 * */
	
	public JSONRespuesta listarTablaUsuario(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	
	
}
