package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroPesquisaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroRevisaoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoAlteracaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoRevisaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ExemploDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.GenericoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ExemploAud;
import br.gov.pbh.prodabel.hubsmsa.service.AlteracaoService;

@RunWith(MockitoJUnitRunner.class)
public class AlteracaoServiceTest {

	private AlteracaoService alteracaoService;

	@Mock
	private ExemploDAO exemploDAO;

	@Before
	public void init() {
		Locale locale = new Locale("pt", "BR");
		Locale.setDefault(locale);

		this.alteracaoService = new AlteracaoService();
		this.alteracaoService.setExemploDAO(exemploDAO);
		this.alteracaoService.setDaoGeneric(
				(GenericoDAO<Long, Exemplo>) Mockito.mock(ExemploDAO.class));
	}	
	
	@Test
	public void consultarAlteracoesEX() {
		Long id = 1L;
		FiltroRevisaoAlteracaoDTO filtrosPesquisa = montarFiltrosRevisaoPesquisaAlteracaoEX();
		try {
			Mockito.when(exemploDAO.consultarEntidadesRevisaoPorId(Matchers.anyLong()))
					.thenReturn(enverDTO());
			Mockito.when(exemploDAO.consultarPorId(Matchers.anyLong()))
					.thenReturn(mockExemplo());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.consultarAlteracoes(id, filtrosPesquisa));
	}	
	
	@Test
	public void consultarAlteracoesPesquisaEX() {
		FiltroPesquisaAlteracaoDTO filtrosPesquisa = montarFiltroPesquisaAlteracaoDTOEX();
		try {
			Mockito.when(exemploDAO.consultarAlteracoes(filtrosPesquisa))
					.thenReturn(mockAlteracoesEX());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.consultarAlteracoes(filtrosPesquisa));
	}
	
	@Test(expected = NegocioException.class)
	public void consultarAlteracoesPesquisaRegistroNaoEncontradoExceptionTest() throws RegistroNaoEncontradoException, DAOException {
		FiltroPesquisaAlteracaoDTO filtrosPesquisa = montarFiltroPesquisaAlteracaoDTOEX();
		Mockito.when(exemploDAO.consultarAlteracoes(filtrosPesquisa)).thenThrow(new RegistroNaoEncontradoException());
		assertNotNull(this.alteracaoService.consultarAlteracoes(filtrosPesquisa));
	}
	
	@Test(expected = NegocioException.class)
	public void consultarAlteracoesPesquisaDAOExceptionTest() throws RegistroNaoEncontradoException, DAOException {
		FiltroPesquisaAlteracaoDTO filtrosPesquisa = montarFiltroPesquisaAlteracaoDTOEX();
		Mockito.when(exemploDAO.consultarAlteracoes(filtrosPesquisa)).thenThrow(new DAOException());
		assertNotNull(this.alteracaoService.consultarAlteracoes(filtrosPesquisa));
	}
	
	@Test
	public void gerarExcelEX() {
		FiltroPesquisaAlteracaoDTO filtrosPesquisa = montarFiltroPesquisaAlteracaoDTOEX();
		try {
			Mockito.when(exemploDAO.consultarAlteracoes(filtrosPesquisa))
					.thenReturn(mockAlteracoesEX());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.gerarExcel(filtrosPesquisa));
	}	
	
	@Test
	public void gerarPdfEX() {
		FiltroPesquisaAlteracaoDTO filtrosPesquisa = montarFiltroPesquisaAlteracaoDTOEX();
		try {
			Mockito.when(exemploDAO.consultarAlteracoes(filtrosPesquisa))
					.thenReturn(mockAlteracoesEX());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.gerarPdf(filtrosPesquisa));
	}
	
	@Test
	public void gerarExcelDetalhadoEX() {
		FiltroRevisaoAlteracaoDTO filtrosPesquisa = montarFiltrosRevisaoPesquisaAlteracaoEX();
		try {
			Mockito.when(exemploDAO.consultarEntidadesRevisaoPorId(Matchers.anyLong())).thenReturn(enverDTO());
			Mockito.when(exemploDAO.consultarPorId(Matchers.anyLong())).thenReturn(mockExemplo());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.gerarExcelDetalhado(1L, filtrosPesquisa));
	}
	
