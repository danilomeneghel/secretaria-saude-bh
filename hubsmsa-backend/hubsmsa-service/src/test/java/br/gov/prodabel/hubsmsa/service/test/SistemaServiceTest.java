package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.hibernate.envers.RevisionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.DadosSistemaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaLogSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.SistemaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

@RunWith(MockitoJUnitRunner.class)
public class SistemaServiceTest {

  private SistemaService sistemaService;

  @Mock
  private SistemaDAO sistemaDao;

  @Mock
  private UsuarioService usuarioService;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.sistemaService = new SistemaService();
    this.sistemaService.setSistemaDAO(sistemaDao);
    this.sistemaService.setDaoGeneric(Mockito.mock(SistemaDAO.class));
    this.sistemaService.setUsuarioService(usuarioService);
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogSistemaDTO filtrosPesquisa = montarFiltroLogSistemaDTO();
    try {
      Mockito.when(sistemaDao.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(sistemaDao.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoSistemaDTO());
      Mockito.when(usuarioService.consultarUsuario("login")).thenReturn(mockUsuario());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.sistemaService.gerarLogCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogSistemaDTO filtrosPesquisa = montarFiltroLogSistemaDTO();
    try {
      Mockito.when(sistemaDao.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(sistemaDao.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoSistemaDTO());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.sistemaService.gerarLogExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogSistemaDTO montarFiltroLogSistemaDTO() {
    FiltroPesquisaLogSistemaDTO filtro = new FiltroPesquisaLogSistemaDTO();
    filtro.setDataInicial("01/11/2020");
    filtro.setDataFinal("30/11/2020");
    filtro.setNome("nome");
    filtro.setIdEmpresa(1L);
    return filtro;
  }

  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<DadosSistemaAudDTO> mockHistoricoDadosTipodeParaAudDTO() {
    DadosSistemaAudDTO sistemaAudDTO = new DadosSistemaAudDTO();
    sistemaAudDTO.setDescricao("descricao");
    sistemaAudDTO.setFormaCadastro(FormaCadastroEnum.A);
    sistemaAudDTO.setId(1L);
    sistemaAudDTO.setNome("nome");
    sistemaAudDTO.setRevisao(revision());
    sistemaAudDTO.setStatus(StatusEnum.A);
    sistemaAudDTO.setRevType(RevisionType.ADD);
    sistemaAudDTO.setNomeEmpresa("nomeEmpresa");
    return Arrays.asList(sistemaAudDTO);
  }
  
  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<HistoricoSistemaDTO> mockHistoricoSistemaDTO() {
    HistoricoSistemaDTO historicoSistemaDTO = new HistoricoSistemaDTO();
    historicoSistemaDTO.setDescricao("descricao");
    historicoSistemaDTO.setDataEvento("01/01/2020");
    historicoSistemaDTO.setEmail("email@email.com.br");
    historicoSistemaDTO.setNome("nome");
    historicoSistemaDTO.setId(1L);
    historicoSistemaDTO.setLogin("login");
    historicoSistemaDTO.setNomeUsuario("nomeUsuario");
    historicoSistemaDTO.setRevisao("1234");
    historicoSistemaDTO.setStatus("Sim");
    historicoSistemaDTO.setTipoRevisao("Inclusão");
    historicoSistemaDTO.setNomeEmpresa("nomeEmpresa");

    return Arrays.asList(historicoSistemaDTO);
  }


  /**
   * Revision.
   *
   * @return the revision
   */
  private Revision revision() {
    Revision revisao = new Revision();
    revisao.setDtRevisao(new Date().from(Instant.now()));
    revisao.setId(1L);
    revisao.setMatricula("login");
    return revisao;
  }


  /**
   * Mock usuario.
   *
   * @return the usuario
   */
  private Usuario mockUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("email@email.com.br");
    usuario.setLogin("login");
    usuario.setNome("usuario");
    return usuario;
  }

}
