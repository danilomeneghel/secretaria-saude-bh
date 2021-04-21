package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoRequestDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.AgendamentoEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.AgendamentoRealizadoService;
import br.gov.pbh.prodabel.hubsmsa.service.AgendamentoService;
import br.gov.pbh.prodabel.hubsmsa.service.AtendimentoPacienteService;

public class AgendamentoEndPointImpl implements AgendamentoEndPoint {

  @EJB
  private AgendamentoService agendamentoService;

  @EJB
  private AgendamentoRealizadoService agendamentoRealizadoservice;

  @EJB
  private AtendimentoPacienteService atendimentoPacienteService;

  @Override
  public AgendamentoResponseDTO solicitarAgendamentoAtendimentoPaciente(
      AgendamentoRequestDTO agendamentoDTO) {
    return agendamentoService.solicitarAgendamento(agendamentoDTO);
  }

  @Override
  public String recuperarAtendimentosAgendados() {
    return agendamentoRealizadoservice.processar("2020-11-25");
  }

  @Override
  public String registrarAtendimentoPaciente() {
    return atendimentoPacienteService.registrarAtendimentoPaciente();
  }

}
