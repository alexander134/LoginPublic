package com.sistema.logindos.servicio;

import com.sistema.logindos.dto.PublicacionDTO;
import com.sistema.logindos.dto.PublicacionRespuesta;

public interface PublicacionServicio {
	
public PublicacionDTO crearPublicacion(long usuarioId, PublicacionDTO publicacionDTO);
	
	public PublicacionRespuesta getListPublicaciones(int numeroPag,int cantRegistro,String orderPorCampo,String sortDir);
	
	public PublicacionDTO getPublicXId(Long idPublicacion);
	
	public PublicacionDTO updPublicId(PublicacionDTO publicacionDTO,Long idPublicacion);
	
	public void deletePublicId(Long idPublicacion);

}
