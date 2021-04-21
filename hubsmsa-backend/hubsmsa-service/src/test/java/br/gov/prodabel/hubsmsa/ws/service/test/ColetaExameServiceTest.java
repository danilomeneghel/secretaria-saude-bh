package br.gov.prodabel.hubsmsa.ws.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.DeParaService;
import br.gov.pbh.prodabel.hubsmsa.service.LogServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.service.ColetaExameService;
import br.gov.pbh.prodabel.hubsmsa.ws.service.SisredeService;

@RunWith(MockitoJUnitRunner.class)
public class ColetaExameServiceTest {

  @Mock
  private SistemaService sistemaService;

  @Mock
  private ContatoEmpresaServicoService contatoService;

  @Mock
  private ServicoService servicoService;

  @Mock
  private SisredeService sisredeService;

  @Mock
  private LogServicoService logServicoService;

  @Mock
  private DeParaService deParaService;

  private ColetaExameService service;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    service = new ColetaExameService();
    service.setContatoService(contatoService);
    service.setServicoService(servicoService);
    service.setSistemaService(sistemaService);
    service.setSisredeService(sisredeService);
    service.setDeParaService(deParaService);
    service.setLogServicoService(logServicoService);
  }

  /**
   * Buscar solicitacoes coleta exame do paciente.
   */
  @Test
  public void buscarSolicitacoesColetaExameDoPaciente() {
    Mockito.when(sistemaService.consultarSistemaPorNome(Matchers.anyString()))
        .thenReturn(sistemaMock(1L, StatusEnum.A, StatusEnum.A));
    Mockito.when(servicoService.consultarServico(Matchers.anyLong(), Matchers.anyLong(),
        Matchers.anyString())).thenReturn(servicosMock(1L, "Servico Teste", StatusEnum.A));
    Mockito.when(sisredeService.buscarSolicitacoesColetaExame(Matchers.any()))
        .thenReturn(coletaMock());
    Mockito.when(deParaService.realizarDePara(Matchers.any(), Matchers.any()))
        .thenReturn(mockResponse());
    Mockito.when(logServicoService.gravarLogServico(Matchers.any()))
        .thenReturn(logServicoGravado());

    SolicitacoesColetaExameResponse response =
        service.buscarSolicitacoesColetaExameDoPaciente(criarSolicitacaoColeta());
    assertNotNull(response);
    assertEquals(Long.valueOf(200), response.getCodigoRetorno());
  }

  /**
   * Buscar solicitacoes coleta CPF invalido.
   */
  @Test(expected = NegocioException.class)
  public void buscarSolicitacoesColetaCPFInvalido() {
    Mockito.when(sistemaService.consultarSistemaPorNome(Matchers.anyString()))
        .thenReturn(sistemaMock(1L, StatusEnum.A, StatusEnum.A));
    Mockito.when(servicoService.consultarServico(Matchers.anyLong(), Matchers.anyLong(),
        Matchers.anyString())).thenReturn(servicosMock(1L, "Servico Teste", StatusEnum.A));
    Mockito.when(sisredeService.buscarSolicitacoesColetaExame(Matchers.any()))
        .thenReturn(coletaMock());
    Mockito.when(deParaService.realizarDePara(Matchers.any(), Matchers.any()))
        .thenReturn(mockResponse());
    Mockito.when(logServicoService.gravarLogServico(Matchers.any()))
        .thenReturn(logServicoGravado());

    service.buscarSolicitacoesColetaExameDoPaciente(criarSolicitacaoColetaCPFInvalido());
  }

  /**
   * Criar solicitacao coleta.
   *
   * @return the solicitacao coleta DTO
   */
  private SolicitacaoColetaDTO criarSolicitacaoColetaCPFInvalido() {
    SolicitacaoColetaDTO solicitacao = new SolicitacaoColetaDTO();
    solicitacao.setCns("101010101010101");
    solicitacao.setCpf("13482486025");
    return solicitacao;
  }

  /**
   * Criar solicitacao coleta.
   *
   * @return the solicitacao coleta DTO
   */
  private SolicitacaoColetaDTO criarSolicitacaoColeta() {
    SolicitacaoColetaDTO solicitacao = new SolicitacaoColetaDTO();
    solicitacao.setCns("101010101010101");
    solicitacao.setCpf("13482486026");
    return solicitacao;
  }

  /**
   * Sistema mock.
   *
   * @param id the id
   * @param statusSistema the status sistema
   * @param statusEmpresa the status empresa
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

  /**
   * Coleta mock.
   *
   * @return the solicitacao coleta exame
   */
  private SolicitacaoColetaExame coletaMock() {
    SolicitacaoColetaExame solicitacao = new SolicitacaoColetaExame();
    solicitacao.setAlturaPaciente(180L);
    solicitacao.setCnesEstabelecimentoPaciente(10020L);
    solicitacao.setCnesEstabelecimentoSolicitante(20019L);
    solicitacao.setCnesSolicitante(100L);
    solicitacao.setCns("200");
    solicitacao.setCodigoCentroSaude(230L);
    solicitacao.setCodigoExamePaciente(2L);
    solicitacao.setCodigoMaterial(2L);
    solicitacao.setCodigoProcedimentoLocal(2L);
    solicitacao.setCodigoSigtapExame(23L);
    solicitacao.setCor("P");
    solicitacao.setCpf("13482486026");
    solicitacao.setCpfMedico("13482486026");
    solicitacao.setDataHoraSolicitacao(new Date());
    solicitacao.setDataInicioSintoma(new Date());
    solicitacao.setDataNascimento(new Date());
    solicitacao.setDataUltimaMenstruacao(new Date());
    solicitacao.setDescricaoExamePacente("Exame do paciente");
    solicitacao.setDescricaoMaterial("descricao do material");
    solicitacao.setIdade("38");
    solicitacao.setIndicadorPedidoSus("S");
    solicitacao.setMedicamentosEmUso("medicamentos em uso");
    solicitacao.setNomeMaePaciente("Mae");
    solicitacao.setNomeMedico("Dr House");
    solicitacao.setNomePaciente("Pedrinho");
    solicitacao.setNumeroConselhoProfissional(54L);
    solicitacao.setNumeroEquipePsfPaciente(74L);
    solicitacao.setNumeroExamePaciente(2L);
    solicitacao.setNumeroPedido(322L);
    solicitacao.setNumeroRegistroProfissional("546");
    solicitacao.setObservacoesColeta("observacoes ...");
    solicitacao.setPacienteEmPreNatal("N");
    solicitacao.setPacienteEstaJejum("S");
    solicitacao.setPesoPaciente(80L);
    solicitacao.setSexo("M");
    solicitacao.setStatusColeta(2L);
    solicitacao.setStatusPedido("2");
    solicitacao.setTelefonePaciente("11996858585");
    solicitacao.setTipoPedido("Tipo do pedido");
    solicitacao.setTipoSolicitante("Tipo solicitante");
    solicitacao.setUfRegistro("MG");
    return solicitacao;
  }

  /**
   * Mock response.
   *
   * @return the solicitacoes coleta exame response
   */
  private SolicitacoesColetaExameResponse mockResponse() {
    SolicitacoesColetaExameResponse response = new SolicitacoesColetaExameResponse();
    response.setCodigoRetorno(200L);

    return response;
  }

  /**
   * Log servico gravado.
   *
   * @return the log servico
   */
  private LogServico logServicoGravado() {
    LogServico logServico = new LogServico();
    logServico.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setId(1L);
    logServico.setRequisicao("{'resquest': 'teste'}");
    logServico.setResposta("{'response': 'teste'}");
    logServico.setServico(servicosMock(1L, "Servico Teste", StatusEnum.A).get(0));
    logServico.setStatus(StatusExecucao.S);
    logServico.setDataFimEvento(new Timestamp(System.currentTimeMillis()));
    return logServico;
  }

}
