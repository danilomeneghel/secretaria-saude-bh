package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import br.gov.pbh.prodabel.hubsmsa.dto.usuario.UsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.VisualizarUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;

public class UsuarioMapper {

	public static Usuario mapper(UsuarioDTO cadastrarUsuario) {
		Usuario usuario = new Usuario();
		usuario.setEmail(cadastrarUsuario.getEmail());
		usuario.setNome(cadastrarUsuario.getFirstName() + ' ' + cadastrarUsuario.getLastName());
		usuario.setLogin(cadastrarUsuario.getUsername());
		return usuario;
	}

	public static VisualizarUsuarioDTO mapper(Usuario usuario) {
		VisualizarUsuarioDTO usuarioDTO = new VisualizarUsuarioDTO();
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setLogin(usuario.getLogin());
		return usuarioDTO;
	}

}
