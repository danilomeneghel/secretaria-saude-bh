package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import utiil.MockUtil;


@RunWith(MockitoJUnitRunner.class)
public class ServicoServiceTest {

  private ServicoService servicoService;

  @Mock
  private ServicoDAO servicoDAO;

  /**
   * <<<<<<< HEAD Teste.
   * 
   * @throws DAOException
   */
  @Test
  public void consultarServico() throws DAOException {
    Mockito
        .when(
            servicoDAO.consultarServico(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(servicosMock());
    List<Servico> servicos = servicoService.consultarServico(1L, 2L, "Teste");
    assertEquals("Teste", servicos.get(0).getNome());
  }

  /**
   * Consultar servico erro.
   *
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarServicoErro() throws DAOException {
    Mockito
        .when(
            servicoDAO.consultarServico(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenThrow(new DAOException());
    servicoService.consultarServico(1L, 2L, "Teste");
  }

  /**
   * Servicos mock.
   *
   * @return the list
   */
  private List<Servico> servicosMock() {
    Servico servico = new Servico();
    servico.setDescricao("Descricao");
    servico.setId(1L);
    servico.setNome("Teste");
    servico.setSistemaPrimario(new Sistema());
    servico.setSistemaSecundario(new Sistema());
    servico.setStatus(StatusEnum.A);
    return Arrays.asList(servico);
  }


  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.servicoService = new ServicoService();
    this.servicoService.setServicoDAO(servicoDAO);
    this.servicoService.setDaoGeneric(Mockito.mock(ServicoDAO.class));
  }

  /**
   * Consultar servico test.
   */
  @Test
  public void consultarServicosTest() {
    try {
      Mockito.when(servicoDAO.consultaServicos()).thenReturn(mockServicos());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.servicoService.consultarServico());
  }

  /**
   * Consultar alteracoes pesquisa registro nao encontrado exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarServicosDaoExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    Mockito.when(servicoDAO.consultaServicos()).thenThrow(new DAOException());
    assertNotNull(this.servicoService.consultarServico());
  }

  /**
   * Consultar servico por sistema primario secundario test.
   */
  @Test
  public void consultarServicoPorSistemaPrimarioSecundarioTest() {
    Long id = 1L;
    String nome = "nome";
    try {
      Mockito.when(servicoDAO.consultarServico(id, id, nome)).thenReturn(mockServicos());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.servicoService.consultarServico(id, id, nome));
  }

  /**
   * Consultar servico por sistema primario secundario exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarServicoPorSistemaPrimarioSecundarioExceptionTest() throws DAOException {
    Long id = 1L;
    String nome = "nome";
    Mockito.when(servicoDAO.consultarServico(id, id, nome)).thenThrow(new DAOException());
    assertNotNull(this.servicoService.consultarServico(id, id, nome));
  }

  /**
   * Consultar servico por sistema primario ou sistema secundario test.
   */
  @Test
  public void consultarServicoPorSistemaPrimarioOuSistemaSecundarioTest() {
    try {
      Mockito.when(servicoDAO.consultarServicoPrimarioOuSecundario(1L)).thenReturn(mockServicos());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.servicoService.consultarServicos(1L));
  }

  /**
   * Consultar servico por sistema primario ou sistema secundario exception test.
   *
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarServicoPorSistemaPrimarioOuSistemaSecundarioExceptionTest()
      throws DAOException {
    Mockito.when(servicoDAO.consultarServicoPrimarioOuSecundario(1L)).thenThrow(new DAOException());
    assertNotNull(this.servicoService.consultarServicos(1L));
  }

  /**
   * Consultar servico por id.
   */
  @Test
  public void consultarServicoPorId() {
    try {
      Mockito.when(servicoDAO.consultaServico(1L)).thenReturn(MockUtil.mockServico());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.servicoService.consultarServico(1L));
  }

  /**
   * Mock servicos.
   *
   * @return the list
   */
  private List<Servico> mockServicos() {
    List<Servico> servicos = new ArrayList<Servico>();
    servicos.add(MockUtil.mockServico());
    return servicos;
  }
}
