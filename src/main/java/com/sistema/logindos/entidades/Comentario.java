package com.sistema.logindos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "cuerpo", nullable = false)
	private String cuerpo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publicacion_id", nullable = false)
	private Publicacion publicacion;

	public Comentario() {
		super();
	}

	public Comentario(Long id, String nombre, String email, String cuerpo, Publicacion publicacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.cuerpo = cuerpo;
		this.publicacion = publicacion;
	}

	public Comentario(String nombre, String email, String cuerpo, Publicacion publicacion) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.cuerpo = cuerpo;
		this.publicacion = publicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", cuerpo=" + cuerpo
				+ ", publicacion=" + publicacion + "]";
	}
	
	
}
