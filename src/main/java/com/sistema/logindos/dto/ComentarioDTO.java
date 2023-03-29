package com.sistema.logindos.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ComentarioDTO {
	private Long id;
	@NotEmpty(message = "el nombre no puede estar vacio")
	private String nombre;
	@NotEmpty(message = "el email no puede estar vacio")
	@Email
	private String email;
	@NotEmpty(message = "este campo no puede estar vacio")
	private String cuerpo;
	public ComentarioDTO(Long id, @NotEmpty(message = "el nombre no puede estar vacio") String nombre,
			@NotEmpty(message = "el email no puede estar vacio") @Email String email,
			@NotEmpty(message = "este campo no puede estar vacio") String cuerpo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.cuerpo = cuerpo;
	}
	public ComentarioDTO() {
		super();
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
	
	
}
