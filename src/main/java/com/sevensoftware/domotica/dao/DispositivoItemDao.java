/**
 * 
 */
package com.sevensoftware.domotica.dao;

import java.util.List;

import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.LiveData;

/**
 * @author LUIS
 *
 */
public interface DispositivoItemDao {
	
	
	/**
	 * Agrega un nuevo DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a registrar
	 */
	public boolean agregarDispositivoItem(DispositivoItem dispositivoItem);
	
	/**
	 * Modifica DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a modificar
	 */
	public boolean modificarDispositivoItem(int id, DispositivoItem dispositivoItem);
	/**
	 * Elimina DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a eliminar
	 */
	public boolean eliminarDispositivoItem(int id);
	
	/**
	 * Modifica DispositivoItem ACTUADOR
	 * @param DispositivoItem ACTUADOR VALORES Datos de DispositivoItem a modificar
	 */
	public boolean modificarDispositivoItemValores(int id, DispositivoItem dispositivoItem);
	
	
	/**
	 * Lista los Usuarios paginados
	 * @return lista usuarios
	 * */	
	public JSONRespuesta listarTablaDispositivoItem(String search, int start, int length, int draw, int posicion, String direccion);
	
	
	/**
	 * Busca DispositivoItem por id
	 * @return DispositivoItem
	 */
	public Boolean buscarDispositivoItem(int id);
	
	/**
	 * Busca DispositivoItem por id
	 * @return DispositivoItem
	 */
	public Boolean buscarDispositivoItem(DispositivoItem dispositivoItem);
	
	
	/**
	 * Busca DispositivoItem por id
	 * @return DispositivoItem
	 */
	public Boolean buscarDispositivoItem(int id, DispositivoItem dispositivoItem);
	
	/**
	 * Listar los DispositivoItem
	 * @return Lista de los DispositivoItem
	 */
	public List<DispositivoItem> listarDispositivoItem();
	
	
	/**
	 * Obtener ultimo datos del sensor (DispositivoItem)
	 * @return Obtener ultimo datos del sensor
	 */
	public LiveData getLiveDataDispositivoItem(int dispositivoItemId, int tipoValorId);
	
	
	/**
	 * Listar los DispositivoItem
	 * @return Lista de los DispositivoItem
	 */
	public List<DispositivoItem> listarDispositivosItems(String tipoDispositivo, int espacioId);
	
	/**
	 * Obtiene el DispositivoItem del usuario
	 * @return DispositivoItem del usuario
	 */
	//public DispositivoItem obtenerDispositivoItem(String username);
	
	/**
	 * Obtiene el DispositivoItem del usuario
	 * @return DispositivoItem del usuario
	 */
	public DispositivoItem obtenerDispositivoItem(int id);
		
	/**
	 * Obtiene el DispositivoItem del usuario
	 * @return DispositivoItem del usuario
	 */
	//public DispositivoItem obtenerDispositivoItemPorIdDeUsuario(int id);


}
