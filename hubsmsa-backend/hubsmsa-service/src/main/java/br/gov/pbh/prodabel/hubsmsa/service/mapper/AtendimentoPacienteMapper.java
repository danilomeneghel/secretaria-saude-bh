package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AtendimentoPacienteDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.Paciente;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.AtendimentoPaciente;

public class AtendimentoPacienteMapper {

  /**
   * Mapper.
   *
   * @param atendimentoPaciente the atendimento paciente
   * @return the atendimento paciente DTO
   */
  public AtendimentoPacienteDTO mapper(AtendimentoPaciente atendimentoPaciente) {

    AtendimentoPacienteDTO dto = new AtendimentoPacienteDTO();
    dto.setNumeroAgendamentoSigrah(atendimentoPaciente.getNumeroAgendamentoSigrah());
    dto.setNumeroSolicitacaoSISREDE(atendimentoPaciente.getNumeroSolicitacaoSISREDE());
    Paciente paciente = new Paciente();

    paciente.setCelularContato(atendimentoPaciente.getTelefone());
    paciente.setCns(atendimentoPaciente.getCns());
    paciente.setMunicipioNascimento(atendimentoPaciente.getMunicipioNascimento());
    paciente.setCpf(atendimentoPaciente.getCpf());
    paciente.setDataNascimento(atendimentoPaciente.getDataNascimento());
    paciente.setNomeMaePaciente(atendimentoPaciente.getNomeMaePaciente());
    paciente.setNomePaciente(atendimentoPaciente.getNomePaciente());
    dto.setNumeroAgendamentoSigrah(atendimentoPaciente.getNumeroAgendamentoSigrah());
    dto.setNumeroSolicitacaoSISREDE(atendimentoPaciente.getNumeroSolicitacaoSISREDE());
    dto.setStatusAtendimento(atendimentoPaciente.getStatusAtendimento());
    dto.setPaciente(paciente);

    return dto;
  }

}
