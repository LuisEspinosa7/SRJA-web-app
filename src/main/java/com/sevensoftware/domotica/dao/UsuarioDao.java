/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.sevensoftware.domotica.entities.Perfil;
import com.sevensoftware.domotica.entities.Usuario;
import com.sevensoftware.domotica.entities.JSONRespuesta;

/**
 * Interface de acceso a los datos para los usuarios
 * @author LUIS
 * @version 1.0
 */
//@Repository("usuarioDao")
public interface UsuarioDao {
	
	/**
	 * Agrega un nuevo usuario
	 * @param Usuario Datos de Usuario a registrar
	 */
	public boolean agregarUsuario(Usuario usuario);
	
	/**
	 * Modifica usuario
	 * @param Usuario Datos de Usuario a modificar
	 */
	public boolean modificarUsuario(int id, Usuario usuario);
	/**
	 * Elimina Usuario
	 * @param Usuario Datos de Usuario a eliminar
	 */
	public boolean eliminarUsuario(int id);
		
	/**
	 * Listar los Usuarios
	 * @return Lista de los Usuarios
	 */
	//public  listarUsuario(String criterio, String acronimo);
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */
	
	public JSONRespuesta listarTablaUsuario(String search, int start, int length, int draw, int posicion, String direccion);
	
	/**
	 * Metodo para consultar un usuario seg�n su id
	 * @param id parametro para filtrar la usuario
	 * @return objeto usuario 
	 */
	public Boolean buscarUsuario(int id);
	
	/**
	 * Obtener Usuarioio seg�n su id
	 * @param id parametro para buscar el usuario
	 * @return objeto usuario 
	 */
	public Usuario obtenerUsuarioPorId(int id);
	
	
	/**
	 * Buscar un usuario por su nombre
	 * 
	 * @param nombre - nombre a buscar
	 * @return El usuario cuyo nombre es igual al parametro dado, si no existe
	 *         null
	 */
	public Usuario buscarPorNombre(String nombre);
	
	/**
	 * Buscar Roles del usuario por su nombre
	 * 
	 * @param nombre - nombre a buscar
	 * @return roles del usuario cuyo nombre es igual al parametro dado, si no existe
	 *         null
	 */
	//public Set<Rol> buscarRolesPorUsername(String nombre);
	/**
	 * Buscar un usuario por su email
	 * 
	 * @param email - email a buscar
	 * @return El usuario cuyo email es igual al parametro dado, si no existe
	 *         null
	 */
	public Boolean buscarPorEmail(String email);
	
	/**
	 * Buscar un usuario por su identificacion
	 * 
	 * @param identificacion - identificacion a buscar
	 * @return Booleano si existe cuyo identificacion es igual al parametro dado, si no existe
	 *         null
	 */
	public Boolean buscarPorIdentificacion(String identificacion);	
	
	/**
	 * Buscar un usuario por su Username
	 * 
	 * @param Username - Username a buscar
	 * @return Booleano si existe cuyo email es igual al parametro dado, si no existe
	 *         null
	 */
	public Boolean buscarPorUsername(String username);		
	
}