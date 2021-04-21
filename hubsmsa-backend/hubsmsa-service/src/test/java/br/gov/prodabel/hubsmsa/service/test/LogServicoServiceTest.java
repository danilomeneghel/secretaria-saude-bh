package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.FiltroPesquisaLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.exception.ServicoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.LogServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.service.LogServicoService;
import utiil.MockUtil;

// TODO: Auto-generated Javadoc
@RunWith(MockitoJUnitRunner.class)
public class LogServicoServiceTest {

  private LogServicoService logServicoService;

  @Mock
  private LogServicoDAO logServicoDAO;

  private List<LogServico> logServicoListMock = new ArrayList<>();

  /**
   * Inits the.
   */
  @Before
  public void init() {
    logServicoService = new LogServicoService();
    this.logServicoService.setLogServicoDAO(logServicoDAO);
    LogServico logServico = new LogServico();
    logServico.setId(1L);
    logServico.setServico(new Servico());
    logServicoListMock.add(logServico);
  }

  /**
   * Buscar resquisicoes falhas OK.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test
  public void buscarResquisicoesFalhasOK() throws RegistroNaoEncontradoException, DAOException {
    Mockito.when(logServicoDAO.buscarResquisicoesFalhas()).thenReturn(logServicoListMock);
    logServicoService.buscarResquisicoesFalhas();
  }

  /**
   * Buscar resquisicoes falhas erro.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = ServicoException.class)
  public void buscarResquisicoesFalhasErro() throws RegistroNaoEncontradoException, DAOException {
    Mockito.when(logServicoDAO.buscarResquisicoesFalhas())
        .thenThrow(new RegistroNaoEncontradoException());
    logServicoService.buscarResquisicoesFalhas();
  }

  /**
   * Consultar log servico.
   */
  @Test
  public void consultarLogServico() {
    FiltroPesquisaLogServicoDTO filtrosPesquisa = montarFiltroPesquisaLogServicoDTO();
    try {
      Mockito.when(logServicoDAO.consultarLogServico(filtrosPesquisa))
          .thenReturn(logServicoList());

    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(
        this.logServicoService.consultarLogServico(filtrosPesquisa));
  }

  /**
   * Consultar log servico DAO exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarLogServicoRegistroNaoEncontradoExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaLogServicoDTO filtro = montarFiltroPesquisaLogServicoDTO();
    Mockito.when(logServicoDAO.consultarLogServico(filtro))
        .thenThrow(new RegistroNaoEncontradoException());
    assertNotNull(this.logServicoService.consultarLogServico(filtro));
  }

  /**
   * Consultar log servico DAO exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarLogServicoDAOExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaLogServicoDTO filtro = montarFiltroPesquisaLogServicoDTO();
    Mockito.when(logServicoDAO.consultarLogServico(filtro))
        .thenThrow(new DAOException());
    assertNotNull(this.logServicoService.consultarLogServico(filtro));
  }

  /**
   * Montar filtro pesquisa log servico DTO.
   *
   * @return the filtro pesquisa log servico DTO
   */
  private FiltroPesquisaLogServicoDTO montarFiltroPesquisaLogServicoDTO() {
    FiltroPesquisaLogServicoDTO filtro = new FiltroPesquisaLogServicoDTO();
    filtro.setDataInicial("2021-01-01 14:00:00");
    filtro.setDataFinal("2021-01-02 14:00:00");
    filtro.setIdServico(1L);
    filtro.setStatus(Arrays.asList(new String[] {"S", "F"}));
    filtro.setNumeroPagina(1);
    filtro.setTipoOrdenacao("ASC");
    filtro.setOrderBy(ColunaOrdenacaoContatoEmpresaServicoEnum.EMPRESA.getName());
    filtro.setItensPorPagina(5);
    return filtro;
  }

  /**
   * Log servico list.
   *
   * @return the list
   */
  private static List<LogServico> logServicoList() {
    List<LogServico> logServicoList = new ArrayList<LogServico>();
    logServicoList.add(MockUtil.mockLogServico());
    return logServicoList;
  }

}
