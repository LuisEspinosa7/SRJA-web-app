/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;

import com.sevensoftware.domotica.entities.DispositivoItem;
import com.sevensoftware.domotica.entities.JSONRespuesta;

/**
 * @author LUIS
 *
 */
public interface DispositivoItemService {
	
	/**
	 * Agrega un nuevo DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a registrar
	 */
	public void agregarDispositivoItem(DispositivoItem DispositivoItem );
	
	/**
	 * Modificar un DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a modificar
	 */
	public void  modificarDispositivoItem(int id, DispositivoItem dispositivoItem);
	
	/**
	 * Modificar un DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a modificar
	 */
	public void  modificarDispositivoItemActuador(int id, DispositivoItem dispositivoItem);
	
	
	/**
	 * Elimina un DispositivoItem
	 * @param DispositivoItem Datos de DispositivoItem a eliminar
	 */
	public void  eliminarDispositivoItem(int id);
		
	/**
	 * Listar los DispositivoItem
	 * @return Lista de los DispositivoItem
	 */
	public List<DispositivoItem> listarDispositivoItem();
	
	
	/**
	 * Lista los Dispositivo paginados
	 * @return lista Dispositivo
	 * */
	
	public JSONRespuesta listarTablaDispositivosItem(String search, int start, int length, int draw, int posicion, String direccion);


}
