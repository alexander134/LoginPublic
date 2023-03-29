package com.sistema.logindos.dto;

import java.util.Date;


public class RegistroUsuarioDTO {

	private String email;
	private String nombre;
	private String username;
	private String password;
	private String segundoNombre;
	private String apm;
	private String app;
	private Date   fechaRegistro;
	private String emailSegundario;
	private String preguntaSecreta;
	private String respSecreta;
	private String telefono;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getApm() {
		return apm;
	}

	public void setApm(String apm) {
		this.apm = apm;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEmailSegundario() {
		return emailSegundario;
	}

	public void setEmailSegundario(String emailSegundario) {
		this.emailSegundario = emailSegundario;
	}

	public String getPreguntaSecreta() {
		return preguntaSecreta;
	}

	public void setPreguntaSecreta(String preguntaSecreta) {
		this.preguntaSecreta = preguntaSecreta;
	}

	public String getRespSecreta() {
		return respSecreta;
	}

	public void setRespSecreta(String respSecreta) {
		this.respSecreta = respSecreta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
}
