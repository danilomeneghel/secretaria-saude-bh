package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.EmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaLogEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.EmpresaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.EmpresaService;

public class EmpresaEndPointImpl implements EmpresaEndPoint {
	@EJB
	private EmpresaService service;

	@Override
	public PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresa(FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
		return service.consultarEmpresa(pesquisarEmpresa);
	}
	
	@Override
	public PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresaDroplist(FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
		return service.consultarEmpresaDroplist(pesquisarEmpresa);
	}

	@Override
	public ResponseDTO<EntityDTO> cadastrarEmpresa(EmpresaDTO empresaDTO) {
		return service.cadastrarEmpresa(empresaDTO);
	}

	@Override
	public ResponseDTO<EntityDTO> editarEmpresa(Long id, EmpresaDTO editarEmpresaDTO) {
		return service.editarEmpresa(editarEmpresaDTO, id);
	}

	@Override
	public VisualizarEmpresaDTO consultarEmpresa(Long id) {
		return service.consultarEmpresa(id);
	}

	@Override
	public ResponseDTO<EntityDTO> excluirEmpresa(Long id) {
		return service.excluirEmpresa(id);
	}

	@Override
	public Response gerarExcel(FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
		return service.gerarExcel(pesquisarEmpresa);
	}

	@Override
	public Response gerarCsv(FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
		return service.gerarCsv(pesquisarEmpresa);
	}

    @Override
    public Response gerarHistoricoCsv(FiltroPesquisaLogEmpresaDTO pesquisarEmpresa) {
      return service.gerarHistoricoCsv(pesquisarEmpresa);
    }

    @Override
    public Response gerarHistoricoExcel(FiltroPesquisaLogEmpresaDTO pesquisarEmpresa) {
      return service.gerarHistoricoExcel(pesquisarEmpresa);
    }

	@Override
	public List<SelecaoDTO> selecaoEmpresa() {
		return service.consultarSelecaoEmpresa();
	}

	@Override
	public List<SelecaoDTO> consultarSistemasDaEmpresa(Long idEmpresa) {
		return service.consultarSistemasDaEmpresa(idEmpresa);
	}
	
	@Override
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(FiltroPesquisaLogEmpresaDTO revisaoDTO) {
		return service.consultarHistoricoRevisoes(revisaoDTO);
	}

	@Override
	public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {
		
		return service.consultarDetalheRevisao(id);
	}

}
