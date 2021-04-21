package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro.CadastrarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.VisualizarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.SistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.SimNaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;

// TODO: Auto-generated Javadoc
public class ContatoEmpresaServicoMapper {

	/**
	 * Mapper.
	 *
	 * @param consultarContatoEmpresa the consultar contato empresa
	 * @return the list
	 */
    public static List<VisualizarContatoEmpresaServicoDTO> mapper(
        List<ContatoEmpresaServico> consultaContatoEmpresaServico) {
      List<VisualizarContatoEmpresaServicoDTO> contatosDeEmpresaServicos = new ArrayList<>();
      consultaContatoEmpresaServico.forEach(contato -> {
        VisualizarContatoEmpresaServicoDTO contatoDTO = new VisualizarContatoEmpresaServicoDTO();
			contatoDTO.setId(contato.getId());
        contatoDTO.setEmpresa(EmpresaMapper.mapper(contato.getContatoEmpresa().getEmpresa()));
        contatoDTO.setServico(ServicoMapper.mapper(contato.getServico()));
        contatoDTO.setContato(ContatoEmpresaMapper.mapper(contato.getContatoEmpresa()));
        contatoDTO.setNotificacaoSucesso(
            SimNaoEnum.S.equals(contato.getNotificacaoSucesso()));
        contatoDTO
            .setNotificacaoFalha(SimNaoEnum.S.equals(contato.getNotificacaoFalha()));
        contatoDTO.setNotificacao(ContatoEmpresaServicoMapper.formataCampoNotificacao(contato));
        contatosDeEmpresaServicos.add(contatoDTO);
		});
      return contatosDeEmpresaServicos;
	}

    /**
     * Mapper.
     *
     * @param contatoEmpresaServico the contato empresa servico
     * @return the visualizar contato empresa servico DTO
     */
    public static VisualizarContatoEmpresaServicoDTO mapper(
        ContatoEmpresaServico contatoEmpresaServico) {
      VisualizarContatoEmpresaServicoDTO contatoDTO = new VisualizarContatoEmpresaServicoDTO();
      contatoDTO.setId(contatoEmpresaServico.getId());
      contatoDTO
          .setEmpresa(EmpresaMapper.mapper(contatoEmpresaServico.getContatoEmpresa().getEmpresa()));
      contatoDTO.setServico(ServicoMapper.mapper(contatoEmpresaServico.getServico()));
      contatoDTO.setContato(ContatoEmpresaMapper.mapper(contatoEmpresaServico.getContatoEmpresa()));
      contatoDTO.setNotificacaoSucesso(
          SimNaoEnum.S.equals(contatoEmpresaServico.getNotificacaoSucesso()));
      contatoDTO
          .setNotificacaoFalha(SimNaoEnum.S.equals(contatoEmpresaServico.getNotificacaoFalha()));
      contatoDTO.setNotificacao(
          ContatoEmpresaServicoMapper.formataCampoNotificacao(contatoEmpresaServico));
      return contatoDTO;
    }
	

    /**
     * Mapper.
     *
     * @param cadastrarDTO the cadastrar DTO
     * @return
     */
    public static ContatoEmpresaServico mapper(CadastrarContatoEmpresaServicoDTO cadastrarDTO) {

      ContatoEmpresaServico contatoEmpresaServico = new ContatoEmpresaServico();

      contatoEmpresaServico
          .setContatoEmpresa(ContatoEmpresaMapper.mapper(cadastrarDTO.getIdContatoEmpresa()));
      contatoEmpresaServico.setServico(ServicoMapper.mapper(cadastrarDTO.getIdServico()));

      contatoEmpresaServico.setNotificacaoSucesso(
          (cadastrarDTO.getNotificacaoSucesso() != null && cadastrarDTO.getNotificacaoSucesso())
              ? SimNaoEnum.S
              : SimNaoEnum.N);

      contatoEmpresaServico
          .setNotificacaoFalha(
              (cadastrarDTO.getNotificacaoFalha() != null && cadastrarDTO.getNotificacaoFalha())
                  ? SimNaoEnum.S
                  : SimNaoEnum.N);

      return contatoEmpresaServico;
    }

    /**
     * Mapper.
     *
     * @param editarDTO the editar DTO
     * @param contatoEmpresaServico the contato empresa servico
     */
    public static void mapper(CadastrarContatoEmpresaServicoDTO editarDTO,
        ContatoEmpresaServico contatoEmpresaServico) {
      contatoEmpresaServico
          .setContatoEmpresa(ContatoEmpresaMapper.mapper(editarDTO.getIdContatoEmpresa()));
      contatoEmpresaServico.setServico(ServicoMapper.mapper(editarDTO.getIdServico()));
      contatoEmpresaServico.setNotificacaoSucesso(
          (editarDTO.getNotificacaoSucesso() != null && editarDTO.getNotificacaoSucesso())
              ? SimNaoEnum.S
              : SimNaoEnum.N);
      contatoEmpresaServico.setNotificacaoFalha(
          (editarDTO.getNotificacaoFalha() != null && editarDTO.getNotificacaoFalha())
              ? SimNaoEnum.S
              : SimNaoEnum.N);
    }

    /**
     * Mapper.
     *
     * @param editarSistemaDTO the editar sistema DTO
     * @param sistema the sistema
     */
    public static void mapper(SistemaDTO editarSistemaDTO, Sistema sistema) {

      sistema.setEmpresa(EmpresaMapper.mapper(editarSistemaDTO.getIdEmpresa()));
      sistema.setDataAtualizacao(LocalDate.now());
      sistema.setNome(editarSistemaDTO.getNome());
      sistema.setDescricao(editarSistemaDTO.getDescricao());
      sistema.setAtivo(StatusEnum.valueOf(editarSistemaDTO.getStatus()));
      if (FormaCadastroEnum.C.equals(sistema.getFormaCadastro())) {
        sistema.setFormaCadastro(FormaCadastroEnum.C);
      }
      if (FormaCadastroEnum.I.equals(sistema.getFormaCadastro())) {
        sistema.setFormaCadastro(FormaCadastroEnum.A);
      }

    }

    /**
     * Formata campo notificacao.
     *
     * @param dado the dado
     * @return the string
     */
    private static String formataCampoNotificacao(ContatoEmpresaServico dado) {

      StringBuilder notificacao = new StringBuilder();

      if (SimNaoEnum.S.equals(dado.getNotificacaoSucesso())
          && SimNaoEnum.S.equals(dado.getNotificacaoFalha())) {
        return (notificacao.append(MensagemUtil.getMessage(MensagemEnum.SUCESSO)).append(" / ")
            .append(MensagemUtil.getMessage(MensagemEnum.FALHA))
            .toString());
      } else if (SimNaoEnum.S.equals(dado.getNotificacaoSucesso())) {
        return (notificacao.append(MensagemUtil.getMessage(MensagemEnum.SUCESSO)).toString());
      } else if (SimNaoEnum.S.equals(dado.getNotificacaoFalha())) {
        return (notificacao.append(MensagemUtil.getMessage(MensagemEnum.FALHA)).toString());
      }
      return "";

    }


}
