package br.gov.pbh.prodabel.hubsmsa.service;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.FiltroPesquisaLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.VisualizarLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.exception.ServicoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.LogServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.LogServicoMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;

// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
public class LogServicoService {

  @EJB
  private LogServicoDAO logServicoDAO;

  /**
   * Gravar log servico.
   *
   * @param logServico o log do servico
   */
  public LogServico gravarLogServico(LogServico logServico) {
    logServico.setDataFimEvento(new Timestamp(System.currentTimeMillis()));
    return logServicoDAO.merge(logServico);
  }

  /**
   * Buscar resquisicoes falhas.
   *
   * @return the list
   */
  public List<LogServico> buscarResquisicoesFalhas() {
    try {
      return logServicoDAO.buscarResquisicoesFalhas();
    } catch (DAOException | RegistroNaoEncontradoException e) {
      throw new ServicoException("Falha ao buscar registro para reprocessamento.");
    }
  }

  /**
   * Consultar log servico.
   *
   * @param filtro the filtro
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<VisualizarLogServicoDTO> consultarLogServico(
      FiltroPesquisaLogServicoDTO filtro) {
    try {
      this.verificarSeTemUmaPropriedade(filtro);
      PaginacaoPublicaDTO<VisualizarLogServicoDTO> logServicos = new PaginacaoPublicaDTO<>();
      List<LogServico> consultarLogServico = logServicoDAO.consultarLogServico(filtro);
      logServicos.setItens(LogServicoMapper.mapper(consultarLogServico));
      logServicos.setTotalRegistros(consultarLogServico.size());
      return logServicos;
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  public void setLogServicoDAO(LogServicoDAO logServicoDAO) {
    this.logServicoDAO = logServicoDAO;
  }

  /**
   * Verficiar se tem uma propriedade.
   *
   * @param filtro the filtro pesquisa
   */
  private void verificarSeTemUmaPropriedade(FiltroPesquisaLogServicoDTO filtro) {
    if (filtro.getDataInicial() == (null) && filtro.getDataFinal() == null
        && filtro.getIdServico() == null && filtro.getStatus().isEmpty()) {

      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME028));

    }

  }



}
