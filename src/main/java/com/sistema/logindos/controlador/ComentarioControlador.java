package com.sistema.logindos.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.logindos.dto.ComentarioDTO;
import com.sistema.logindos.dto.ComentarioRespuesta;
import com.sistema.logindos.servicio.ComentarioServicio;
import com.sistema.logindos.utilerias.AppConstantes;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {

	@Autowired
	private ComentarioServicio comentarioServicio;
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/publicaciones/{idU}/{idP}/comentarios")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(name = "idU")Long idU,@PathVariable(name = "idP")Long idP,@Valid @RequestBody ComentarioDTO comentarioDTO){
		
		return new ResponseEntity<>(comentarioServicio.crearComentario(idU, idP, comentarioDTO),HttpStatus.CREATED);
	}
	
	@GetMapping("/comentarios/listar")
	public ComentarioRespuesta listarComentarios(
			@RequestParam(value = "pageN",defaultValue = AppConstantes.NUMERO_PAGINA_DEFECTO,required = false) int numeroPagina,
			@RequestParam(value = "cantReg",defaultValue = AppConstantes.MEDIDA_PAGINA_DEFECTO,required = false)int cantidadRegistros,
			@RequestParam(value = "sortById",defaultValue = AppConstantes.ORDER_CAMPO_DEFECTO,required = false)String orderPorCampo,
			@RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDER_DIRECCION_DEFECTO,required = false)String sortDir){
		return comentarioServicio.getListComentarios(numeroPagina,cantidadRegistros,orderPorCampo,sortDir);
	}
	
	@GetMapping("/comentarios/listar/{id}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(name = "id")Long id){
		return ResponseEntity.ok(comentarioServicio.getComentariosXId(id));
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/comentarios/actualizar/{id}")
	public ResponseEntity<ComentarioDTO> actualizarComentario(@Valid @RequestBody ComentarioDTO comentarioDTO,@PathVariable(name = "id")Long id){
		ComentarioDTO comentarioRespuesta = comentarioServicio.updComentarioId(comentarioDTO, id);
		return new ResponseEntity<>(comentarioRespuesta,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/comentarios/delete/{id}")
	public ResponseEntity<String> eliminarComentarioPorId(@PathVariable(name = "id")Long id){
		comentarioServicio.deleteComentarioId(id);
		 return new ResponseEntity<>("Comentario con id="+id+" Eliminado" ,HttpStatus.OK);
	}
	
	@GetMapping("/comentarios/publicacion/{idPublicacion}")
	public List<ComentarioDTO> listarComentariosXPublic(@PathVariable(name = "idPublicacion")Long id){
		return comentarioServicio.getComentariosXIdPublicidad(id);
	}
	
	@GetMapping("/comentarios/{idC}/publicacion/{idP}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorIPublicacion(@PathVariable(name = "idC")Long idC,@PathVariable(name = "idP")Long idP){
		ComentarioDTO comentarioRespuestaDTO = comentarioServicio.obtenerComentarioPorIPublicacion(idP,idC);
		return new ResponseEntity<>(comentarioRespuestaDTO,HttpStatus.OK);
	}
	
}
