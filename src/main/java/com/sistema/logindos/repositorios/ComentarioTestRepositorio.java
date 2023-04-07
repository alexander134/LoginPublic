package com.sistema.logindos.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.sistema.logindos.entidades.Comentario;

public interface ComentarioTestRepositorio  extends CrudRepository<Comentario, Long>{
	  
		public Comentario findByNombre(String nombre);
		Comentario findTopByOrderByIdDesc();

}
