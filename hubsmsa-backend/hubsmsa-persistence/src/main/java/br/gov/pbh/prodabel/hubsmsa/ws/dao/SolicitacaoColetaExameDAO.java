package br.gov.pbh.prodabel.hubsmsa.ws.dao;

import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;

@LocalBean
@Stateless
public class SolicitacaoColetaExameDAO extends SisredeDAO<Long, SolicitacaoColetaExame> {

  /**
   * Buscar solicitacao coleta exame.
   *
   * @return the solicitacao coleta exame
   */
  public SolicitacaoColetaExame buscarSolicitacaoColetaExame() {
    return coletaMock();
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
    solicitacao.setCpf("30162123825");
    solicitacao.setCpfMedico("30162123825");
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
    solicitacao.setTelefonePaciente("11996819286");
    solicitacao.setTipoPedido("Tipo do pedido");
    solicitacao.setTipoSolicitante("Tipo solicitante");
    solicitacao.setUfRegistro("MG");
    return solicitacao;
  }

}
