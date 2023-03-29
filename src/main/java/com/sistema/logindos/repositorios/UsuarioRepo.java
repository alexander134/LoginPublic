package com.sistema.logindos.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.logindos.entidades.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByEmail(String email);
	public Optional<Usuario> findByUsernameOrEmail(String username,String email);
	public Optional<Usuario> findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Boolean existsByEmail(String email);
}
