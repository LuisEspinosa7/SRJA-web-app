/**
 * 
 */
package com.sevensoftware.domotica.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;



/**
 * @author LUIS
 *
 */
public class Usuario extends Persona{

	//private Persona persona;
	private int id;
	private String userName;
	private String password;
	private Estado estado;
	private Perfil perfil = new Perfil();
	
	
	public Usuario() {
		super();
	}
	
	public Usuario(int id, String userName, String password, Estado estado, Perfil perfil) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.estado = estado;
		this.perfil = perfil;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}
	
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		this.userName = username;
	}	

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	
}
