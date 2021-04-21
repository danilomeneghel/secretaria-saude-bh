package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.CadastrarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.EditarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.VisualizarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.ExemploEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.ExemploService;

public class ExemploEndPointImpl implements ExemploEndPoint {

	@EJB
	private ExemploService service;

	@Override
	public PaginacaoPublicaDTO<VisualizarExemploDTO> consultarExemplo(FiltroPesquisaExemploDTO pesquisarExemplo) {
		return service.consultarExemplo(pesquisarExemplo);
	}

	@Override
	public ResponseDTO<EntityDTO> cadastrarExemplo(CadastrarExemploDTO exemploDTO) {
		return service.cadastrarExemplo(exemploDTO);
	}

	@Override
	public ResponseDTO<EntityDTO> editarExemplo(Long id, EditarExemploDTO editarExemploDTO) {
		return service.editarExemplo(editarExemploDTO, id);
	}

	@Override
	public VisualizarExemploDTO consultarExemplo(Long id) {
		return service.consultarExemplo(id);
	}

	@Override
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(Long id, FiltroRevisaoDTO revisaoDTO) {
		return service.consultarHistoricoRevisoes(id, revisaoDTO);
	}

	@Override
	public ResponseDTO<EntityDTO> excluirExemplo(Long id) {
		return service.excluirExemplo(id);
	}

}
