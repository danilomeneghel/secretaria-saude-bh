package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.Atendimento;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.AgendamentoRealizado;

// TODO: Auto-generated Javadoc
public final class AgendamentoRealizadoMapper {

  /**
   * Instantiates a new agendamento realizado mapper.
   */
  private AgendamentoRealizadoMapper() {

  }

  /**
   * Mapper.
   *
   * @param atendimento the atendimento
   * @return the agendamento realizado
   */
  public static AgendamentoRealizado mapper(Atendimento atendimento) {

    AgendamentoRealizado agendamento = new AgendamentoRealizado();

    agendamento.setCnesEstabelecimentoSaudeAtendimento(
        atendimento.getCnesEstabelecimentoSaudeAtendimento());
    agendamento
        .setCnesEstabelecimentoSaudeMarcacao(atendimento.getCnesEstabelecimentoSaudeMarcacao());
    agendamento.setCns(atendimento.getPaciente().getCns());
    agendamento.setCodigoProcedimento(atendimento.getCodigoProcedimento());
    agendamento.setCpf(atendimento.getPaciente().getCpf());
    agendamento.setCpfMedicoExecutante(atendimento.getMedicoExecutante().getCpfMedico());
    agendamento.setCpfMedicoSolicitante(atendimento.getMedicoSolicitante().getCpfMedico());
    agendamento.setDataHoraAgendamento(atendimento.getDataHoraAgendamento());
    agendamento.setDataNascimento(atendimento.getPaciente().getDataNascimento());
    agendamento.setDistrito(atendimento.getPaciente().getDistrito());
    agendamento.setEnderecoEstabelecimentoSaudeAtendimento(
        atendimento.getEnderecoEstabelecimentoSaudeAtendimento());
    agendamento.setEnderecoEstabelecimentoSaudeMarcacao(
        atendimento.getEnderecoEstabelecimentoSaudeMarcacao());
    agendamento.setFormaAviso(atendimento.getFormaAviso());
    agendamento.setMunicipioNascimento(atendimento.getPaciente().getMunicipioNascimento());
    agendamento.setNomeEstabelecimentoSaudeAtendimento(
        atendimento.getNomeEstabelecimentoSaudeAtendimento());
    agendamento
        .setNomeEstabelecimentoSaudeMarcacao(atendimento.getNomeEstabelecimentoSaudeMarcacao());
    agendamento.setNomeMaePaciente(atendimento.getPaciente().getNomeMaePaciente());
    agendamento.setNomeMedicoExecutante(atendimento.getMedicoExecutante().getNomeMedico());
    agendamento.setNomeMedicoSolicitante(atendimento.getMedicoSolicitante().getNomeMedico());
    agendamento.setNomePaciente(atendimento.getPaciente().getNomePaciente());
    agendamento.setNumeroAgendamentoSigrah(atendimento.getNumeroAgendamentoSigrah());
    agendamento.setOrientacoes(atendimento.getOrientacoes());
    agendamento.setPrioridade(atendimento.getPrioridade());
    agendamento.setSexo(atendimento.getPaciente().getSexo());
    agendamento.setTelefone(atendimento.getPaciente().getTelefone());
    agendamento.setTelefoneEstabelecimentoSaudeAtendimento(
        atendimento.getTelefoneEstabelecimentoSaudeAtendimento());
    agendamento.setTelefoneEstabelecimentoSaudeMarcacao(
        atendimento.getTelefoneEstabelecimentoSaudeMarcacao());
    agendamento.setTipoAtendimento(atendimento.getTipoAtendimento());
    agendamento.setTipoConsulta(atendimento.getTipoConsulta());
    agendamento.setCboMedicoExecutante(atendimento.getMedicoExecutante().getCbo());
    agendamento.setCboMedicoSolicitante(atendimento.getMedicoSolicitante().getCbo());

    return agendamento;
  }

}
