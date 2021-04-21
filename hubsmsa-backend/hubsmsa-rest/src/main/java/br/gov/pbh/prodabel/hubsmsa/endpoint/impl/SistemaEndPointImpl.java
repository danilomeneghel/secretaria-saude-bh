package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaLogSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.SistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.VisualizarSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.SistemaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;

public class SistemaEndPointImpl implements SistemaEndPoint {
	
	@EJB
	private SistemaService service;

	@Override
	public PaginacaoPublicaDTO<VisualizarSistemaDTO> consultarSistema(FiltroPesquisaSistemaDTO pesquisarSistema) {
		return service.consultarSistema(pesquisarSistema);
	}
	
	@Override
  public VisualizarSistemaDTO consultarSistema(Long id) {
		return service.consultarSistema(id);
	}

    @Override
    public List<VisualizarSistemaDTO> consultarSistemaPorEmpresa(Long idEmpresa) {

      return service.consultarSistemasPorEmpresa(idEmpresa);
    }

	@Override
	public ResponseDTO<EntityDTO> cadastrarSistema(SistemaDTO cadastrarSistema) {
		return service.cadastrarSistema(cadastrarSistema);
	}

	@Override
	public ResponseDTO<EntityDTO> editarSistema(Long id, SistemaDTO editarSistema) {
		return service.editarSistema(id, editarSistema);
	}

	@Override
	public ResponseDTO<EntityDTO> excluirSistema(Long id) {
		return service.excluirSistema(id);
	}

	@Override
	public List<SelecaoDTO> consultarSelecao() {
		return service.consultarSelecao();
	}

	@Override
	public Response gerarExcel(FiltroPesquisaSistemaDTO pesquisarSistema) {
		return service.gerarExcel(pesquisarSistema);
	}

	@Override
	public Response gerarCsv(FiltroPesquisaSistemaDTO pesquisarSistema) {
		return service.gerarCsv(pesquisarSistema);
	}
	
	@Override
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(FiltroPesquisaLogSistemaDTO revisaoDTO) {
		return service.consultarHistoricoRevisoes(revisaoDTO);
	}
	
	@Override
	public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {
		
		return service.consultarDetalheRevisao(id);
	}

    @Override
    public List<VisualizarSistemaDTO> consultarSistema() {
      return service.consultarSistema();
    }
    
    @Override
    public Response gerarLogCsv(FiltroPesquisaLogSistemaDTO filtro) {
      return service.gerarLogCsv(filtro);
    }

    @Override
    public Response gerarLogExcel(FiltroPesquisaLogSistemaDTO filtro) {
      return service.gerarLogExcel(filtro);
    }

}
