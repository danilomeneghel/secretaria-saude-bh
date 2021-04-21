package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro.CadastrarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.FiltroPesquisaContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.VisualizarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.ContatoEmpresaServicoEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;

public class ContatoEmpresaServicoEndPointImpl implements ContatoEmpresaServicoEndPoint {

  @EJB
  private ContatoEmpresaServicoService service;

  /**
   * Consultar contato servico.
   *
   * @param filtro the filtro
   * @return the paginacao publica DTO
   */
  @Override
  public PaginacaoPublicaDTO<VisualizarContatoEmpresaServicoDTO> consultarContatoServico(
      FiltroPesquisaContatoEmpresaServicoDTO filtro) {
    return service.consultarContatoEmpresaServico(filtro);
  }

  @Override
  public ResponseDTO<EntityDTO> cadastrar(
      CadastrarContatoEmpresaServicoDTO cadastrarDTO) {
    return service.cadastrarContatoEmpresaServico(cadastrarDTO);
  }

  @Override
  public VisualizarContatoEmpresaServicoDTO consultarContatoEmpresaServico(Long id) {
    return service.consultarContatoEmpresaServico(id);
  }

  @Override
  public ResponseDTO<EntityDTO> editar(Long id,
      CadastrarContatoEmpresaServicoDTO editarContatoEmpresaServico) {
    return service.editarContatoEmpresaService(id, editarContatoEmpresaServico);
  }

  @Override
  public ResponseDTO<EntityDTO> excluir(Long id) {
    return service.excluirContatoEmpresaServico(id);
  }

}
