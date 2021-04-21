package br.gov.prodabel.hubsmsa.ws.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.dao.SolicitacaoColetaExameDAO;
import br.gov.pbh.prodabel.hubsmsa.ws.service.SisredeService;

@RunWith(MockitoJUnitRunner.class)
public class SisredeServiceTest {

  @Mock
  private SolicitacaoColetaExameDAO solicitacaoColetaDAO;

  private SisredeService service;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    service = new SisredeService();
    service.setSolicitacaoColetaDAO(solicitacaoColetaDAO);
  }

  /**
   * Buscar solicitacao coleta exame.
   */
  @Test
  public void buscarSolicitacaoColetaExameTest() {
    when(solicitacaoColetaDAO.buscarSolicitacaoColetaExame()).thenReturn(coletaMock());
    SolicitacaoColetaExame response =
        service.buscarSolicitacoesColetaExame(criarSolicitacaoColeta());
    assertNotNull(response);
    assertEquals("Pedrinho", response.getNomePaciente());
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

}
