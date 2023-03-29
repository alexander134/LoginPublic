package com.sistema.logindos.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),@UniqueConstraint(columnNames = { "email" }) })
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "segundoNombre", nullable = false)
	private String segundoNombre;
	@Column(name = "apm", nullable = false)
	private String apm;
	@Column(name = "app", nullable = false)
	private String app;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "fechaRegistro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "pass", nullable = false)
	private String pass;
	@Column(name = "emailSegundario", nullable = false)
	private String emailSegundario;
	@Column(name = "preguntaSecreta", nullable = false)
	private String preguntaSecreta;
	@Column(name = "respSecreta", nullable = false)
	private String respSecreta;
	@Column(name = "telefono", nullable = false)
	private String telefono;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id"))
	private Set<Rol> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	
	
}
