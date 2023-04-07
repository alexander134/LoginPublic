package com.sistema.logindos.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.sistema.logindos.entidades.Publicacion;

public interface PublicacionTestRepositorio extends CrudRepository<Publicacion, Long>{
	
	public Publicacion findByTitulo(String titulo);
	Publicacion findTopByOrderByIdDesc();

}
