package br.gov.pbh.prodabel.hubsmsa.ws.service.mapper;

import java.util.ArrayList;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.MedicoSolicitante;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.PacienteColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.PedidoColeta;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.dto.ExameSolicitadoDTO;

public class SolicitacaoColetaMapper {
  
  
  /**
   * Mapper.
   *
   * @param solicitacao the solicitacao
   * @return the solicitacoes coleta exame response
   */
  public SolicitacoesColetaExameResponse mapper(SolicitacaoColetaExame solicitacao) {
    
    SolicitacoesColetaExameResponse response = new SolicitacoesColetaExameResponse();
    response.setExamesSolicitados(new ArrayList<>());
    PacienteColetaExame paciente = new PacienteColetaExame();
    paciente.setAlturaPaciente(solicitacao.getAlturaPaciente());
    paciente.setCnesEstabelecimentoPaciente(solicitacao.getCnesEstabelecimentoPaciente());
    paciente.setCns(solicitacao.getCns());
    paciente.setCor(solicitacao.getCor());
    paciente.setCpf(solicitacao.getCpf());
    paciente.setDataInicioSintoma(solicitacao.getDataInicioSintoma());
    paciente.setDataNascimento(solicitacao.getDataNascimento());
    paciente.setDataUltimaMenstruacao(solicitacao.getDataUltimaMenstruacao());
    paciente.setIdade(solicitacao.getIdade());
    paciente.setMedicamentosEmUso(solicitacao.getMedicamentosEmUso());
    paciente.setNomeMaePaciente(solicitacao.getNomeMaePaciente());
    paciente.setNomePaciente(solicitacao.getNomePaciente());
    paciente.setNumeroEquipePSFPaciente(solicitacao.getNumeroEquipePsfPaciente());
    paciente.setPacienteEmPreNatal(solicitacao.getPacienteEmPreNatal());
    paciente.setPacienteEstaJejum(solicitacao.getPacienteEstaJejum());
    paciente.setPesoPaciente(solicitacao.getPesoPaciente());
    paciente.setSexo(solicitacao.getSexo());
    paciente.setTelefonePaciente(solicitacao.getTelefonePaciente());

    PedidoColeta pedido = new PedidoColeta();
    pedido.setCnesEstabelecimentoSolicitante(solicitacao.getCnesEstabelecimentoSolicitante());
    pedido.setCodigoCentroSaude(solicitacao.getCodigoCentroSaude());
    pedido.setIndicadorPedidoSus(solicitacao.getIndicadorPedidoSus());
    pedido.setNumeroPedido(solicitacao.getNumeroPedido());
    pedido.setStatusPedido(solicitacao.getStatusPedido());
    pedido.setTipoPedido(solicitacao.getTipoPedido());

    MedicoSolicitante solicitante = new MedicoSolicitante();
    solicitante.setCnesSolicitante(solicitacao.getCnesSolicitante());
    solicitante.setDataHoraSolicitacao(solicitacao.getDataHoraSolicitacao());
    solicitante.setNumeroConselhoProfissional(solicitacao.getNumeroConselhoProfissional());
    solicitante.setNumeroRegistroProfissional(solicitacao.getNumeroRegistroProfissional());
    solicitante.setUfRegistroConselho(solicitacao.getUfRegistro());

    ExameSolicitadoDTO exame = new ExameSolicitadoDTO();
    exame.setCodigoExamePaciente(solicitacao.getCodigoExamePaciente());
    exame.setCodigoMaterial(solicitacao.getCodigoMaterial());
    exame.setCodigoProcedimentoLocal(solicitacao.getCodigoProcedimentoLocal());
    exame.setCodigoSigtapExame(solicitacao.getCodigoSigtapExame());
    exame.setDescricaoExamePaciente(solicitacao.getDescricaoExamePacente());
    exame.setDescricaoMaterial(solicitacao.getDescricaoMaterial());
    exame.setNumeroExamePaciente(solicitacao.getNumeroExamePaciente());
    exame.setObservacoesColeta(solicitacao.getObservacoesColeta());
    exame.setStatusColeta(solicitacao.getStatusColeta());

    response.setPaciente(paciente);
    response.setPedido(pedido);
    response.setSolicitante(solicitante);
    response.getExamesSolicitados().add(exame);
    
    return response;
  }

}
