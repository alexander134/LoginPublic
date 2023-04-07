package com.sistema.logindos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sistema.logindos.entidades.Rol;
import com.sistema.logindos.repositorios.RolTestRepositorio;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class RolTests {
	
	@Autowired
	private RolTestRepositorio rolTestRepositorio;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarRol() {
		Rol roles = new Rol("ROLE_ADMIN4");
		try {
			Rol rolesGuardardos= rolTestRepositorio.save(roles);
			assertNotNull(rolesGuardardos);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void testBurcarNombre() {
		String nombre = "ROLE_ADMIN4";
		Rol roles = rolTestRepositorio.findByNombre(nombre);
		assertThat(roles.getNombre()).isEqualTo(nombre);
		
	}
	
	@Test
	@Order(3)
	public void testBurcarNombreNoExist() {
		String nombre = "ROLE_ADMIN3333";
		Rol roles = rolTestRepositorio.findByNombre(nombre);
		assertNull(roles);
		
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testUPDRol() {
		String nombre = "ROLE_ADMIN_CAMBIO4";
		Rol roles = new Rol(nombre);
		roles.setId(8);
		rolTestRepositorio.save(roles);
		Rol rolesActualizado = rolTestRepositorio.findByNombre(nombre);
		assertThat(rolesActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(5)
	public void testlistarRol() {
		List<Rol> rol = (List<Rol>) rolTestRepositorio.findAll();
		for (Rol rol2 : rol) {
			System.out.println(rol2);
		}
		assertThat(rol).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeleteRol() {
		long id = 8;
		boolean esExistenteAnteDeEliminar = rolTestRepositorio.findById(id).isPresent();
		rolTestRepositorio.deleteById(id);
		
		boolean noExistenteDespuesDeEliminar = rolTestRepositorio.findById(id).isPresent();
		assertTrue(esExistenteAnteDeEliminar);
		assertFalse(noExistenteDespuesDeEliminar);
	}
	
}
