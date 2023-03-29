package com.sistema.logindos.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.logindos.dto.PublicacionDTO;
import com.sistema.logindos.dto.PublicacionRespuesta;
import com.sistema.logindos.entidades.Publicacion;
import com.sistema.logindos.excepciones.ResourceNotFoundException;
import com.sistema.logindos.repositorios.PublicacionRepo;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PublicacionServicionIMP implements PublicacionServicio{

	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private PublicacionRepo publicacionRepo;
	
	
	@Override
	public PublicacionDTO crearPublicacion(long usuarioId, PublicacionDTO publicacionDTO) {

		Publicacion publicacion= mapDTOtoENTITY(publicacionDTO);

		Publicacion nuevaPublicacion = publicacionRepo.save(publicacion);
		
		PublicacionDTO publicacionResp = mapENTITYtoDTO(nuevaPublicacion);
		return publicacionResp;
	}
	
	@Override
	public PublicacionRespuesta getListPublicaciones(int numeroPag,int cantRegistro,String orderPorCampo,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
			Sort.by(orderPorCampo).ascending()
		:
			Sort.by(orderPorCampo).descending();
		Pageable pageable = PageRequest.of(numeroPag, cantRegistro,sort);
		Page<Publicacion> publicaciones = publicacionRepo.findAll(pageable);
		List<Publicacion> listaPublicaciones = publicaciones.getContent();
		List<PublicacionDTO> contenido= listaPublicaciones.stream().map(publicacion ->mapENTITYtoDTO(publicacion)).collect(Collectors.toList());
		
		PublicacionRespuesta publicacionRes = new PublicacionRespuesta();
		publicacionRes.setContenido(contenido);
		publicacionRes.setNumeroPagina(publicaciones.getNumber());
		publicacionRes.setMedidaPagina(publicaciones.getSize());
		publicacionRes.setTotalRegistros(publicaciones.getTotalElements());
		publicacionRes.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRes.setUltimaPagina(publicaciones.isLast());
		return publicacionRes;
	}
	
	@Override
	public PublicacionDTO getPublicXId(Long idPublicacion) {
		Publicacion publicacion = publicacionRepo.findById(idPublicacion).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",idPublicacion) );
		return mapENTITYtoDTO(publicacion);
	}
	
	
	@Override
	public PublicacionDTO updPublicId(PublicacionDTO publicacionDTO, Long idPublicacion) {
		Publicacion publicacion = publicacionRepo.findById(idPublicacion).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",idPublicacion) );
		
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		
		Publicacion publicacionActualizada = publicacionRepo.save(publicacion);
		return mapENTITYtoDTO(publicacionActualizada);
	}
	
	
	@Override
	public void deletePublicId(Long idPublicacion) {		
		Publicacion publicacion = publicacionRepo.findById(idPublicacion).orElseThrow(()->new ResourceNotFoundException("Publicacion", "Id",idPublicacion) );
		publicacionRepo.delete(publicacion);
	}
	
	private PublicacionDTO mapENTITYtoDTO(Publicacion publicacion) {
			
			PublicacionDTO publicacionDTO = modelmapper.map(publicacion, PublicacionDTO.class);
			return publicacionDTO;
		}
	
	private Publicacion mapDTOtoENTITY(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = modelmapper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}
}
