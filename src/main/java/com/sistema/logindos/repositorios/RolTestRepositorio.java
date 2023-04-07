package com.sistema.logindos.repositorios;

import org.springframework.data.repository.CrudRepository;

import com.sistema.logindos.entidades.Rol;
//import com.sistema.logindos.entidades.Roles;

public interface RolTestRepositorio extends CrudRepository<Rol, Long>{
  
	public Rol findByNombre(String nombre);
}
