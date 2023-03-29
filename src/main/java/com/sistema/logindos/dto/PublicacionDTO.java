package com.sistema.logindos.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sistema.logindos.entidades.Comentario;

public class PublicacionDTO {

private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "El titulo de la publicación debe tener al menos dos caracateres.")
	private String titulo;
	
	@NotEmpty
	@Size(min = 10, message = "La desccripción de la publicación debe tener al menos 10 caracateres.")
	private String descripcion;
	
	@NotEmpty
	private String contenido;
	private Set<Comentario> comentarios;
	
	
	public PublicacionDTO() {
		super();
	}


	public PublicacionDTO(Long id,
			@NotEmpty @Size(min = 2, message = "El titulo de la publicación debe tener al menos dos caracateres.") String titulo,
			@NotEmpty @Size(min = 10, message = "La desccripción de la publicación debe tener al menos 10 caracateres.") String descripcion,
			@NotEmpty String contenido, Set<Comentario> comentarios) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
		this.comentarios = comentarios;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getContenido() {
		return contenido;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public Set<Comentario> getComentarios() {
		return comentarios;
	}


	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
}
