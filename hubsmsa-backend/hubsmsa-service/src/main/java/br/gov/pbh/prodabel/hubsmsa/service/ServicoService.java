package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ServicoMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

/**
 * 
 * @author weverton.lucena classe responsável por manipular os dados da classe Servico
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ServicoService extends GenericService<Long, Servico> {
  
  @EJB
  private ServicoDAO servicoDAO;
  
  
  /**
   * Consultar servico.
   *
   * @return the list
   */
  public List<VisualizarServicoDTO> consultarServico() {
    try {
      List<Servico> servicos = servicoDAO.consultaServicos();
      ValidadorUtil.validarRegistroNaoEncontrado(servicos);
      return ServicoMapper.mapper(servicos);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar servico.
   *
   * @param idSistemaPrimario the id sistema primario
   * @param idSistemaSecundario the id sistema secundario
   * @param nomeServico the nome servico
   * @return the list
   */
  public List<Servico> consultarServico(Long idSistemaPrimario, Long idSistemaSecundario,
      String nomeServico) {
    try {
      return servicoDAO.consultarServico(idSistemaPrimario, idSistemaSecundario, nomeServico);
    } catch (DAOException e) {
      throw new NegocioException("Nenhum Servico encontrado com o nome: " + nomeServico);
    }
  }

  /**
   * Consultar servico.
   *
   * @param idSistema the id sistema
   * @return the list
   */
  public List<VisualizarServicoDTO> consultarServicos(Long idSistema) {
    try {
      List<Servico> servicos = servicoDAO.consultarServicoPrimarioOuSecundario(idSistema);
      ValidadorUtil.validarNoResultList(servicos);
      return ServicoMapper.mapper(servicos);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME036));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar servico por id.
   *
   * @param id the id
   * @return the visualizar servico DTO
   */
  public VisualizarServicoDTO consultarServico(Long id) {
    try {
      Servico servico = servicoDAO.consultaServico(id);
      ValidadorUtil.validarRegistroNaoEncontrado(servico);
      return ServicoMapper.mapper(servico);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME036));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar selecao servico.
   *
   * @return the list
   */
  public List<SelecaoDTO> consultarSelecaoServico(Long idSistema) {
    try {
      return this.servicoDAO.consultarSelecaoServico(idSistema);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME036));
    }
  }

  public void setServicoDAO(ServicoDAO servicoDao) {
    this.servicoDAO = servicoDao;
  }

}
