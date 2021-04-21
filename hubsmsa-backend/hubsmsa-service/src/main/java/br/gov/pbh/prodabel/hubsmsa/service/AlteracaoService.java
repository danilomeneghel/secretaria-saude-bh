package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.commons.lang.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroPesquisaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroRevisaoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.PaginacaoPublicaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.VisualizarAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ExemploDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ExemploMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.util.AlteracaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

@LocalBean
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AlteracaoService extends GenericService<Long, Exemplo> {

	@EJB
	private ExemploDAO exemploDAO;
	
	public PaginacaoPublicaAlteracaoDTO<HistoricoAlteracaoDTO> consultarAlteracoes(Long id, FiltroRevisaoAlteracaoDTO filtro) {
		PaginacaoPublicaAlteracaoDTO<HistoricoAlteracaoDTO> paginacao = new PaginacaoPublicaAlteracaoDTO<>();		
		paginacao.setAssunto(AssuntoEnum.valueOf(filtro.getAssunto()));		
		List<HistoricoAlteracaoDTO> historico = null;
		try {
			switch(AssuntoEnum.valueOf(filtro.getAssunto())) {			
			case EX:
				historico = RevisionMapper.mapperRevisions(exemploDAO.consultarEntidadesRevisaoPorId(id), filtro);		
				paginacao.setDescricao(exemploDAO.consultarPorId(id).getDescricao());
				break;			
			}
			paginacao.setItens(PaginacaoUtil.definirPaginacao(historico, filtro.getNumeroPagina(), filtro.getItensPorPagina()));
			paginacao.setTotalRegistros(PaginacaoUtil.definirTotalRegistros(historico));
			
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
		return paginacao;
	}

	public PaginacaoPublicaDTO<VisualizarAlteracaoDTO> consultarAlteracoes(FiltroPesquisaAlteracaoDTO filtroDTO) {
		String assunto = filtroDTO.getAssunto();
		PaginacaoPublicaDTO<VisualizarAlteracaoDTO> paginacao = new PaginacaoPublicaDTO<>();
		List<VisualizarAlteracaoDTO> alteracoes = null;
		try {
			switch(AssuntoEnum.valueOf(assunto)) {		
			
			case EX:
				alteracoes = filtrarAlteracoes(ExemploMapper.mapperAlteracoes(exemploDAO.consultarAlteracoes(filtroDTO), filtroDTO));			
				break;	
				
			}			
			ValidadorUtil.validarNoResultList(alteracoes);
			AlteracaoUtil.ordenarListaAlteracao(alteracoes, filtroDTO.getOrderBy(), filtroDTO.getTipoOrdenacao());
			paginacao.setItens(PaginacaoUtil.definirPaginacao(alteracoes, filtroDTO.getNumeroPagina(), filtroDTO.getItensPorPagina()));
			paginacao.setTotalRegistros(PaginacaoUtil.definirTotalRegistros(alteracoes));
			
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
		return paginacao;
	}
	
	public byte[] gerarExcel(FiltroPesquisaAlteracaoDTO filtroDTO) {
		try {			
			return gerarRelatorio(filtroDTO, Boolean.TRUE);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	public byte[] gerarPdf(FiltroPesquisaAlteracaoDTO filtroDTO) {
		try {
			return gerarRelatorio(filtroDTO, Boolean.FALSE);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}
	
	public byte[] gerarExcelDetalhado(Long id, FiltroRevisaoAlteracaoDTO filtroDTO) {
		try {			
			return gerarRelatorioDetalhado(id, filtroDTO, Boolean.TRUE);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	public byte[] gerarPdfDetalhado(Long id, FiltroRevisaoAlteracaoDTO filtroDTO) {
		try {
			return gerarRelatorioDetalhado(id, filtroDTO, Boolean.FALSE);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}
	
	private byte[] gerarRelatorio(FiltroPesquisaAlteracaoDTO filtroDTO, boolean isExcel) throws RegistroNaoEncontradoException, DAOException {		
		List<VisualizarAlteracaoDTO> alteracoes = new ArrayList<>();
		String assunto = filtroDTO.getAssunto();
		switch(AssuntoEnum.valueOf(assunto)) {	
		
		case EX:
			alteracoes = filtrarAlteracoes(ExemploMapper.mapperAlteracoes(exemploDAO.consultarAlteracoes(filtroDTO), filtroDTO));
			break;	
			
		}
		ValidadorUtil.validarNoResultList(alteracoes);
		AlteracaoUtil.ordenarListaAlteracao(alteracoes, filtroDTO.getOrderBy(), filtroDTO.getTipoOrdenacao());
		if(isExcel) {
			return AlteracaoUtil.gerarExcel(alteracoes);
		}
		return AlteracaoUtil.gerarPdf(alteracoes, filtroDTO);
	}
	
	private byte[] gerarRelatorioDetalhado(Long id, FiltroRevisaoAlteracaoDTO filtro, boolean isExcel) throws RegistroNaoEncontradoException, DAOException {		
		List<HistoricoAlteracaoDTO> alteracoes = new ArrayList<>();
		String entidadeNome = StringUtils.EMPTY;
		switch(AssuntoEnum.valueOf(filtro.getAssunto())) {
		
		case EX:
			alteracoes = RevisionMapper.mapperRevisions(exemploDAO.consultarEntidadesRevisaoPorId(id), filtro);
			entidadeNome = exemploDAO.consultarPorId(id).getDescricao();
			break;
			
		}
		if(isExcel) {
			return AlteracaoUtil.gerarExcelDetalhado(alteracoes, filtro.getAssunto(), entidadeNome);
		}
		return AlteracaoUtil.gerarPdfDetalhado(alteracoes, filtro.getAssunto(), entidadeNome);
	}
	
	private List<VisualizarAlteracaoDTO> filtrarAlteracoes(List<VisualizarAlteracaoDTO> alteracoes) {		
		return alteracoes.stream().collect(Collectors.groupingBy(VisualizarAlteracaoDTO::getId, Collectors.toCollection(
			            () -> new TreeSet<>((a, b) -> a.getDataAlteracao().compareTo(b.getDataAlteracao()))
			        )
			    )
			).values().stream().map(TreeSet::last).collect(Collectors.toList());	
	}

	public void setExemploDAO(ExemploDAO exemploDAO) {
		this.exemploDAO = exemploDAO;
	}	
}
