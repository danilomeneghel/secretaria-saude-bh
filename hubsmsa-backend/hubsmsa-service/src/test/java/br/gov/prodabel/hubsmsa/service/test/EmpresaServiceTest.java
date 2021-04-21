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
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.DadosEmpresaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaLogEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.EmpresaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.EmpresaService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

@RunWith(MockitoJUnitRunner.class)
public class EmpresaServiceTest {

  private EmpresaService empresaService;

  @Mock
  private EmpresaDAO empresaDao;

  @Mock
  private UsuarioService usuarioService;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.empresaService = new EmpresaService();
    this.empresaService.setEmpresaDAO(empresaDao);
    this.empresaService.setDaoGeneric(Mockito.mock(EmpresaDAO.class));
    this.empresaService.setUsuarioService(usuarioService);
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogEmpresaDTO filtrosPesquisa = montarFiltroLogEmpresaDTO();
    try {
      Mockito.when(empresaDao.consultarLog(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosEmpresaAudDTO());
      Mockito.when(empresaDao.montarHistorico(mockHistoricoDadosEmpresaAudDTO()))
          .thenReturn(mockHistoricoEmpresaDTO());
      Mockito.when(usuarioService.consultarUsuario("login")).thenReturn(mockUsuario());
    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.empresaService.gerarHistoricoCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogEmpresaDTO filtrosPesquisa = montarFiltroLogEmpresaDTO();
    try {
      Mockito.when(empresaDao.consultarLog(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosEmpresaAudDTO());
      Mockito.when(empresaDao.montarHistorico(mockHistoricoDadosEmpresaAudDTO()))
          .thenReturn(mockHistoricoEmpresaDTO());
    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.empresaService.gerarHistoricoExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogEmpresaDTO montarFiltroLogEmpresaDTO() {
    FiltroPesquisaLogEmpresaDTO filtro = new FiltroPesquisaLogEmpresaDTO();
    filtro.setDataInicial("01/11/2020");
    filtro.setDataFinal("30/11/2020");
    filtro.setIdEmpresa(1L);
    return filtro;
  }

  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<DadosEmpresaAudDTO> mockHistoricoDadosEmpresaAudDTO() {
    DadosEmpresaAudDTO empresaAudDTO = new DadosEmpresaAudDTO();
    empresaAudDTO.setCnpj("11111111111");
    empresaAudDTO.setCodigoCnes(1L);
    empresaAudDTO.setId(1L);
    empresaAudDTO.setNomeEmpresarial("nome empresarial");
    empresaAudDTO.setNomeFantasia("nome fantasia");
    empresaAudDTO.setRevisao(revision());
    empresaAudDTO.setAtivo(StatusEnum.A);
    empresaAudDTO.setRevType(RevisionType.ADD);
    return Arrays.asList(empresaAudDTO);
  }
  
  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<HistoricoEmpresaDTO> mockHistoricoEmpresaDTO() {
    HistoricoEmpresaDTO historicoEmpresaDTO = new HistoricoEmpresaDTO();
    historicoEmpresaDTO.setCnes(1L);
    historicoEmpresaDTO.setDataEvento("01/01/2020");
    historicoEmpresaDTO.setEmail("email@email.com.br");
    historicoEmpresaDTO.setNomeEmpresarial("nome empresarial");
    historicoEmpresaDTO.setNomeFantasia("nome fantasia");
    historicoEmpresaDTO.setSite("site");
    historicoEmpresaDTO.setId(1L);
    historicoEmpresaDTO.setLogin("login");
    historicoEmpresaDTO.setNomeUsuario("nomeUsuario");
    historicoEmpresaDTO.setRevisao("1234");
    historicoEmpresaDTO.setStatus("Sim");
    historicoEmpresaDTO.setTipoRevisao("Inclusão");

    return Arrays.asList(historicoEmpresaDTO);
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
