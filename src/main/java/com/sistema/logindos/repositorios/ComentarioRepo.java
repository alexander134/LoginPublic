package com.sistema.logindos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.logindos.entidades.Comentario;

public interface ComentarioRepo extends JpaRepository<Comentario, Long>{

public List<Comentario> findByPublicacionId(Long publicacionId);
	
	//public List<Comentario> findByUsuarioId(Long usuarioId);
}