	@Test
	public void gerarPdfDetalhadoEX() {
		FiltroRevisaoAlteracaoDTO filtrosPesquisa = montarFiltrosRevisaoPesquisaAlteracaoEX();
		try {
			Mockito.when(exemploDAO.consultarEntidadesRevisaoPorId(Matchers.anyLong())).thenReturn(enverDTO());
			Mockito.when(exemploDAO.consultarPorId(Matchers.anyLong())).thenReturn(mockExemplo());
		} catch (RegistroNaoEncontradoException | DAOException e) {
			assumeNoException(e);
		}
		assertNotNull(this.alteracaoService.gerarPdfDetalhado(1L, filtrosPesquisa));
	}
	
	private List<ExemploAud> mockAlteracoesEX() {
		ExemploAud et = new ExemploAud();
		et.setCodigo("codigo");
		et.setDataAtualizacao(LocalDate.now());
		et.setDescricao("descricao");
		et.setFormaCadastro(FormaCadastroEnum.A);
		et.setId(1L);
		et.setRevisao(revision());
		et.setStatus(StatusEnum.A);
		return Arrays.asList(et);
	}

	private Revision revision() {
		Revision revisao = new Revision();
		revisao.setDtRevisao(new Date().from(Instant.now()));
		revisao.setId(1L);
		revisao.setMatricula("123456");
		return revisao;
	}

	private FiltroPesquisaAlteracaoDTO montarFiltroPesquisaAlteracaoDTOEX() {
		FiltroPesquisaAlteracaoDTO filtro = new FiltroPesquisaAlteracaoDTO();
		filtro.setDataInicial("11/11/2011");
		filtro.setDataFinal("13/11/2012");
		filtro.setAssunto(AssuntoEnum.EX.toString());
		filtro.setNumeroPagina(1);
		filtro.setTipoOrdenacao("ASC");
		filtro.setOrderBy(ColunaOrdenacaoAlteracaoEnum.ASSUNTO.getName());
		filtro.setItensPorPagina(20);
		return filtro;
	}
	
	private Exemplo mockExemplo() {
		Exemplo exemplo = new Exemplo();
		exemplo.setCodigo("codigo");
		exemplo.setId(1L);
		exemplo.setDescricao("exemplo descricao");
		exemplo.setStatus(StatusEnum.A);
		exemplo.setDataAtualizacao(LocalDate.now());
		exemplo.setFormaCadastro(FormaCadastroEnum.C);
		return exemplo;
	}

	private List<HistoricoAlteracaoDTO> mockListaHistoricoAlteracoes() {
		HistoricoAlteracaoDTO alteracao = new HistoricoAlteracaoDTO();
		alteracao.setDadoAnterior("dadoAnterior");
		alteracao.setDadoAtual("dadoAtual");
		alteracao.setDataAlteracaoDadoAtual("01/10/1990");
		alteracao.setSistemaResponsavelAlteracao("sistema-exemplo");
		alteracao.setStatus(StatusEnum.A);
		alteracao.setUsuarioResponsavelAlteracao("user-exemplo");
		return Arrays.asList(alteracao);
	}

	private FiltroRevisaoAlteracaoDTO montarFiltrosRevisaoPesquisaAlteracaoEX() {
		FiltroRevisaoAlteracaoDTO filtro = new FiltroRevisaoAlteracaoDTO();
		filtro.setAssunto(AssuntoEnum.EX.toString());
		filtro.setNumeroPagina(1);
		filtro.setTipoOrdenacao("ASC");
		filtro.setOrderBy(ColunaOrdenacaoRevisaoEnum.DADOANTERIOR.getName());
		filtro.setItensPorPagina(20);
		return filtro;
	}

	private List<EnversDTO> enverDTO() {
		EnversDTO envers = new EnversDTO();
		envers.setDateRevision(Date.from(Instant.now()));
		envers.setEntityId(1L);
		envers.setEntityName("name");
		envers.setOldValue("antigo");
		envers.setValue("valor atual");
		envers.setUser("usuario-exemplo");
		return Arrays.asList(envers);
	}
}
