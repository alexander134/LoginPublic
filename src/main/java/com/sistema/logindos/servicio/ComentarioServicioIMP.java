package com.sistema.logindos.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.logindos.dto.ComentarioDTO;
import com.sistema.logindos.dto.ComentarioRespuesta;
import com.sistema.logindos.entidades.Comentario;
import com.sistema.logindos.entidades.Publicacion;
import com.sistema.logindos.excepciones.BlockAppException;
import com.sistema.logindos.excepciones.ResourceNotFoundException;
import com.sistema.logindos.repositorios.ComentarioRepo;
import com.sistema.logindos.repositorios.PublicacionRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
@Service
public class ComentarioServicioIMP implements ComentarioServicio{
	
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private ComentarioRepo comentarioRepo;
	@Autowired
	private PublicacionRepo publicacionRepo;

	
	@Override
	public ComentarioDTO crearComentario(long usuarioId, long publicacionId, ComentarioDTO comentarioDTO) {
		
		Comentario comentario = mapDTOtoENTITY(comentarioDTO);
		Publicacion publicacion = publicacionRepo.findById(publicacionId).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",publicacionId));
		comentario.setPublicacion(publicacion);
		//Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow(()->new ResourceNotFoundException("Usuario", "Id",usuarioId));
		//comentario.setUsuario(usuario);
		Comentario newComentario = comentarioRepo.save(comentario);
		return mapENTITYtoDTO(newComentario);
	}
	
	
	@Override
	public ComentarioRespuesta getListComentarios(int numeroPag, int cantRegistro, String orderPorCampo, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
			Sort.by(orderPorCampo).ascending()
		:
			Sort.by(orderPorCampo).descending();
		Pageable pageable = PageRequest.of(numeroPag, cantRegistro,sort);
		Page<Comentario> comentarios = comentarioRepo.findAll(pageable);
		List<Comentario> listaComentarios= comentarios.getContent();
		List<ComentarioDTO> contenido = listaComentarios.stream().map(comentario ->mapENTITYtoDTO(comentario)).collect(Collectors.toList());
		
		ComentarioRespuesta comentarioRes= new ComentarioRespuesta();
		comentarioRes.setContenido(contenido);
		comentarioRes.setNumeroPagina(comentarios.getNumber());
		comentarioRes.setMedidaPagina(comentarios.getSize());
		comentarioRes.setTotalRegistros(comentarios.getTotalElements());
		comentarioRes.setTotalPaginas(comentarios.getTotalPages());
		comentarioRes.setUltimaPagina(comentarios.isLast());
		return comentarioRes;
	}
	
	
	@Override
	public ComentarioDTO getComentariosXId(Long comentarioId) {
		Comentario comentario = comentarioRepo.findById(comentarioId).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",comentarioId) );
		return mapENTITYtoDTO(comentario);
	}

	@Override
	public ComentarioDTO updComentarioId(ComentarioDTO comentarioDTO, Long comentarioId) {
		Comentario comentario = comentarioRepo.findById(comentarioId).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",comentarioId) );
		comentario.setNombre(comentarioDTO.getNombre());
		comentario.setCuerpo(comentarioDTO.getCuerpo());
		comentario.setEmail(comentarioDTO.getEmail());
		
		Comentario comentarioActualizado = comentarioRepo.save(comentario);
		return mapENTITYtoDTO(comentarioActualizado);
	}

	@Override
	public void deleteComentarioId(Long comentarioId) {
		Comentario comentario = comentarioRepo.findById(comentarioId).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",comentarioId) );
		comentarioRepo.delete(comentario);
	}
	
	@Override
	public List<ComentarioDTO> getComentariosXIdPublicidad(Long publicacionId) {
		List<Comentario> comentarios = comentarioRepo.findByPublicacionId(publicacionId);
		return  comentarios.stream().map(comentario ->mapENTITYtoDTO(comentario)).collect(Collectors.toList());
	}
	
	private ComentarioDTO mapENTITYtoDTO(Comentario comentario) {
		
		ComentarioDTO comentarioDTO = modelmapper.map(comentario, ComentarioDTO.class);
		return comentarioDTO;
	}
	
	private Comentario mapDTOtoENTITY(ComentarioDTO comentarioDTO) {
		Comentario comentario = modelmapper.map(comentarioDTO, Comentario.class);
		return comentario;
	}
	
	@Override
	public ComentarioDTO obtenerComentarioPorIPublicacion(Long publicacionId, Long comentarioid) {
		Publicacion publicacion = publicacionRepo.findById(publicacionId).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",publicacionId) );
		
		Comentario comentario = comentarioRepo.findById(comentarioid).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",comentarioid) );
		
		if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlockAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicion");
		}
		
		// TODO Auto-generated method stub
		return mapENTITYtoDTO(comentario);
	}

}
