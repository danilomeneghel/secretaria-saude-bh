package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.time.LocalDate;
import java.util.Arrays;
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
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.CadastrarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.EditarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoRevisaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ExemploDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.service.ExemploService;

@RunWith(MockitoJUnitRunner.class)
public class ExemploServiceTest {

  private ExemploService exemploService;

  @Mock
  private ExemploDAO exemploDAO;


  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.exemploService = new ExemploService();
    this.exemploService.setExemploDAO(this.exemploDAO);
    this.exemploService.setDaoGeneric(Mockito.mock(ExemploDAO.class));
  }


  @Test
  public void consultarExemploTest() {
    FiltroPesquisaExemploDTO filtrosPesquisa = this.montarFiltrosPesquisaExemplo();

    try {
      Mockito.when(this.exemploDAO.consultarExemplo(filtrosPesquisa)).thenReturn(this.mockListaExemplo());
      Mockito.when(this.exemploDAO.consultarTotalRegistros(filtrosPesquisa)).thenReturn(1);
    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.exemploService.consultarExemplo(filtrosPesquisa));
  }

  @Test(expected = NegocioException.class)
  public void consultarExemploRegistroNaoEncontradoTest() throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaExemploDTO filtrosPesquisa = this.montarFiltrosPesquisaExemplo();

    Mockito.doThrow(new RegistroNaoEncontradoException()).when(this.exemploDAO).consultarExemplo(filtrosPesquisa);

    this.exemploService.consultarExemplo(filtrosPesquisa);
  }

  @Test(expected = NegocioException.class)
  public void consultarGrupoLocalDAOExceptionTest() throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaExemploDTO filtrosPesquisa = this.montarFiltrosPesquisaExemplo();

    Mockito.doThrow(new DAOException()).when(this.exemploDAO).consultarExemplo(filtrosPesquisa);

    this.exemploService.consultarExemplo(filtrosPesquisa);
  }

  @Test
  public void cadastrarExemploTest() {
    CadastrarExemploDTO exemploDTO = this.montarCadastroDTO();

    try {
      Mockito.when(this.exemploDAO.verificarNomeExistente("nome")).thenReturn(Boolean.FALSE);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.exemploService.cadastrarExemplo(exemploDTO));
  }

  @Test(expected = NegocioException.class)
  public void cadastrarNomeExemploJaExisteTest() {
    CadastrarExemploDTO cadastroDTO = this.montarCadastroDTO();

    try {
      Mockito.when(this.exemploDAO.verificarNomeExistente("nome")).thenReturn(Boolean.TRUE);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    this.exemploService.cadastrarExemplo(cadastroDTO);
  }

  @Test(expected = NegocioException.class)
  public void cadastrarCodigoExemploJaExisteTest() {
    CadastrarExemploDTO cadastroDTO = this.montarCadastroDTO();

    try {
      Mockito.when(this.exemploDAO.verificarSeCodigoExiste("codigo")).thenReturn(Boolean.TRUE);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    this.exemploService.cadastrarExemplo(cadastroDTO);
  }


  @Test(expected = NegocioException.class)
  public void cadastrarNomeExemploDaoExceptionTest() throws DAOException {
    CadastrarExemploDTO cadastroDTO = this.montarCadastroDTO();

    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarNomeExistente("nome");

    this.exemploService.cadastrarExemplo(cadastroDTO);
  }

  @Test(expected = NegocioException.class)
  public void cadastrarCodigoExemploDaoExceptionTest() throws DAOException {
    CadastrarExemploDTO cadastroDTO = this.montarCadastroDTO();

    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarSeCodigoExiste("codigo");

    this.exemploService.cadastrarExemplo(cadastroDTO);
  }
  @Test
  public void editarExemploTest() {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();

    try {
      Mockito.when(this.exemploDAO.consultarPorId(id)).thenReturn(this.mockExemplo());
      Mockito.when(this.exemploDAO.verificarNomeExistente("nome")).thenReturn(Boolean.FALSE);
      Mockito.when(this.exemploDAO.verificarSeCodigoExiste("codigo")).thenReturn(Boolean.FALSE);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.exemploService.editarExemplo(editarDTO, id));
  }

  public void editarExemploComVinculoTest() {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();

    try {
      Mockito.when(this.exemploDAO.consultarPorId(id)).thenReturn(this.mockExemplo());
      Mockito.when(this.exemploDAO.verificarNomeExistente("nome")).thenReturn(Boolean.FALSE);
      Mockito.when(this.exemploDAO.verificarSeCodigoExiste("codigo")).thenReturn(Boolean.FALSE);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.exemploService.editarExemplo(editarDTO, id));
  }

  @Test(expected = NegocioException.class)
  public void exemploComVinculoDAOExceptionTest() {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();
    Mockito.when(this.exemploDAO.consultarPorId(id)).thenReturn(null);
    assertNotNull(this.exemploService.editarExemplo(editarDTO, id));
  }

  @Test
  public void consultarExemploPorIdTest() {
    Mockito.when(this.exemploDAO.consultarPorId(Matchers.anyLong())).thenReturn(this.mockExemplo());
    this.exemploService.consultarExemplo(Matchers.anyLong());
  }

  @Test(expected = Exception.class)
  public void consultarExemploPorIdDAOExceptionTest() throws Exception {
    Mockito.doThrow(new Exception()).when(this.exemploDAO).consultarPorId(1L);
    this.exemploService.consultarExemplo(1L);
  }

  @Test(expected = NegocioException.class)
  public void consultarExemploPorIdRegistroNaoEncontradoExceptionTest() throws RegistroNaoEncontradoException{
    Mockito.when(this.exemploDAO.consultarPorId(Matchers.anyLong())).thenReturn(null);
    this.exemploService.consultarExemplo(1L);
  }

  @Test(expected = NegocioException.class)
  public void editarExemploRegistroNaoEncontradoTest() {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();
    try {
      Mockito.when(this.exemploDAO.consultarPorId(id)).thenReturn(null);
    } catch (Exception e) {
      assumeNoException(e);
    }
    this.exemploService.editarExemplo(editarDTO, id);
  }

  @Test(expected = NegocioException.class)
  public void editarExemploDAOExceptionTest() throws DAOException {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();
    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarNomeExistente("nome");
    this.exemploService.editarExemplo(editarDTO, id);
  }

  @Test(expected = NegocioException.class)
  public void editarCodigoExemploDAOExceptionTest() throws DAOException {
    Long id = 1L;
    EditarExemploDTO editarDTO = this.montarEditarDTO();
    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarSeCodigoExiste("codigo");
    this.exemploService.editarExemplo(editarDTO, id);
  }

  @Test(expected = NegocioException.class)
  public void excluirExemploTest() {
    Long id = 1L;
    assertNotNull(this.exemploService.excluirExemplo(id));
  }

  @Test(expected = NegocioException.class)
  public void excluirExemploDAOExceptionTest() throws DAOException {
    Long id = 1L;
    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarNomeExistente("nome");
    Mockito.doThrow(new DAOException()).when(this.exemploDAO).verificarSeCodigoExiste("codigo");
    this.exemploService.excluirExemplo(id);
  }

  @Test(expected = Exception.class)
  public void excluirExemploIdNaoEncontaradoTest() {
    Long id = 1L;
    Mockito.doThrow(new Exception()).when(this.exemploDAO).consultarPorId(id);
    this.exemploService.excluirExemplo(id);
  }

  @Test(expected = NegocioException.class)
  public void consultarHistoricoRevisaoServiceExceptionTest() throws RegistroNaoEncontradoException, Exception{
    FiltroRevisaoDTO revisao = this.montarRevisao();
    Mockito.when(this.exemploDAO.consultarEntidadesRevisaoPorId(1L)).thenReturn(this.mockHistoricoAlteracaoDTO());
    this.exemploService.consultarHistoricoRevisoes(1L, revisao);
  }

  @Test
  public void consultarHistoricoRevisaoServiceTest() throws RegistroNaoEncontradoException, Exception{
    FiltroRevisaoDTO revisao = this.montarRevisaoCompleto();
    Mockito.when(this.exemploDAO.consultarEntidadesRevisaoPorId(1L)).thenReturn(this.mockHistoricoAlteracaoDTO());
    this.exemploService.consultarHistoricoRevisoes(1L, revisao);
  }

  private FiltroRevisaoDTO montarRevisao() {
    FiltroRevisaoDTO filtro = new FiltroRevisaoDTO();
    filtro.setItensPorPagina(1);
    filtro.setNumeroPagina(1);
    return filtro;
  }

  private FiltroRevisaoDTO montarRevisaoCompleto() {
    FiltroRevisaoDTO filtro = new FiltroRevisaoDTO();
    filtro.setItensPorPagina(1);
    filtro.setNumeroPagina(1);
    filtro.setOrderBy(ColunaOrdenacaoRevisaoEnum.DATAALTERACAODADOATUAL.name());
    filtro.setTipoOrdenacao("DESC");
    return filtro;
  }


  private List<EnversDTO> mockHistoricoAlteracaoDTO() {
    EnversDTO envers = new EnversDTO();
    envers.setIdRevision(1L);
    envers.setOldValue("old");
    envers.setValue("novo");
    return Arrays.asList(envers);
  }


  private EditarExemploDTO montarEditarDTO() {
    EditarExemploDTO dto = new EditarExemploDTO();
    dto.setNomeExemplo("nome");
    dto.setStatus("A");
    return dto;
  }
  private CadastrarExemploDTO montarCadastroDTO() {
    CadastrarExemploDTO dto = new CadastrarExemploDTO();
    dto.setCodigo("codigo");
    dto.setNomeExemplo("nome");
    dto.setStatus("A");
    return dto;
  }

  private Exemplo mockExemplo() {
    Exemplo exemplo = new Exemplo();
    exemplo.setCodigo("codigo");
    exemplo.setId(1L);
    exemplo.setDescricao("nomeGrupoLocal");
    exemplo.setStatus(StatusEnum.A);
    exemplo.setDataAtualizacao(LocalDate.now());
    exemplo.setFormaCadastro(FormaCadastroEnum.C);
    return exemplo;
  }

  private Exemplo mockExemploTipoCadastro() {
    Exemplo exemplo = new Exemplo();
    exemplo.setCodigo("codigo");
    exemplo.setId(1L);
    exemplo.setDescricao("nomeGrupoLocal");
    exemplo.setStatus(StatusEnum.A);
    exemplo.setDataAtualizacao(LocalDate.now());
    exemplo.setFormaCadastro(FormaCadastroEnum.I);
    return exemplo;
  }

  private List<Exemplo> mockListaExemplo() {
    Exemplo exemplo = new Exemplo();
    exemplo.setCodigo("codigo");
    exemplo.setId(1L);
    exemplo.setDescricao("nome");
    exemplo.setStatus(StatusEnum.A);
    return Arrays.asList(exemplo);
  }

  private FiltroPesquisaExemploDTO montarFiltrosPesquisaExemplo() {
    FiltroPesquisaExemploDTO filtro = new FiltroPesquisaExemploDTO();
    filtro.setNomeExemplo("nome");
    filtro.setStatus("A");
    filtro.setCodigo("codigo");
    filtro.setNumeroPagina(1);
    return filtro;
  }



}
