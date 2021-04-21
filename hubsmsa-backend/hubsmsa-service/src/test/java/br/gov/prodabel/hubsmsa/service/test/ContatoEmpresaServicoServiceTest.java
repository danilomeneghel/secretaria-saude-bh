package br.gov.prodabel.hubsmsa.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro.CadastrarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.FiltroPesquisaContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ContatoEmpresaServicoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ContatoEmpresaServicoMapper;
import utiil.MockUtil;

// TODO: Auto-generated Javadoc
/**
 * @author weverton.lucena@cits.com.br
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ContatoEmpresaServicoServiceTest {

  private ContatoEmpresaServicoService contatoEmpresaServicoService;

  @Mock
  private ContatoEmpresaServicoDAO contatoEmpresaServicoDao;




  /**
   * Inits the.
   */
  @Before
  public void init() {
    Locale locale = new Locale("pt", "BR");
    Locale.setDefault(locale);

    this.contatoEmpresaServicoService = new ContatoEmpresaServicoService();
    this.contatoEmpresaServicoService.setContatoEmpresaServicoDAO(contatoEmpresaServicoDao);
    this.contatoEmpresaServicoService
        .setDaoGeneric(Mockito.mock(ContatoEmpresaServicoDAO.class));
  }

  /**
   * Consultar contato empresa servico.
   */
  @Test
  public void consultarContatoEmpresaServico() {
    FiltroPesquisaContatoEmpresaServicoDTO filtrosPesquisa =
        montarFiltroPesquisaContatoEmpresaServicoDTO();
    try {
      Mockito.when(contatoEmpresaServicoDao.consultarContatoEmpresaServico(filtrosPesquisa))
          .thenReturn(contatoEmpresaServicoList());

    } catch (RegistroNaoEncontradoException | DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(
        this.contatoEmpresaServicoService.consultarContatoEmpresaServico(filtrosPesquisa));
  }

  /**
   * Consultar contato empresa servico exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarContatoEmpresaServicoDAOExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaContatoEmpresaServicoDTO filtrosPesquisa =
        montarFiltroPesquisaContatoEmpresaServicoDTO();
    Mockito.when(contatoEmpresaServicoDao.consultarContatoEmpresaServico(filtrosPesquisa))
        .thenThrow(new DAOException());
    assertNotNull(
        this.contatoEmpresaServicoService.consultarContatoEmpresaServico(filtrosPesquisa));
  }

  /**
   * Consultar contato empresa servico exception registro nao encontrado test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarContatoEmpresaServicoRegistroNaoEncontradoExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    FiltroPesquisaContatoEmpresaServicoDTO filtrosPesquisa =
        montarFiltroPesquisaContatoEmpresaServicoDTO();
    Mockito.when(contatoEmpresaServicoDao.consultarContatoEmpresaServico(filtrosPesquisa))
        .thenThrow(new RegistroNaoEncontradoException());
    assertNotNull(
        this.contatoEmpresaServicoService.consultarContatoEmpresaServico(filtrosPesquisa));
  }

  /**
   * Consultar contato empresa servico por id.
   */
  @Test
  public void consultarContatoEmpresaServicoPorId() {
    Long id = 1L;
    try {
      Mockito.when(contatoEmpresaServicoDao.consultaContatoEmpresaServicoPorId(id))
          .thenReturn(MockUtil.mockContatoEmpresaServico());

    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(
        this.contatoEmpresaServicoService.consultarContatoEmpresaServico(id));
  }

  /**
   * Consultar contato empresa servico por id DAO exception test.
   *
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void consultarContatoEmpresaServicoPorIdDAOExceptionTest()
      throws RegistroNaoEncontradoException, DAOException {
    Long id = 1L;
    Mockito.when(contatoEmpresaServicoDao.consultaContatoEmpresaServicoPorId(id))
        .thenThrow(new DAOException());
    assertNotNull(this.contatoEmpresaServicoService.consultarContatoEmpresaServico(id));
  }

  /**
   * Cadastrar contato empresa servico test.
   */
  @Test
  public void cadastrarContatoEmpresaServicoTest() {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    try {
      Mockito.when(
          this.contatoEmpresaServicoDao.verificarExistenciaContatoEmpresaParaMesmoServico(1L, 1L))
          .thenReturn(null);
    } catch (DAOException e) {
      assumeNoException(e);
    }
    assertNotNull(this.contatoEmpresaServicoService.cadastrarContatoEmpresaServico(cadastroDTO));
  }

  /**
   * Editar contato empresa servico test.
   */
  @Test
  public void editarContatoEmpresaServicoTest() {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    Mockito.when(
        this.contatoEmpresaServicoDao.verificarExistenciaContatoEmpresaParaMesmoServico(1L, 1L, 1L))
        .thenReturn(null);
    Mockito.when(this.contatoEmpresaServicoDao.consultarPorId(1L))
        .thenReturn(MockUtil.mockContatoEmpresaServico());
    assertNotNull(this.contatoEmpresaServicoService.editarContatoEmpresaService(1L, cadastroDTO));
  }

  /**
   * Cadastrar contato empresa servico test ja existe test.
   */
  @Test(expected = NegocioException.class)
  public void cadastrarContatoEmpresaServicoJaExisteParaMesmoServicoTest() {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    try {
      Mockito.when(
          this.contatoEmpresaServicoDao.verificarExistenciaContatoEmpresaParaMesmoServico(1L, 1L))
          .thenReturn(MockUtil.mockContatoEmpresaServico());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    this.contatoEmpresaServicoService.cadastrarContatoEmpresaServico(cadastroDTO);
  }

  /**
   * Editar contato empresa servico ja existe para mesmo servico test.
   */
  @Test(expected = NegocioException.class)
  public void editarContatoEmpresaServicoJaExisteParaMesmoServicoTest() {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    try {
      Mockito.when(
          this.contatoEmpresaServicoDao.verificarExistenciaContatoEmpresaParaMesmoServico(1L, 1L))
          .thenReturn(MockUtil.mockContatoEmpresaServico());
    } catch (DAOException e) {
      assumeNoException(e);
    }
    this.contatoEmpresaServicoService.cadastrarContatoEmpresaServico(cadastroDTO);
  }

  /**
   * Cadastrar codigo exemplo dao exception test.
   *
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void cadastrarContatoEmpresaServicoJaExisteParaMesmoServicoDaoExceptionTest()
      throws DAOException {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    Mockito.doThrow(new DAOException()).when(this.contatoEmpresaServicoDao)
        .verificarExistenciaContatoEmpresaParaMesmoServico(1L, 1L);

    this.contatoEmpresaServicoService.cadastrarContatoEmpresaServico(cadastroDTO);
  }

  /**
   * Cadastrar codigo exemplo persiste exception test.
   *
   * @throws DAOException the DAO exception
   */
  @Test(expected = NegocioException.class)
  public void cadastrarCodigoExemploPersisteExceptionTest() throws DAOException {
    CadastrarContatoEmpresaServicoDTO cadastroDTO = this.montarCadastroDTO();

    ContatoEmpresaServico contatoEmpresaServico = ContatoEmpresaServicoMapper.mapper(cadastroDTO);
    Mockito.doThrow(new PersistenceException())
        .when(this.contatoEmpresaServicoDao).gravar(contatoEmpresaServico);

    this.contatoEmpresaServicoService.cadastrarContatoEmpresaServico(cadastroDTO);

  }

  /**
   * Editar contato empresa servico ja existe para mesmo servico test.
   */
  @Test(expected = NegocioException.class)
  public void editarContatoEmpresaServicoJaExisteParaMesmoServicoExceptionTest() {
    Long id = 1L;
    Long idContatoEmpresa = 1L;
    Long idServico = 1L;
    CadastrarContatoEmpresaServicoDTO editarDTO = this.montarCadastroDTO();

    Mockito.when(this.contatoEmpresaServicoDao.consultarPorId(id))
        .thenReturn(MockUtil.mockContatoEmpresaServico());
    Mockito.when(this.contatoEmpresaServicoDao.verificarExistenciaContatoEmpresaParaMesmoServico(id,
        idContatoEmpresa, idServico)).thenReturn(MockUtil.mockContatoEmpresaServico());
    assertNotNull(this.contatoEmpresaServicoService.editarContatoEmpresaService(id, editarDTO));
  }

  /**
   * Excluir contato empresa servico service.
   */
  @Test(expected = NegocioException.class)
  public void excluirContatoEmpresaServicoService() {
    Long id = 1L;
    assertNotNull(this.contatoEmpresaServicoService.excluirContatoEmpresaServico(id));
  }



  /**
   * Montar filtro pesquisa contato empresa servico DTO.
   *
   * @return the filtro pesquisa contato empresa servico DTO
   */
  private FiltroPesquisaContatoEmpresaServicoDTO montarFiltroPesquisaContatoEmpresaServicoDTO() {
    FiltroPesquisaContatoEmpresaServicoDTO filtro = new FiltroPesquisaContatoEmpresaServicoDTO();
    filtro.setIdContato(1L);
    filtro.setIdEmpresa(1L);
    filtro.setIdServico(1L);
    filtro.setIdSistema(1L);
    filtro.setNumeroPagina(1);
    filtro.setTipoOrdenacao("ASC");
    filtro.setOrderBy(ColunaOrdenacaoContatoEmpresaServicoEnum.EMPRESA.getName());
    filtro.setItensPorPagina(5);
    return filtro;
  }

  /**
   * Contato empresa servico list.
   *
   * @return the list
   */
  private static List<ContatoEmpresaServico> contatoEmpresaServicoList() {
    List<ContatoEmpresaServico> contatoEmpresaServicoList = new ArrayList<ContatoEmpresaServico>();
    contatoEmpresaServicoList.add(MockUtil.mockContatoEmpresaServico());
    return contatoEmpresaServicoList;
  }

  /**
   * Montar cadastro DTO.
   *
   * @return the cadastrar contato empresa servico DTO
   */
  private CadastrarContatoEmpresaServicoDTO montarCadastroDTO() {
    CadastrarContatoEmpresaServicoDTO dto = new CadastrarContatoEmpresaServicoDTO();
    dto.setIdContatoEmpresa(1L);
    dto.setIdEmpresa(1L);
    dto.setIdServico(1L);
    dto.setNotificacaoFalha(Boolean.TRUE);
    dto.setNotificacaoSucesso(Boolean.TRUE);
    return dto;
  }

}
