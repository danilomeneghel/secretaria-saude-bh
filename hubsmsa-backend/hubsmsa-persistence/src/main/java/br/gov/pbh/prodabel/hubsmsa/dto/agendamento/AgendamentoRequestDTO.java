package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class AgendamentoRequestDTO implements Serializable{

  private static final long serialVersionUID = 4163506821445404928L;

  @JsonProperty("dadosDoPaciente")
  @Valid
  private Paciente paciente;

  @JsonProperty("dadosDoSolicitante")
  @Valid
  private Solicitante solicitante;

  @JsonProperty("dadosDoAgendamento")
  @Valid
  private Agendamento agendamento;

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Solicitante getSolicitante() {
    return solicitante;
  }

  public void setSolicitante(Solicitante solicitante) {
    this.solicitante = solicitante;
  }

  public Agendamento getAgendamento() {
    return agendamento;
  }

  public void setAgendamento(Agendamento agendamento) {
    this.agendamento = agendamento;
  }
}
