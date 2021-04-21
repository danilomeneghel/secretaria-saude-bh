package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.FiltroPesquisaLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.LogAcessoUsuarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogAcessoUsuario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.LogAcessoUsuarioService;

// TODO: Auto-generated Javadoc
@RunWith(MockitoJUnitRunner.class)
public class LogAcessoUsuarioServiceTest {

  private LogAcessoUsuarioService logAcessoUsuarioService;

  @Mock
  private LogAcessoUsuarioDAO logAcessoUsuarioDAO;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.logAcessoUsuarioService = new LogAcessoUsuarioService();
    this.logAcessoUsuarioService.setlogAcessoUsuarioServiceDAO(logAcessoUsuarioDAO);
    this.logAcessoUsuarioService.setDaoGeneric(Mockito.mock(LogAcessoUsuarioDAO.class));
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogAcessoUsuarioDTO filtrosPesquisa = montarFiltroLogAcessoUsuarioDTO();
    try {
      Mockito.when(logAcessoUsuarioDAO.consultarLogAcessoUsuarioSemPaginacao(filtrosPesquisa))
          .thenReturn(mockLogAcessoUsuario());
    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.logAcessoUsuarioService.gerarCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogAcessoUsuarioDTO filtrosPesquisa = montarFiltroLogAcessoUsuarioDTO();
    try {
      Mockito.when(logAcessoUsuarioDAO.consultarLogAcessoUsuarioSemPaginacao(filtrosPesquisa))
          .thenReturn(mockLogAcessoUsuario());
    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.logAcessoUsuarioService.gerarExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogAcessoUsuarioDTO montarFiltroLogAcessoUsuarioDTO() {
    FiltroPesquisaLogAcessoUsuarioDTO filtro = new FiltroPesquisaLogAcessoUsuarioDTO();
    filtro.setDataInicial("01/11/2020");
    filtro.setDataFinal("30/11/2020");
    filtro.setIdUsuario(1L);
    return filtro;
  }

  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<LogAcessoUsuario> mockLogAcessoUsuario() {
    LogAcessoUsuario logAcessoUsuario = new LogAcessoUsuario();
    logAcessoUsuario.setDataAcesso(new Timestamp(System.currentTimeMillis()));
    logAcessoUsuario.setId(1L);
    logAcessoUsuario.setUsuario(new Usuario());
    return Arrays.asList(logAcessoUsuario);
  }
  

  /**
   * Mock visualizar log acesso usuario DTO.
   *
   * @return the list
   */
  private List<VisualizarLogAcessoUsuarioDTO> mockVisualizarLogAcessoUsuarioDTO() {
    VisualizarLogAcessoUsuarioDTO logAcessoDTO = new VisualizarLogAcessoUsuarioDTO();
    logAcessoDTO.setDataAcesso("01/01/2020");
    logAcessoDTO.setEmail("email@email.com.br");
    logAcessoDTO.setId(1L);
    logAcessoDTO.setLogin("login");
    logAcessoDTO.setNome("nomeUsuario");

    return Arrays.asList(logAcessoDTO);
  }

}
