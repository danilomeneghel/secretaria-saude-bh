package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
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
import br.gov.pbh.prodabel.hubsmsa.dto.depara.DeParaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.DeParaService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

/**
 * @author weverton.lucena@cits.com.br
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DeParaServiceTest {

  private DeParaService deParaService;

  @Mock
  private DeParaDAO deParaDAO;

  @Mock
  private UsuarioService usuarioService;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.deParaService = new DeParaService();
    this.deParaService.setDeParaDAO(deParaDAO);
    this.deParaService.setDaoGeneric(Mockito.mock(DeParaDAO.class));
    this.deParaService.setUsuarioService(usuarioService);
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogDeParaDTO filtrosPesquisa = montarFiltroPesquisaLogDeParaDTO();
    try {
      Mockito.when(deParaDAO.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDeParaAudDTO());
      Mockito.when(deParaDAO.montarHistorico(mockHistoricoDeParaAudDTO()))
          .thenReturn(mockHistoricoDeParaDTO());
      Mockito.when(usuarioService.consultarUsuario("login")).thenReturn(mockUsuario());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.deParaService.gerarLogCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogDeParaDTO filtrosPesquisa = montarFiltroPesquisaLogDeParaDTO();
    try {
      Mockito.when(deParaDAO.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDeParaAudDTO());
      Mockito.when(deParaDAO.montarHistorico(mockHistoricoDeParaAudDTO()))
          .thenReturn(mockHistoricoDeParaDTO());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.deParaService.gerarLogExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogDeParaDTO montarFiltroPesquisaLogDeParaDTO() {
    FiltroPesquisaLogDeParaDTO filtro = new FiltroPesquisaLogDeParaDTO();
    filtro.setDataInicial("01/11/2020");
    filtro.setDataFinal("30/11/2020");
    return filtro;
  }

  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<DeParaAudDTO> mockHistoricoDeParaAudDTO() {
    DeParaAudDTO deParaAudDTO = new DeParaAudDTO();
    deParaAudDTO.setId(1L);
    deParaAudDTO.setRevisao(revision());
    deParaAudDTO.setStatus(StatusEnum.A);
    deParaAudDTO.setRevType(RevisionType.ADD);
    return Arrays.asList(deParaAudDTO);
  }
  
  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<HistoricoDeParaDTO> mockHistoricoDeParaDTO() {
    HistoricoDeParaDTO historicoDePara = new HistoricoDeParaDTO();

    historicoDePara.setDataEvento("01/01/2020");
    historicoDePara.setLogin("login");
    historicoDePara.setNomeUsuario("nomeUsuario");
    historicoDePara.setIdRevisao(1234L);
    historicoDePara.setTipoRevisao("Inclusão");

    return Arrays.asList(historicoDePara);
  }


  /**
   * Revision.
   *
   * @return the revision
   */
  private Revision revision() {
    Revision revisao = new Revision();
    revisao.setDtRevisao(new Date());
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
