package br.gov.pbh.prodabel.hubsmsa.ws.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.dao.SolicitacaoColetaExameDAO;

@Stateless
@LocalBean
public class SisredeService {

  @EJB
  private SolicitacaoColetaExameDAO solicitacaoColetaDAO;

  /**
   * Buscar solicitacoes coleta exame.
   *
   * @param solicitacaoColeta the solicitacao coleta
   * @return
   */
  public SolicitacaoColetaExame buscarSolicitacoesColetaExame(
      SolicitacaoColetaDTO solicitacaoColeta) {

    return solicitacaoColetaDAO.buscarSolicitacaoColetaExame();

  }

  public void setSolicitacaoColetaDAO(SolicitacaoColetaExameDAO solicitacaoColetaDAO) {
    this.solicitacaoColetaDAO = solicitacaoColetaDAO;
  }

}
