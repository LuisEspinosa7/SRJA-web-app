/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.Dispositivo;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Dispositivo;

/**
 * @author LUIS
 *
 */
public interface DispositivoDao {
	
	/**
	 * Agrega un nuevo Dispositivo
	 * @param Dispositivo Datos de Dispositivo a registrar
	 */
	public boolean agregarDispositivo(Dispositivo dispositivo);
	
	/**
	 * Modifica Dispositivo
	 * @param Dispositivo Datos de Dispositivo a modificar
	 */
	public boolean modificarDispositivo(int id, Dispositivo dispositivo);
	/**
	 * Elimina Dispositivo
	 * @param Dispositivo Datos de Dispositivo a eliminar
	 */
	public boolean eliminarDispositivo(int id);
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */	
	public JSONRespuesta listarTablaDispositivo(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca Dispositivo por id
	 * @return Dispositivo
	 */
	public Boolean buscarDispositivo(int id);
	
	/**
	 * Busca Dispositivo por nombre 
	 * @return Dispositivo
	 */
	public Boolean buscarDispositivo(Dispositivo nodo);
	
	
	/**
	 * Listar los Dispositivo
	 * @return Lista de los Dispositivo
	 */
	public List<Dispositivo> listarDispositivo();
	
	/**
	 * Obtiene el Dispositivo del usuario
	 * @return Dispositivo del usuario
	 */
	//public Dispositivo obtenerDispositivo(String username);
	
	/**
	 * Obtiene el Dispositivo del usuario
	 * @return Dispositivo del usuario
	 */
	public Dispositivo obtenerDispositivo(int id);
		
	/**
	 * Obtiene el Dispositivo del usuario
	 * @return Dispositivo del usuario
	 */
	//public Dispositivo obtenerDispositivoPorIdDeUsuario(int id);

}
