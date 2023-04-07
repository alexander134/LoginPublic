package com.sistema.logindos.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.sistema.logindos.entidades.Usuario;

public interface UsuarioTestRepositorio  extends CrudRepository<Usuario, Long>{
	  
		public Usuario findByNombre(String nombre);
		Usuario findTopByOrderByIdDesc();

}
