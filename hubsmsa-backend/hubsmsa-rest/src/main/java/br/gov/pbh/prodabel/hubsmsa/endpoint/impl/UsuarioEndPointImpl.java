package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.FiltroPesquisaLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.UsuarioEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.LogAcessoUsuarioService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

public class UsuarioEndPointImpl implements UsuarioEndPoint {

	@EJB
    private UsuarioService usuarioService;
	
	@EJB
    private LogAcessoUsuarioService logAcessoUsuarioService;

    // @Override
    // public Boolean consultarUsuario(final String username) {
    // return this.usuarioService.verificarCadastroUsuario(username);
    // }
    //
    // @Override
    // public ResponseDTO<EntityDTO> cadastrarUsuario(final UsuarioDTO cadastrarUsuario) {
    // return this.usuarioService.cadastrarUsuario(cadastrarUsuario);
    // }

    @Override
    public ResponseDTO<String> checkin() {
      return this.usuarioService.checkin();
      // recuperar usuario
      // se nao existir, fazer cadastro
      // registrar log
      // retornar o usuario
      // System.out.println(this.usuarioLogadoService.getAuditUser().getMatricula());
      // System.out.println(this.usuarioLogadoService.getAuditUser().getNomeUsuario());
      // System.out.println(this.usuarioLogadoService.getAuditUser().getEmail());
      // return null;
    }

	@Override
	public PaginacaoPublicaDTO<VisualizarLogAcessoUsuarioDTO> consultarAcessoUsuarios(
			FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisaDTO) {
		return logAcessoUsuarioService.consultarLogAcessoUsuario(filtroPesquisaDTO);
	}

	@Override
	public List<SelecaoDTO> selecaoUsuario() {
		return usuarioService.consultarSelecaoUsuario();
	}

    @Override
    public Response gerarLogCsv(FiltroPesquisaLogAcessoUsuarioDTO filtro) {
      return logAcessoUsuarioService.gerarCsv(filtro);
    }

    @Override
    public Response gerarLogExcel(FiltroPesquisaLogAcessoUsuarioDTO filtro) {
      return logAcessoUsuarioService.gerarExcel(filtro);
    }

}
