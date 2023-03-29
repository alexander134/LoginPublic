package com.sistema.logindos.servicio;

import java.util.List;

import com.sistema.logindos.dto.ComentarioDTO;
import com.sistema.logindos.dto.ComentarioRespuesta;

public interface ComentarioServicio {
	
public ComentarioDTO crearComentario(long usuarioId, long publicacionId, ComentarioDTO comentarioDTO);
	
	public ComentarioRespuesta getListComentarios(int numeroPag,int cantRegistro,String orderPorCampo,String sortDir);
	
	public ComentarioDTO getComentariosXId(Long comentarioId);
	
	public ComentarioDTO updComentarioId(ComentarioDTO comentarioDTO, Long comentarioId);
	
	public void deleteComentarioId(Long comentarioId);
	
	public List<ComentarioDTO> getComentariosXIdPublicidad(Long publicacionId);
	
	public ComentarioDTO obtenerComentarioPorIPublicacion(Long publicacionId, Long comentarioid);

}
