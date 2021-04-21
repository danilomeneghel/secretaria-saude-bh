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
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.DadosContatoEmpresaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaLogContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ContatoEmpresaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaService;
import br.gov.pbh.prodabel.hubsmsa.service.UsuarioService;

// TODO: Auto-generated Javadoc
/**
 * @author weverton.lucena@cits.com.br
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ContatoEmpresaServiceTest {

  private ContatoEmpresaService contatoEmpresaService;

  @Mock
  private ContatoEmpresaDAO contatoEmpresaDAO;

  @Mock
  private UsuarioService usuarioService;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.contatoEmpresaService = new ContatoEmpresaService();
    this.contatoEmpresaService.setContatoEmpresaDAO(contatoEmpresaDAO);
    this.contatoEmpresaService
        .setDaoGeneric(Mockito.mock(ContatoEmpresaDAO.class));
    this.contatoEmpresaService.setUsuarioService(usuarioService);
  }

  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogCsvTest() {
    FiltroPesquisaLogContatoEmpresaDTO filtrosPesquisa = montarFiltroPesquisaLogContatoEmpresaDTO();
    try {
      Mockito.when(contatoEmpresaDAO.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(contatoEmpresaDAO.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoContatoEmpresaDTO());
      Mockito.when(usuarioService.consultarUsuario("login")).thenReturn(mockUsuario());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.contatoEmpresaService.gerarLogCsv(filtrosPesquisa));
  }




  /**
   * Gerar excel EX.
   */
  @Test
  public void gerarLogExcelTest() {
    FiltroPesquisaLogContatoEmpresaDTO filtrosPesquisa = montarFiltroPesquisaLogContatoEmpresaDTO();
    try {
      Mockito.when(contatoEmpresaDAO.consultarListaEntidades(filtrosPesquisa))
          .thenReturn(mockHistoricoDadosTipodeParaAudDTO());
      Mockito.when(contatoEmpresaDAO.montarHistorico(mockHistoricoDadosTipodeParaAudDTO()))
          .thenReturn(mockHistoricoContatoEmpresaDTO());
    } catch (RegistroNaoEncontradoException e) {
      assumeNoException(e);
    }
    assertNotNull(this.contatoEmpresaService.gerarLogExcel(filtrosPesquisa));
  }

  /**
   * Montar filtro log tipo de para DTO.
   *
   * @return the filtro pesquisa log tipo de para DTO
   */
  private FiltroPesquisaLogContatoEmpresaDTO montarFiltroPesquisaLogContatoEmpresaDTO() {
    FiltroPesquisaLogContatoEmpresaDTO filtro = new FiltroPesquisaLogContatoEmpresaDTO();
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
  private List<DadosContatoEmpresaAudDTO> mockHistoricoDadosTipodeParaAudDTO() {
    DadosContatoEmpresaAudDTO contatoEmpresaAudDTO = new DadosContatoEmpresaAudDTO();

    contatoEmpresaAudDTO.setSetor("TI");
    contatoEmpresaAudDTO.setTelefone("11999999999");
    contatoEmpresaAudDTO.setId(1L);
    contatoEmpresaAudDTO.setNome("nome");
    contatoEmpresaAudDTO.setRevisao(revision());
    contatoEmpresaAudDTO.setStatus(StatusEnum.A);
    contatoEmpresaAudDTO.setRevType(RevisionType.ADD);
    return Arrays.asList(contatoEmpresaAudDTO);
  }
  
  /**
   * Mock historico tipo de para DTO.
   *
   * @return the list
   */
  private List<HistoricoContatoEmpresaDTO> mockHistoricoContatoEmpresaDTO() {
    HistoricoContatoEmpresaDTO historicoTipoDeParaDTO = new HistoricoContatoEmpresaDTO();

    historicoTipoDeParaDTO.setDataEvento("01/01/2020");
    historicoTipoDeParaDTO.setEmail("email@email.com.br");
    historicoTipoDeParaDTO.setNome("nome");
    historicoTipoDeParaDTO.setIdContatoEmpresa(1L);
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
