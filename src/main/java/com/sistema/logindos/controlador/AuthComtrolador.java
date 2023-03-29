package com.sistema.logindos.controlador;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.logindos.dto.LoginDTO;
import com.sistema.logindos.dto.RegistroUsuarioDTO;
import com.sistema.logindos.entidades.Rol;
import com.sistema.logindos.entidades.Usuario;
import com.sistema.logindos.repositorios.RolRepo;
import com.sistema.logindos.repositorios.UsuarioRepo;
import com.sistema.logindos.seguridad.JWTAuthResonseDTO;
import com.sistema.logindos.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthComtrolador {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private RolRepo rolRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generarToken(authentication);
		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO){
		if(usuarioRepo.existsByUsername(registroUsuarioDTO.getUsername())) {
			return new  ResponseEntity<>("Ese nombre de usuario Existente", HttpStatus.BAD_REQUEST);
		}
		
		if(usuarioRepo.existsByEmail(registroUsuarioDTO.getEmail())) {
			return new  ResponseEntity<>("Ese nombre de Email Existente", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = new Usuario();
		usuario.setUsername(registroUsuarioDTO.getUsername());
		usuario.setNombre(registroUsuarioDTO.getNombre());
		usuario.setSegundoNombre(registroUsuarioDTO.getSegundoNombre());
		usuario.setApm(registroUsuarioDTO.getApm());
		usuario.setApp(registroUsuarioDTO.getApp());
		usuario.setEmail(registroUsuarioDTO.getEmail());
		usuario.setFechaRegistro(registroUsuarioDTO.getFechaRegistro());
		usuario.setPass(passwordEncoder.encode(registroUsuarioDTO.getPassword()));
		usuario.setEmailSegundario(registroUsuarioDTO.getEmailSegundario());
		usuario.setPreguntaSecreta(registroUsuarioDTO.getPreguntaSecreta());
		usuario.setRespSecreta(registroUsuarioDTO.getRespSecreta());
		usuario.setTelefono(registroUsuarioDTO.getTelefono());
		
		Rol roles = rolRepo.findByNombre("ROLE_USER").get();
		usuario.setRoles(Collections.singleton(roles));
		usuarioRepo.save(usuario);
		
		return new ResponseEntity<>("Usuario registrado Exitosamente!", HttpStatus.OK);
	} 
	
}
