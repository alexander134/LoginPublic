package com.sistema.logindos.servicio;

import com.sistema.logindos.dto.RegistroUsuarioDTO;
import com.sistema.logindos.dto.UsuarioRespuesta;

public interface UsuarioServicio {
	
	//public RegistroUsuarioDTO crearUsuario(RegistroUsuarioDTO usuarioDTO);

	public UsuarioRespuesta getListUsuarios(int numeroPag,int cantRegistro,String orderPorCampo,String sortDir);
	
	public RegistroUsuarioDTO getUsuarioXId(Long idUsuario);
	
	public RegistroUsuarioDTO updUsuarioId(RegistroUsuarioDTO usuarioDTO,Long idUsuario);
	
	public void deleteUsuarioId(Long idUsuario);
}
