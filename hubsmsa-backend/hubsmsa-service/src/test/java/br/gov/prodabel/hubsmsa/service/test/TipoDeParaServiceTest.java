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
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.DadosTipodeParaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaLogTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.TipoDeParaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.TipoDeParaService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

// TODO: Auto-generated Javadoc
@RunWith(MockitoJUnitRunner.class)
public class TipoDeParaServiceTest {

  private TipoDeParaService tipoDeParaService;

  @Mock
  private TipoDeParaDAO tipoDeParaDao;

  @Mock
  private UsuarioService usuarioService;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.tipoDeParaService = new TipoDeParaService();
    this.tipoDeParaService.setTipoDeParaDAO(tipoDeParaDao);
    this.tipoDeParaService
        .setDaoGeneric(Mockito.mock(TipoDeParaDAO.class));
    this.tipoDeParaService.setUsuarioService(usuarioService);
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogTipoDeParaDTO filtrosPesquisa = montarFiltroLogTipoDeParaDTO();
    try {
      Mockito.when(tipoDeParaDao.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(tipoDeParaDao.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoTipoDeParaDTO());
      Mockito.when(usuarioService.consultarUsuario("login")).thenReturn(mockUsuario());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.tipoDeParaService.gerarLogCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogTipoDeParaDTO filtrosPesquisa = montarFiltroLogTipoDeParaDTO();
    try {
      Mockito.when(tipoDeParaDao.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(tipoDeParaDao.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoTipoDeParaDTO());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.tipoDeParaService.gerarLogExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogTipoDeParaDTO montarFiltroLogTipoDeParaDTO() {
    FiltroPesquisaLogTipoDeParaDTO filtro = new FiltroPesquisaLogTipoDeParaDTO();
    filtro.setDataInicial("01/11/2020");
    filtro.setDataFinal("30/11/2020");
    filtro.setNome("teste");
    return filtro;
  }

  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<DadosTipodeParaAudDTO> mockHistoricoDadosTipodeParaAudDTO() {
    DadosTipodeParaAudDTO tipoDeParaAudDTO = new DadosTipodeParaAudDTO();
    tipoDeParaAudDTO.setDescricao("descricao");
    tipoDeParaAudDTO.setFormaCadastro(FormaCadastroEnum.A);
    tipoDeParaAudDTO.setId(1L);
    tipoDeParaAudDTO.setNome("nome");
    tipoDeParaAudDTO.setRevisao(revision());
    tipoDeParaAudDTO.setStatus(StatusEnum.A);
    tipoDeParaAudDTO.setRevType(RevisionType.ADD);
    return Arrays.asList(tipoDeParaAudDTO);
  }
  
  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<HistoricoTipoDeParaDTO> mockHistoricoTipoDeParaDTO() {
    HistoricoTipoDeParaDTO historicoTipoDeParaDTO = new HistoricoTipoDeParaDTO();
    historicoTipoDeParaDTO.setDescricao("descricao");
    historicoTipoDeParaDTO.setDataEvento("01/01/2020");
    historicoTipoDeParaDTO.setEmail("email@email.com.br");
    historicoTipoDeParaDTO.setNome("nome");
    historicoTipoDeParaDTO.setIdTipoDePara(1L);
    historicoTipoDeParaDTO.setLogin("login");
    historicoTipoDeParaDTO.setNomeUsuario("nomeUsuario");
    historicoTipoDeParaDTO.setRevisao("1234");
    historicoTipoDeParaDTO.setStatus("Sim");
    historicoTipoDeParaDTO.setTipoRevisao("Inclusão");

    return Arrays.asList(historicoTipoDeParaDTO);
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
