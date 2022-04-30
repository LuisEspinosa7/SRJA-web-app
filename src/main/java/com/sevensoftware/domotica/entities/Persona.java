/**
 * 
 */
package com.sevensoftware.domotica.entities;

import java.sql.Date;

/**
 * @author LUIS
 *
 */
public class Persona {
	
	private int id;
	private String identificacion;
	private String nombre;
	private String apellido;
	private String email;	
	private Ciudad ciudad;
	private Departamento departamento;
	private Pais pais;
	private Date fechaNacimiento;
	private String telefono;
	private String direccion;		
	private TipoIdentificacion tipoIdentificacion;
	
	private Genero genero;
	
	public Persona() {
		super();
	}
	
	public Persona(int id, String identificacion, String nombre, String apellido, String email, Ciudad ciudad,
			Departamento departamento, Pais pais, Date fechaNacimiento, String telefono, String direccion,
			TipoIdentificacion tipoIdentificacion, Genero genero) {
		super();
		this.id = id;
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.ciudad = ciudad;
		this.departamento = departamento;
		this.pais = pais;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipoIdentificacion = tipoIdentificacion;
		this.genero = genero;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	

}
