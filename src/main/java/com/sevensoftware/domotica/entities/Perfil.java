/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class Perfil {
	
	private int id;
	private String perfil;
	
	public Perfil() {
		super();
	}
	
	public Perfil(int id, String perfil) {
		super();
		this.id = id;
		this.perfil = perfil;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}	
	

}
