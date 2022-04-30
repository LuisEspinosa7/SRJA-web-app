/**
 * 
 */
package com.sevensoftware.domotica.entities;

/**
 * @author LUIS
 *
 */
public class Vivienda {
	
	private int id;
	private String codigo;
	private String direccion;
	private Coordenada coordenada;
	private String barrio;
	private Ciudad ciudad;
	private Departamento departamento;
	private Pais pais;
	private Estado estado;
	
		
	public Vivienda() {
		super();
	}


	public Vivienda(int id, String codigo, String direccion, Coordenada coordenada, String barrio, Ciudad ciudad,
			Departamento departamento, Pais pais, Estado estado) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.direccion = direccion;
		this.coordenada = coordenada;
		this.barrio = barrio;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.pais = pais;
		this.estado = estado;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Coordenada getCoordenada() {
		return coordenada;
	}


	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}


	public String getBarrio() {
		return barrio;
	}


	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}


	public Ciudad getCiudad() {
		return ciudad;
	}


	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}


	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
