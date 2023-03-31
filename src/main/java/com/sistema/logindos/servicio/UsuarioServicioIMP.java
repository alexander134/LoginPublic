package com.sistema.logindos.servicio;

import com.sistema.logindos.dto.RegistroUsuarioDTO;
import com.sistema.logindos.dto.UsuarioRespuesta;
import com.sistema.logindos.entidades.Usuario;
import com.sistema.logindos.repositorios.UsuarioRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioServicioIMP implements UsuarioServicio{

	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	@Override
	public UsuarioRespuesta getListUsuarios(int numeroPag, int cantRegistro, String orderPorCampo, String sortDir) {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public RegistroUsuarioDTO getUsuarioXId(Long idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistroUsuarioDTO updUsuarioId(RegistroUsuarioDTO usuarioDTO, Long idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUsuarioId(Long idUsuario) {
		// TODO Auto-generated method stub
		
	}
	
	private RegistroUsuarioDTO mapENTITYtoDTO(Usuario usuario) {
		RegistroUsuarioDTO usuarioDTO = modelmapper.map(usuario, RegistroUsuarioDTO.class);
		return usuarioDTO;
	}
	
	private Usuario mapDTOtoENTITY(RegistroUsuarioDTO usuarioDTO) {
		Usuario usuario = modelmapper.map(usuarioDTO, Usuario.class);
		return usuario;
	}

}
