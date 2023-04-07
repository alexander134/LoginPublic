package com.sistema.logindos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sistema.logindos.entidades.Rol;
import com.sistema.logindos.entidades.Usuario;
import com.sistema.logindos.repositorios.RolRepo;
import com.sistema.logindos.repositorios.UsuarioTestRepositorio;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioTests {
	
	@Autowired
	private  UsuarioTestRepositorio usuarioTestRepositorio;
	
	@Autowired
	private RolRepo rolRepo;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarUsuario() {
		
		Rol roles = rolRepo.findByNombre("ROLE_USER").get();
		Usuario usuario = new Usuario("naruInventado","Inventado1","SegNombrInventado","APMInventado","APPInventado",
				"inventado@inventado.com",new Date(),"inventado","2inventado@2inventado.com",
				"pregInventada","RespInventada","1223454848",Collections.singleton(roles));
		try {
			Usuario usuarioGuardardos= usuarioTestRepositorio.save(usuario);
			assertNotNull(usuarioGuardardos);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void testBurcarNombre() {
		String nombre = "Inventado1";
		Usuario usuario = usuarioTestRepositorio.findByNombre(nombre);
		assertThat(usuario.getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testBurcarNombreNoExist() {
		String nombre = "Inventado2";
		Usuario usuario = usuarioTestRepositorio.findByNombre(nombre);
		assertNull(usuario);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testUPDUsuario() {
		Rol roles = rolRepo.findTopByOrderByIdDesc();
		String nombre = "Inventado_UPD";
		Usuario usuario = new Usuario("naruInventado","Inventado_UPD","SegNombrInventado","APMInventado","APPInventado",
				"inventado@inventado22.com",new Date(),"inventado","2inventado@2inventado22.com",
				"pregInventada22","RespInventada222","1111111111",Collections.singleton(roles));
		
		long idUsuario= usuarioTestRepositorio.findTopByOrderByIdDesc().getId();
		usuario.setId(idUsuario);
		usuarioTestRepositorio.save(usuario);
		Usuario usuarioActualizado = usuarioTestRepositorio.findByNombre(nombre);
		assertThat(usuarioActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(5)
	public void testlistarUsuario() {
		List<Usuario> usuario = (List<Usuario>) usuarioTestRepositorio.findAll();
		for (Usuario usuario2 : usuario) {
			System.out.println(usuario2);
		}
		assertThat(usuario).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeleteUsuario() {
		long id = usuarioTestRepositorio.findTopByOrderByIdDesc().getId();
		boolean esExistenteAnteDeEliminar = usuarioTestRepositorio.findById(id).isPresent();
		usuarioTestRepositorio.deleteById(id);
		
		boolean noExistenteDespuesDeEliminar = usuarioTestRepositorio.findById(id).isPresent();
		assertTrue(esExistenteAnteDeEliminar);
		assertFalse(noExistenteDespuesDeEliminar);
	}
	
}
