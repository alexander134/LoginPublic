package com.sistema.logindos.controlador;

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

import com.sistema.logindos.dto.PublicacionDTO;
import com.sistema.logindos.dto.PublicacionRespuesta;
import com.sistema.logindos.servicio.PublicacionServicio;
import com.sistema.logindos.utilerias.AppConstantes;

@RestController
@RequestMapping("/api/")
public class PublicacionControlador {

	
	@Autowired
	private PublicacionServicio publicacionServicio;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/publicaciones/{idU}")
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@PathVariable(name = "idU")Long idU,@Valid @RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(idU,publicacionDTO),HttpStatus.CREATED);
	}
	
	@GetMapping("/publicaciones/listar")
	public PublicacionRespuesta listarPublicaciones(
			@RequestParam(value = "pageN",defaultValue = AppConstantes.NUMERO_PAGINA_DEFECTO,required = false) int numeroPagina,
			@RequestParam(value = "cantReg",defaultValue = AppConstantes.MEDIDA_PAGINA_DEFECTO,required = false)int cantidadRegistros,
			@RequestParam(value = "sortById",defaultValue = AppConstantes.ORDER_CAMPO_DEFECTO,required = false)String orderPorCampo,
			@RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDER_DIRECCION_DEFECTO,required = false)String sortDir){
		return publicacionServicio.getListPublicaciones(numeroPagina,cantidadRegistros,orderPorCampo,sortDir);
	}
	
	@GetMapping("/publicaciones/listar/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id")Long id){
		return ResponseEntity.ok(publicacionServicio.getPublicXId(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/publicaciones/actualizar/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable(name = "id")Long id){
		PublicacionDTO publicacionRespuesta = publicacionServicio.updPublicId(publicacionDTO, id);
		return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/publicaciones/delete/{id}")
	public ResponseEntity<String> eliminarPublicacionPorId(@PathVariable(name = "id")Long id){
		 publicacionServicio.deletePublicId(id);
		 return new ResponseEntity<>("Publicacion con id="+id+" Eliminada" ,HttpStatus.OK);
	}
}
