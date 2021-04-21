package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogAcessoUsuario;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;

public class LogAcessoUsuarioMapper {

	private static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

	public static List<VisualizarLogAcessoUsuarioDTO> mapper(List<LogAcessoUsuario> consultarLogAcessoUsuarios) {
		
		List<VisualizarLogAcessoUsuarioDTO> logAcessoUsuarios = new ArrayList<VisualizarLogAcessoUsuarioDTO>();
		consultarLogAcessoUsuarios.forEach(acesso -> {
			VisualizarLogAcessoUsuarioDTO acessoDTO = new VisualizarLogAcessoUsuarioDTO();
			acessoDTO.setDataAcesso(TimeUtil.convertDataToStr(acesso.getDataAcesso(), DD_MM_YYYY_HH_MM));
			acessoDTO.setNome(acesso.getUsuario().getNome());
			acessoDTO.setEmail(acesso.getUsuario().getEmail());
			acessoDTO.setLogin(acesso.getUsuario().getLogin());
			
			logAcessoUsuarios.add(acessoDTO);
		});
		return logAcessoUsuarios;
	}

}
