package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.endpoint.ColetaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import br.gov.pbh.prodabel.hubsmsa.ws.service.ColetaExameService;

public class ColetaEndPointImpl implements ColetaEndPoint {

  @EJB
  private ColetaExameService coletaExameService;

  @Override
  public SolicitacoesColetaExameResponse buscarSolicitacoesColetaExame(
      SolicitacaoColetaDTO solicitacaoColeta) {

    return coletaExameService.buscarSolicitacoesColetaExameDoPaciente(solicitacaoColeta);
  }

}
