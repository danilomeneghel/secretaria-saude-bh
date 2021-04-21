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
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaLogTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.TipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.VisualizarTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.TipoDeParaEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.TipoDeParaService;

public class TipoDeParaEndPointImpl implements TipoDeParaEndPoint {

	@EJB
	private TipoDeParaService tipoDeParaService;

	@Override
	public PaginacaoPublicaDTO<VisualizarTipoDeParaDTO> consultarTipoDePara(
			FiltroPesquisaTipoDeParaDTO pesquisarTipoDePara) {
		return tipoDeParaService.consultarTiposDePara(pesquisarTipoDePara);
	}

	@Override
	public ResponseDTO<EntityDTO> cadastrarTipoDePara(TipoDeParaDTO tipoDeParaDTO) {
		return tipoDeParaService.cadastrarTipoDePara(tipoDeParaDTO);
	}

	@Override
	public ResponseDTO<EntityDTO> editarTipoDePara(Long id, TipoDeParaDTO editarTipoDeParaDTO) {
		return tipoDeParaService.editarTipoDePara(id, editarTipoDeParaDTO);
	}

	@Override
	public VisualizarTipoDeParaDTO consultarTipoDePara(Long id) {
		return tipoDeParaService.consultarTipoDePara(id);
	}

	@Override
	public ResponseDTO<EntityDTO> excluirTipoDePara(Long id) {
		return tipoDeParaService.excluirTipoDePara(id);
	}

	@Override
	public List<SelecaoDTO> consultarSelecao() {
		return tipoDeParaService.consultarSelecao();
	}
	
	@Override
	public Response gerarExcel(FiltroPesquisaTipoDeParaDTO filtroTipoDePara) {
		return tipoDeParaService.gerarExcel(filtroTipoDePara);
	}

	@Override
	public Response gerarCsv(FiltroPesquisaTipoDeParaDTO filtroTipoDePara) {
		return tipoDeParaService.gerarCsv(filtroTipoDePara);
	}

	@Override
    public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogTipoDePara(
        FiltroPesquisaLogTipoDeParaDTO revisaoDTO) {
		return tipoDeParaService.consultarLogTipoDePara(revisaoDTO);
	}

    @Override
    public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {
      return tipoDeParaService.consultarDetalheRevisao(id);
    }

    @Override
    public Response gerarLogCsv(FiltroPesquisaLogTipoDeParaDTO filtro) {
      return tipoDeParaService.gerarLogCsv(filtro);
    }

    @Override
    public Response gerarLogExcel(FiltroPesquisaLogTipoDeParaDTO filtro) {
      return tipoDeParaService.gerarLogExcel(filtro);
    }

}
