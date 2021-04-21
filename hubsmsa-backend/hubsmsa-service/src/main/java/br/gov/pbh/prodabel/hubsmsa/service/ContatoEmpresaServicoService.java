package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro.CadastrarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.FiltroPesquisaContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.VisualizarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ContatoEmpresaServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ContatoEmpresaServicoMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
/**
 * Implementação do Service responsável por acessar os dados de Contatos de Empresa para notificacao
 *
 * @author weverton.lucena@ctis.com.br
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ContatoEmpresaServicoService extends GenericService<Long, ContatoEmpresaServico> {

  @EJB
  private ContatoEmpresaServicoDAO contatoEmpresaServicoDAO;

  /**
   * Buscar contatos.
   *
   * @param servico the servico
   * @return the list
   */
  public List<ContatoEmpresaServico> buscarContatosSucesso(Servico servico) {

    try {
      return contatoEmpresaServicoDAO.consultarPorServicoSucesso(servico);
    } catch (DAOException e) {
      throw new NegocioException(e.getMessage());
    }

  }

  /**
   * Buscar contatos falha.
   *
   * @param servico the servico
   * @return the list
   */
  public List<ContatoEmpresaServico> buscarContatosFalha(Servico servico) {

    try {
      return contatoEmpresaServicoDAO.consultarPorServicoFalha(servico);
    } catch (DAOException e) {
      throw new NegocioException(e.getMessage());
    }

  }

  /**
   * Consultar contato empresa servico.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<VisualizarContatoEmpresaServicoDTO> consultarContatoEmpresaServico(
      final FiltroPesquisaContatoEmpresaServicoDTO filtroPesquisa) {

    try {
      final PaginacaoPublicaDTO<VisualizarContatoEmpresaServicoDTO> contatoEmpresaServicos =
          new PaginacaoPublicaDTO<>();
      List<ContatoEmpresaServico> resultado =
          this.contatoEmpresaServicoDAO.consultarContatoEmpresaServico(filtroPesquisa);

      List<VisualizarContatoEmpresaServicoDTO> dadosDTO =
          ContatoEmpresaServicoMapper.mapper(resultado);

      contatoEmpresaServicos.setItens(dadosDTO);
      contatoEmpresaServicos
          .setTotalRegistros(resultado.size());

      return contatoEmpresaServicos;
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }

  }

  /**
   * Consultar sistema.
   *
   * @param id the id
   * @return the visualizar contato empresa servico DTO
   */
  public VisualizarContatoEmpresaServicoDTO consultarContatoEmpresaServico(Long id) {
    try {
      ContatoEmpresaServico contatoEmpresaServico =
          contatoEmpresaServicoDAO.consultaContatoEmpresaServicoPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(contatoEmpresaServico);
      return ContatoEmpresaServicoMapper.mapper(contatoEmpresaServico);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002, id));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Cadastrar contato empresa servico.
   *
   * @param cadastrarDTO the cadastrar DTO
   * @return the response DTO
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarContatoEmpresaServico(
      CadastrarContatoEmpresaServicoDTO cadastrarDTO) {

    try {
      verificarExistenciaContatoEmpresaParaMesmoServico(cadastrarDTO.getIdContatoEmpresa(),
          cadastrarDTO.getIdServico());
      ContatoEmpresaServico contatoEmpresaServico =
          ContatoEmpresaServicoMapper.mapper(cadastrarDTO);
      contatoEmpresaServicoDAO.gravar(contatoEmpresaServico);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(contatoEmpresaServico.getId()));

    } catch (PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  public void setContatoEmpresaServicoDAO(ContatoEmpresaServicoDAO contatoEmpresaServicoDAO) {
    this.contatoEmpresaServicoDAO = contatoEmpresaServicoDAO;
  }

  /**
   * Verificar existencia contato empresa para mesmo servico.
   *
   * @param idContatoEmpresa the id contato empresa
   * @param idServico the id servico
   */
  public void verificarExistenciaContatoEmpresaParaMesmoServico(Long idContatoEmpresa,
      Long idServico) {
    try {
      ContatoEmpresaServico contatoEmpresaServico = contatoEmpresaServicoDAO
          .verificarExistenciaContatoEmpresaParaMesmoServico(idContatoEmpresa, idServico);
      if (null != contatoEmpresaServico) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME038,
            contatoEmpresaServico.getContatoEmpresa().getNome()));
      }
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }
  

  /**
   * Verificar existencia contato empresa para mesmo servico.
   *
   * @param id the id
   * @param idContatoEmpresa the id contato empresa
   * @param idServico the id servico
   */
  public void verificarExistenciaContatoEmpresaParaMesmoServico(Long id, Long idContatoEmpresa,
      Long idServico) {    
      ContatoEmpresaServico contatoEmpresaServico = contatoEmpresaServicoDAO
          .verificarExistenciaContatoEmpresaParaMesmoServico(id, idContatoEmpresa, idServico);
      if (null != contatoEmpresaServico) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME038,
            contatoEmpresaServico.getContatoEmpresa().getNome()));
      }
  }

  /**
   * Editar contato empresa service.
   *
   * @param id the id
   * @param editarDTO the editar DTO
   * @return the response DTO
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> editarContatoEmpresaService(Long id,
      CadastrarContatoEmpresaServicoDTO editarDTO) {
    try {
      verificarExistenciaContatoEmpresaParaMesmoServico(id, editarDTO.getIdContatoEmpresa(),
          editarDTO.getIdServico());
      ContatoEmpresaServico contatoEmpresaServico = contatoEmpresaServicoDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(contatoEmpresaServico);
      ContatoEmpresaServicoMapper.mapper(editarDTO, contatoEmpresaServico);
      contatoEmpresaServicoDAO.merge(contatoEmpresaServico);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(contatoEmpresaServico.getId()));

    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002, id));
    }
  }

  /**
   * Excluir contato empresa servico.
   *
   * @param id the id
   * @return the response DTO
   */
  public ResponseDTO<EntityDTO> excluirContatoEmpresaServico(Long id) {
    try {
      ContatoEmpresaServico contatoEmpresaServico =
          contatoEmpresaServicoDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(contatoEmpresaServico);
      contatoEmpresaServicoDAO.excluir(id);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(contatoEmpresaServico.getId()));
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME003, id));
    }
  }


}
