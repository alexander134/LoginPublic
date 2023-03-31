package com.sistema.logindos.controlador;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.logindos.dto.LoginDTO;
import com.sistema.logindos.dto.RegistroUsuarioDTO;
import com.sistema.logindos.dto.UsuarioRespuesta;
import com.sistema.logindos.entidades.Rol;
import com.sistema.logindos.entidades.Usuario;
import com.sistema.logindos.excepciones.ResourceNotFoundException;
import com.sistema.logindos.repositorios.RolRepo;
import com.sistema.logindos.repositorios.UsuarioRepo;
import com.sistema.logindos.seguridad.JWTAuthResonseDTO;
import com.sistema.logindos.seguridad.JwtTokenProvider;
//import com.sistema.logindos.servicio.UsuarioServicio;
import com.sistema.logindos.utilerias.AppConstantes;

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
	
	@Autowired
	private ModelMapper modelmapper;
	
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
		usuario.setId(registroUsuarioDTO.getId());
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
	
	@GetMapping
	public UsuarioRespuesta UsuariosRespuesta(
			@RequestParam(value = "pageN",defaultValue = AppConstantes.NUMERO_PAGINA_DEFECTO,required = false) int numeroPagina,
			@RequestParam(value = "cantReg",defaultValue = AppConstantes.MEDIDA_PAGINA_DEFECTO,required = false)int cantidadRegistros,
			@RequestParam(value = "sortById",defaultValue = AppConstantes.ORDER_CAMPO_DEFECTO,required = false)String orderPorCampo,
			@RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDER_DIRECCION_DEFECTO,required = false)String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
				Sort.by(orderPorCampo).ascending()
			:
				Sort.by(orderPorCampo).descending();
			Pageable pageable = PageRequest.of(numeroPagina, cantidadRegistros,sort);
		Page<Usuario> usuarios = usuarioRepo.findAll(pageable);
		List<Usuario> listaUsuarios = usuarios.getContent();
		List<RegistroUsuarioDTO> contenido= listaUsuarios.stream().map(usuario ->mapENTITYtoDTO(usuario)).collect(Collectors.toList());
		
		UsuarioRespuesta usuariosRes = new UsuarioRespuesta();
		usuariosRes.setContenido(contenido);
		usuariosRes.setNumeroPagina(usuarios.getNumber());
		usuariosRes.setMedidaPagina(usuarios.getSize());
		usuariosRes.setTotalRegistros(usuarios.getTotalElements());
		usuariosRes.setTotalPaginas(usuarios.getTotalPages());
		usuariosRes.setUltimaPagina(usuarios.isLast());
		return usuariosRes;
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<RegistroUsuarioDTO> obtenerUsuarioPorId(@PathVariable(name = "id")Long id){
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario", "Id",id) );
		
		return ResponseEntity.ok(mapENTITYtoDTO(usuario));
	}
	
	@PutMapping("usuario/upd/{id}")
	public ResponseEntity<RegistroUsuarioDTO> actualizarUsuario(@Valid @RequestBody RegistroUsuarioDTO usuarioDTO,@PathVariable(name = "id")Long id){
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario", "Id",id) );
		
		usuario.setUsername(usuarioDTO.getUsername());
		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setSegundoNombre(usuarioDTO.getSegundoNombre());
		usuario.setApm(usuarioDTO.getApm());
		usuario.setApp(usuarioDTO.getApp());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
		usuario.setPass(passwordEncoder.encode(usuarioDTO.getPassword()));
		usuario.setEmailSegundario(usuarioDTO.getEmailSegundario());
		usuario.setPreguntaSecreta(usuarioDTO.getPreguntaSecreta());
		usuario.setRespSecreta(usuarioDTO.getRespSecreta());
		usuario.setTelefono(usuarioDTO.getTelefono());
		//Rol roles = rolRepo.findByNombre("ROLE_USER").get();
		//usuario.setRoles(Collections.singleton(roles));

		Usuario nuevoActualizado = usuarioRepo.save(usuario);
		
		return new ResponseEntity<>(mapENTITYtoDTO(nuevoActualizado),HttpStatus.OK);
	}
	
	
	@DeleteMapping("usuario/delete/{id}")
	public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable(name = "id")Long id){
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario", "Id",id));
		usuario.getRoles().clear();
		usuarioRepo.delete(usuario);
		
		return new ResponseEntity<>("Usuario con id="+id+" Eliminado" ,HttpStatus.OK);
	}
	
	
	private RegistroUsuarioDTO mapENTITYtoDTO(Usuario usuario) {
		RegistroUsuarioDTO usuarioDTO = modelmapper.map(usuario, RegistroUsuarioDTO.class);
		return usuarioDTO;
	}
	
	private Usuario mapDTOtoENTITY(RegistroUsuarioDTO usuarioDTO) {
		Usuario usuario = modelmapper.map(usuarioDTO, Usuario.class);
		return usuario;
	}
	
}
