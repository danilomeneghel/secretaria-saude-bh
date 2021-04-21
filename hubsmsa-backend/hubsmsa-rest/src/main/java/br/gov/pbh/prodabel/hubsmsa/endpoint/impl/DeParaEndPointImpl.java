package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.VisualizarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.PesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.DeParaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.DeParaService;

public class DeParaEndPointImpl implements DeParaEndPoint {

  @EJB
  DeParaService deParaService;

  @Override
  public PaginacaoPublicaDTO<PesquisaDeParaDTO> consultarDePara(FiltroPesquisaDeParaDTO pesquisarDePara) {
    return deParaService.consultarDePara(pesquisarDePara);
  }

  @Override
  public VisualizarDeParaDTO visualizarDePara(Long id) {
    return deParaService.visualizarDePara(id);
  }

  @Override
  public ResponseDTO<EntityDTO> cadastrarDePara(CadastrarDeParaDTO cadastrarDePara) {
    return deParaService.cadastrarDePara(cadastrarDePara);
  }

  @Override
  public ResponseDTO<EntityDTO> editarDePara(Long id, CadastrarDeParaDTO editarDePara) {
    return deParaService.editarDePara(id, editarDePara);
  }

  @Override
  public ResponseDTO<EntityDTO> excluirDePara(Long id) {
    return deParaService.excluirDePara(id);
  }

  @Override
  public CadastrarDeParaDTO selecionarDePara(Long id) {
    return deParaService.selecionarDePara(id);
  }
  
  @Override
  public Response gerarCsv(FiltroPesquisaDeParaDTO pesquisarDePara) {
    return deParaService.gerarCsv(pesquisarDePara);
  }
  @Override
  public Response gerarExcel(FiltroPesquisaDeParaDTO pesquisarDePara) {
    return deParaService.gerarExcel(pesquisarDePara);
  }

  @Override
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogTipoDePara(
      FiltroPesquisaLogDeParaDTO revisaoDTO) {

    return deParaService.consultarLogDePara(revisaoDTO);
  }

  @Override
  public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {
    return deParaService.consultarDetalheRevisao(id);
  }

  @Override
  public Response gerarLogCsv(FiltroPesquisaLogDeParaDTO filtro) {
    return deParaService.gerarLogCsv(filtro);
  }

  @Override
  public Response gerarLogExcel(FiltroPesquisaLogDeParaDTO filtro) {
    return deParaService.gerarLogExcel(filtro);
  }

}
