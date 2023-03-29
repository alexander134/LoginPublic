package com.sistema.logindos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.logindos.entidades.Publicacion;

import java.util.List;

public interface PublicacionRepo extends JpaRepository<Publicacion, Long>{
	//public List<Publicacion> findByUsuarioId(Long usuarioId);
}
