package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;


public class AtendimentoPacienteDTO extends ServicoBaseDTO {

  private static final long serialVersionUID = 7338729043571541566L;

  private Long numeroSolicitacaoSISREDE;

  private Long numeroAgendamentoSigrah;

  private String statusAtendimento;


  public Long getNumeroSolicitacaoSISREDE() {
    return numeroSolicitacaoSISREDE;
  }

  public void setNumeroSolicitacaoSISREDE(Long numeroSolicitacaoSISREDE) {
    this.numeroSolicitacaoSISREDE = numeroSolicitacaoSISREDE;
  }

  public Long getNumeroAgendamentoSigrah() {
    return numeroAgendamentoSigrah;
  }

  public void setNumeroAgendamentoSigrah(Long numeroAgendamentoSigrah) {
    this.numeroAgendamentoSigrah = numeroAgendamentoSigrah;
  }

  public String getStatusAtendimento() {
    return statusAtendimento;
  }

  public void setStatusAtendimento(String statusAtendimento) {
    this.statusAtendimento = statusAtendimento;
  }

}
