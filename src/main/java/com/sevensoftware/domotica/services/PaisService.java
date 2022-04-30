/**
 * 
 */
package com.sevensoftware.domotica.services;

import java.util.List;
import com.sevensoftware.domotica.entities.JSONRespuesta;
import com.sevensoftware.domotica.entities.Pais;
import com.sevensoftware.domotica.entities.Usuario;

/**
 * @author LUIS
 *
 */
public interface PaisService {	
	
	/**
	 * Agrega un nuevo Pais
	 * @param Pais Datos de Pais a registrar
	 */
	public String agregarPais(Pais Pais );
	
	/**
	 * Modificar un Pais
	 * @param Pais Datos de Pais a modificar
	 */
	public String  modificarPais(int id, Pais pais);
	/**
	 * Elimina un Pais
	 * @param Pais Datos de Pais a eliminar
	 */
	public String  eliminarPais(int id);
		
	/**
	 * Listar los Pais
	 * @return Lista de los Pais
	 */
	public List<Pais> listarPais();
		
}
