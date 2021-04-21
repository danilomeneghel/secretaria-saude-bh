package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.util.RevisionUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;

// TODO: Auto-generated Javadoc
public final class RevisionMapper {
	
	/**
	 * Instantiates a new revision mapper.
	 */
	private RevisionMapper() {
	}

	/**
	 * Mapper revisions.
	 *
	 * @param findEntityChangesById the find entity changes by id
	 * @param filtro the filtro
	 * @return the list
	 */
	public static List<HistoricoAlteracaoDTO> mapperRevisions(List<EnversDTO> findEntityChangesById, FiltroRevisaoDTO filtro) {
		List<HistoricoAlteracaoDTO> revisoes = new ArrayList<>();
		for (EnversDTO enversDTO : findEntityChangesById) {
			HistoricoAlteracaoDTO historicoAlteracao = new HistoricoAlteracaoDTO();
			historicoAlteracao.setDadoAnterior(enversDTO.getOldValue());
			historicoAlteracao.setDadoAtual(enversDTO.getValue());
			historicoAlteracao.setIdRevisao(enversDTO.getIdRevision());
			historicoAlteracao.setDataAlteracaoDadoAtual(TimeUtil.formatarTipoDate(enversDTO.getDateRevision()));
			historicoAlteracao.setUsuarioResponsavelAlteracao(enversDTO.getUser());	
			historicoAlteracao.setEmpresa(enversDTO.getNomeEmpresa());
            historicoAlteracao.setTipoDePara(enversDTO.getTipoDePara());
            historicoAlteracao.setCampo(enversDTO.getField());
			revisoes.add(historicoAlteracao);
		}
		RevisionUtil.ordenarListaRevisao(revisoes, filtro.getOrderBy(), filtro.getTipoOrdenacao());
		return revisoes;
	}
	
	/**
	 * Mapper revision.
	 *
	 * @param enversDTO the envers DTO
	 * @return the historico alteracao DTO
	 */
	public static HistoricoAlteracaoDTO mapperRevision(EnversDTO enversDTO) {
		HistoricoAlteracaoDTO historicoAlteracao = new HistoricoAlteracaoDTO();
		historicoAlteracao.setCampo(enversDTO.getField());
		historicoAlteracao.setDadoAnterior(enversDTO.getOldValue());
		historicoAlteracao.setDadoAtual(enversDTO.getValue());
		historicoAlteracao.setIdRevisao(enversDTO.getIdRevision());
		historicoAlteracao.setDataAlteracaoDadoAtual(TimeUtil.formatarTipoDate(enversDTO.getDateRevision()));
		historicoAlteracao.setUsuarioResponsavelAlteracao(enversDTO.getUser());
		
		return historicoAlteracao;
		
	}
}
