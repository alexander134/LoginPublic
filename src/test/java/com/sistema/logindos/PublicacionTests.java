package com.sistema.logindos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.sistema.logindos.entidades.Publicacion;
import com.sistema.logindos.repositorios.PublicacionTestRepositorio;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PublicacionTests {
	
	@Autowired
	private PublicacionTestRepositorio publicacionTestRepositorio;
	
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarPublicacion() {
		Publicacion publicacion = new Publicacion("Titulo Anonimo", "Descripcion Anonima", "Contenido Anonima", null);
		
		try {
			Publicacion publicaciontest = publicacionTestRepositorio.save(publicacion);
			assertNotNull(publicaciontest);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void testBurcarTitulo() {
		String titulo = "Titulo Anonimo";
		Publicacion publicion = publicacionTestRepositorio.findByTitulo(titulo);
		assertThat(publicion.getTitulo()).isEqualTo(titulo);
		
	}
	
	@Test
	@Order(3)
	public void testBurcarTituloNoExist() {
		String titulo = "Titulo Anonimo2";
		Publicacion publicion = publicacionTestRepositorio.findByTitulo(titulo);
		assertNull(publicion);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testUPDPublicacion() {
		String titulo = "Titulo Anonimo222";
		Publicacion publicacionExistente = publicacionTestRepositorio.findTopByOrderByIdDesc();
		publicacionExistente.setTitulo("Titulo Anonimo222");
	    publicacionExistente.setDescripcion("Descripcion Anonima22");
	    publicacionExistente.setContenido("Contenido Anonima2222");
	    
	    publicacionTestRepositorio.save(publicacionExistente);
		Publicacion publicacionActualizado = publicacionTestRepositorio.findByTitulo(titulo);
		assertThat(publicacionActualizado.getTitulo()).isEqualTo(titulo);
	}
	
	
	@Test
	@Order(5)
	public void testlistarPublicacion() {
		
		List<Publicacion> publicion = (List<Publicacion>) publicacionTestRepositorio.findAll();
		for (Publicacion publicacion2 : publicion) {
			System.out.println(publicacion2);
		}
		assertThat(publicion).size().isGreaterThan(0);
	}
	
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeletePublicacion() {
		long id = publicacionTestRepositorio.findTopByOrderByIdDesc().getId();
		boolean esExistenteAnteDeEliminar = publicacionTestRepositorio.findById(id).isPresent();
		publicacionTestRepositorio.deleteById(id);
		
		boolean noExistenteDespuesDeEliminar = publicacionTestRepositorio.findById(id).isPresent();
		assertTrue(esExistenteAnteDeEliminar);
		assertFalse(noExistenteDespuesDeEliminar);
	}

}
