/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.TipoEspacio;

/**
 * @author LUIS
 *
 */
public interface TipoEspacioDao {
	
	/**
	 * Agrega un nuevo Tipo Nodo
	 * @param Nodo Datos de Tipo Nodo a registrar
	 */
	public boolean agregarTipoEspacio(TipoEspacio tipoNodo);
	
	/**
	 * Modifica TipoEspacio
	 * @param Nodo Datos de TipoEspacio a modificar
	 */
	public boolean modificarTipoEspacio(int id, TipoEspacio tipoNodo);
	/**
	 * Elimina TipoEspacio
	 * @param TipoEspacio Datos de TipoEspacio a eliminar
	 */
	public boolean eliminarTipoEspacio(int id);
	
	/**
	 * Lista los TipoEspacio paginados
	 * @return lista TipoEspacio
	 * */	
	public JSONRespuesta listarTablaTipoEspacio(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca TipoEspacio por id
	 * @return TipoEspacio
	 */
	public Boolean buscarTipoEspacio(int id);
	
	
	/**
	 * Listar los TipoEspacio
	 * @return Lista de los TipoEspacio
	 */
	public List<TipoEspacio> listarTipoEspacios();
	
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	//public Nodo obtenerNodo(String username);
	
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	public TipoEspacio obtenerTipoEspacio(int id);
		
	/**
	 * Obtiene el Nodo del usuario
	 * @return Nodo del usuario
	 */
	//public Nodo obtenerNodoPorIdDeUsuario(int id);

}
