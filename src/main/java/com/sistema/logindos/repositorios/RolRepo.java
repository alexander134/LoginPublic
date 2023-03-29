package com.sistema.logindos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.logindos.entidades.Rol;

public interface RolRepo extends JpaRepository<Rol, Long>{

	public Optional<Rol> findByNombre(String nombre);
}
