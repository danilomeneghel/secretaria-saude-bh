package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.ContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaLogContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.VisualizarContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.ContatoEmpresaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaService;

public class ContatoEmpresaEndPointImpl implements ContatoEmpresaEndPoint {

  @EJB
  private ContatoEmpresaService service;

  @Override
  public PaginacaoPublicaDTO<VisualizarContatoEmpresaDTO> consultarContatoEmpresa(
      FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa) {
    return service.consultarContatoEmpresa(pesquisarContatoEmpresa);
  }

  @Override
  public VisualizarContatoEmpresaDTO consultarContatoEmpresa(Long id) {
    return service.consultarContatoEmpresa(id);
  }

  @Override
  public ResponseDTO<EntityDTO> cadastrarContatoEmpresa(ContatoEmpresaDTO cadastrarContatoEmpresa) {
    return service.cadastrarContatoEmpresa(cadastrarContatoEmpresa);
  }

  @Override
  public ResponseDTO<EntityDTO> editarContatoEmpresa(Long id,
      ContatoEmpresaDTO editarContatoEmpresa) {
    return service.editarContatoEmpresa(id, editarContatoEmpresa);
  }

  @Override
  public ResponseDTO<EntityDTO> excluirContatoEmpresa(Long id) {
    return service.excluirContatoEmpresa(id);
  }

  @Override
  public Response gerarExcel(FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa) {
    return service.gerarExcel(pesquisarContatoEmpresa);
  }

  @Override
  public Response gerarCsv(FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa) {
    return service.gerarCsv(pesquisarContatoEmpresa);
  }

  @Override
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(
      FiltroPesquisaLogContatoEmpresaDTO revisaoDTO) {
    return service.consultarHistoricoRevisoes(revisaoDTO);
  }

  @Override
  public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {
    return service.consultarDetalheRevisao(id);
  }

  @Override
  public Response gerarLogExcel(FiltroPesquisaLogContatoEmpresaDTO filtro) {

    return service.gerarLogExcel(filtro);
  }

  /**
   * Gerar log csv.
   *
   * @param filtro the filtro
   * @return the response
   */
  @Override
  public Response gerarLogCsv(FiltroPesquisaLogContatoEmpresaDTO filtro) {
    return service.gerarLogCsv(filtro);
  }

  @Override
  public List<SelecaoDTO> consultarContatosDaEmpresa(Long idEmpresa) {
    return service.consultarContatosDaEmpresa(idEmpresa);
  }

}
