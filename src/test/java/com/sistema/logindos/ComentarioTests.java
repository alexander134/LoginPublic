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

import com.sistema.logindos.entidades.Comentario;
import com.sistema.logindos.entidades.Publicacion;
import com.sistema.logindos.repositorios.ComentarioTestRepositorio;
import com.sistema.logindos.repositorios.PublicacionRepo;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ComentarioTests {
	
	@Autowired
	private  ComentarioTestRepositorio comentarioTestRepositorio;
	
	@Autowired
	private PublicacionRepo publicacionRepo;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarComentario() {


		Publicacion publicacion = publicacionRepo.findTopByOrderByIdDesc();
		
		Comentario comentario = new Comentario("ANONIMO","anonimo@anonimo.cl","este es el cuerpo del comentario anonimo",publicacion);
		try {
			Comentario comentarioGuardardos= comentarioTestRepositorio.save(comentario);
			assertNotNull(comentarioGuardardos);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void testBurcarNombre() {
		String nombre = "ANONIMO";
		Comentario comentario = comentarioTestRepositorio.findByNombre(nombre);
		assertThat(comentario.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(3)
	public void testBurcarNombreNoExist() {
		String nombre = "ANONIMO2";
		Comentario comentario = comentarioTestRepositorio.findByNombre(nombre);
		assertNull(comentario);
	}
	
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testUPDComentario() {
		Publicacion publicacion = publicacionRepo.findTopByOrderByIdDesc();
		String nombre = "ANONIMO_ANONIMO";
		Comentario comentario = new Comentario("ANONIMO_ANONIMO","anonimoModi@anonimoModi.cl","este es el cuerpo del comentario anonimo  MoDi",publicacion);
		long idcomentario= comentarioTestRepositorio.findTopByOrderByIdDesc().getId();
		comentario.setId(idcomentario);
		comentarioTestRepositorio.save(comentario);
		Comentario comentarioActualizado = comentarioTestRepositorio.findByNombre(nombre);
		assertThat(comentarioActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(5)
	public void testlistarComentario() {
		List<Comentario> comentario = (List<Comentario>) comentarioTestRepositorio.findAll();
		for (Comentario comentario2 : comentario) {
			System.out.println(comentario2);
		}
		assertThat(comentario).size().isGreaterThan(0);
	}
	
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeleteComentario() {
		long id = comentarioTestRepositorio.findTopByOrderByIdDesc().getId();
		boolean esExistenteAnteDeEliminar = comentarioTestRepositorio.findById(id).isPresent();
		comentarioTestRepositorio.deleteById(id);
		
		boolean noExistenteDespuesDeEliminar = comentarioTestRepositorio.findById(id).isPresent();
		assertTrue(esExistenteAnteDeEliminar);
		assertFalse(noExistenteDespuesDeEliminar);
	}

}
