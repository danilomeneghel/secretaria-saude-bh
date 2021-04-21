package br.gov.prodabel.hubsmsa.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.AtendimentoPacienteService;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.LogServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;

// TODO: Auto-generated Javadoc
@RunWith(MockitoJUnitRunner.class)
public class AtendimentoPacienteServiceTest {

  private AtendimentoPacienteService service;

  @Mock
  private SistemaService sistemaService;

  @Mock
  private ContatoEmpresaServicoService contatoService;

  @Mock
  private ServicoService servicoService;

  @Mock
  private LogServicoService logServicoService;

  private List<LogServico> logServicoListMock = new ArrayList<>();

  /**
   * Inits the.
   */
  @Before
  public void init() {
    service = new AtendimentoPacienteService();
    service.setContatoService(contatoService);
    service.setServicoService(servicoService);
    service.setSistemaService(sistemaService);
  }

  /**
   * Registrar atendimento test.
   */
  @Test(expected = NegocioException.class)
  public void registrarAtendimentoEmpresaInativaTest() {

    Mockito.when(sistemaService.consultarSistemaPorNome(Matchers.anyString()))
        .thenReturn(sistemaMock(1L, StatusEnum.A, StatusEnum.I));
    Mockito.when(servicoService.consultarServico(Matchers.anyLong(), Matchers.anyLong(),
        Matchers.anyString())).thenReturn(servicosMock(1L, "Servico Teste", StatusEnum.A));

    service.registrarAtendimentoPaciente();

  }

  /**
   * Registrar atendimento sistema invalido test.
   */
  @Test(expected = NegocioException.class)
  public void registrarAtendimentoSistemaInativoTest() {

    Mockito.when(sistemaService.consultarSistemaPorNome(Matchers.anyString()))
        .thenReturn(sistemaMock(1L, StatusEnum.I, StatusEnum.A));
    Mockito.when(servicoService.consultarServico(Matchers.anyLong(), Matchers.anyLong(),
        Matchers.anyString())).thenReturn(servicosMock(1L, "Servico Teste", StatusEnum.A));

    service.registrarAtendimentoPaciente();

  }

  /**
   * Registrar atendimento servico inativo test.
   */
  @Test(expected = NegocioException.class)
  public void registrarAtendimentoServicoInativoTest() {

    Mockito.when(sistemaService.consultarSistemaPorNome(Matchers.anyString()))
        .thenReturn(sistemaMock(1L, StatusEnum.A, StatusEnum.A));
    Mockito.when(servicoService.consultarServico(Matchers.anyLong(), Matchers.anyLong(),
        Matchers.anyString())).thenReturn(servicosMock(1L, "Servico Teste", StatusEnum.I));

    service.registrarAtendimentoPaciente();

  }

  /**
   * Reprocessar registro com falha lista vazia.
   */
  @Test
  public void reprocessarRegistroComFalhaListaVazia() {
    Mockito.when(logServicoService.buscarResquisicoesFalhas()).thenReturn(logServicoListMock);
    Mockito.spy(logServicoService.buscarResquisicoesFalhas());
  }

  /**
   * Reprocessar registro com falha.
   * 
   * @throws IOException
   */
  @Test(expected = NullPointerException.class)
  public void reprocessarRegistroComFalha() throws IOException {
    preencherLogServicoList();
    Mockito.when(logServicoService.buscarResquisicoesFalhas()).thenReturn(logServicoListMock);
    service.reprocessarRegistroComFalha();
  }

  /**
   * Preencher log servico list.
   */
  private void preencherLogServicoList() {
    LogServico logServico = new LogServico();
    logServico.setId(1L);
    logServico.setRequisicao("{'teste': 'OK'}");
    logServicoListMock.add(logServico);

  }

  /**
   * Sistema mock.
   *
   * @param id the id
   * @return the sistema
   */
  private Sistema sistemaMock(Long id, StatusEnum statusSistema, StatusEnum statusEmpresa) {
    Sistema sistema = new Sistema();
    sistema.setId(id);
    sistema.setAtivo(statusSistema);
    sistema.setEmpresa(empresaMock(1L, "Empresa Teste", statusEmpresa));
    return sistema;
  }

  /**
   * Empresa mock.
   *
   * @param id the id
   * @param nome the nome
   * @param status the status
   * @return the empresa
   */
  private Empresa empresaMock(Long id, String nome, StatusEnum status) {
    Empresa empresa = new Empresa();
    empresa.setId(id);
    empresa.setNomeEmpresarial(nome);
    empresa.setAtivo(status);
    return empresa;
  }

  /**
   * Servico mock.
   *
   * @param id the id
   * @param nome the nome
   * @return the servico
   */
  private List<Servico> servicosMock(Long id, String nome, StatusEnum status) {
    List<Servico> list = new ArrayList<>();
    Servico servico = new Servico();
    servico.setId(id);
    servico.setNome(nome);
    servico.setStatus(status);
    list.add(servico);
    return list;
  }

}
