package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateDeserializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateSerializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.Temporal;

public class Atendimento implements Serializable {

  private static final long serialVersionUID = 4137295059553559663L;

  @NotNull(message = "O campo numeroAgendamentoSigrah é obrigatório")
  private Integer numeroAgendamentoSigrah;

  @NotNull(message = "O campo tipoAtendimento é obrigatório")
  private Integer tipoAtendimento;

  @NotNull(message = "O campo dataHoraAgendamento é obrigatório")
  @Temporal(format = "yyyy-MM-dd HH:mm")
  @JsonDeserialize(using = DateDeserializer.class)
  @JsonSerialize(using = DateSerializer.class)
  private LocalDateTime dataHoraAgendamento;

  @NotNull(message = "O campo prioridade é obrigatório")
  private Integer prioridade;

  @NotNull(message = "O campo tipoConsulta é obrigatório")
  private Integer tipoConsulta;

  @NotBlank(message = "O campo codigoProcedimento é obrigatório")
  private String codigoProcedimento;

  @NotNull(message = "O campo cnesEstabelecimentoSaudeMarcacao é obrigatório")
  private Integer cnesEstabelecimentoSaudeMarcacao;

  @NotBlank(message = "O campo nomeEstabelecimentoSaudeMarcacao é obrigatório")
  private String nomeEstabelecimentoSaudeMarcacao;

  @NotBlank(message = "O campo enderecoEstabelecimentoSaudeMarcacao é obrigatório")
  private String enderecoEstabelecimentoSaudeMarcacao;

  @NotBlank(message = "O campo telefoneEstabelecimentoSaudeMarcacao é obrigatório")
  private String telefoneEstabelecimentoSaudeMarcacao;

  @NotNull(message = "O campo cnesEstabelecimentoSaudeAtendimento é obrigatório")
  private Integer cnesEstabelecimentoSaudeAtendimento;

  @NotBlank(message = "O campo nomeEstabelecimentoSaudeAtendimento é obrigatório")
  private String nomeEstabelecimentoSaudeAtendimento;

  @NotBlank(message = "O campo enderecoEstabelecimentoSaudeAtendimento é obrigatório")
  private String enderecoEstabelecimentoSaudeAtendimento;

  @NotBlank(message = "O campo telefoneEstabelecimentoSaudeAtendimento é obrigatório")
  private String telefoneEstabelecimentoSaudeAtendimento;

  @NotBlank(message = "O campo formaAviso é obrigatório")
  private String formaAviso;

  @NotBlank(message = "O campo orientacoes é obrigatório")
  private String orientacoes;

  @JsonProperty("dadosDoPaciente")
  private Paciente paciente;

  @JsonProperty("dadosDoMedicoSolicitante")
  private Medico medicoSolicitante;

  @JsonProperty("dadosDoMedicoExecutante")
  private Medico medicoExecutante;

  public Integer getNumeroAgendamentoSigrah() {
    return numeroAgendamentoSigrah;
  }

  public void setNumeroAgendamentoSigrah(Integer numeroAgendamentoSigrah) {
    this.numeroAgendamentoSigrah = numeroAgendamentoSigrah;
  }

  public Integer getTipoAtendimento() {
    return tipoAtendimento;
  }

  public void setTipoAtendimento(Integer tipoAtendimento) {
    this.tipoAtendimento = tipoAtendimento;
  }

  public LocalDateTime getDataHoraAgendamento() {
    return dataHoraAgendamento;
  }

  public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
    this.dataHoraAgendamento = dataHoraAgendamento;
  }

  public Integer getPrioridade() {
    return prioridade;
  }

  public void setPrioridade(Integer prioridade) {
    this.prioridade = prioridade;
  }

  public Integer getTipoConsulta() {
    return tipoConsulta;
  }

  public void setTipoConsulta(Integer tipoConsulta) {
    this.tipoConsulta = tipoConsulta;
  }

  public String getCodigoProcedimento() {
    return codigoProcedimento;
  }

  public void setCodigoProcedimento(String codigoProcedimento) {
    this.codigoProcedimento = codigoProcedimento;
  }

  public Integer getCnesEstabelecimentoSaudeMarcacao() {
    return cnesEstabelecimentoSaudeMarcacao;
  }

  public void setCnesEstabelecimentoSaudeMarcacao(Integer cnesEstabelecimentoSaudeMarcacao) {
    this.cnesEstabelecimentoSaudeMarcacao = cnesEstabelecimentoSaudeMarcacao;
  }

  public String getNomeEstabelecimentoSaudeMarcacao() {
    return nomeEstabelecimentoSaudeMarcacao;
  }

  public void setNomeEstabelecimentoSaudeMarcacao(String nomeEstabelecimentoSaudeMarcacao) {
    this.nomeEstabelecimentoSaudeMarcacao = nomeEstabelecimentoSaudeMarcacao;
  }

  public String getEnderecoEstabelecimentoSaudeMarcacao() {
    return enderecoEstabelecimentoSaudeMarcacao;
  }

  public void setEnderecoEstabelecimentoSaudeMarcacao(String enderecoEstabelecimentoSaudeMarcacao) {
    this.enderecoEstabelecimentoSaudeMarcacao = enderecoEstabelecimentoSaudeMarcacao;
  }

  public String getTelefoneEstabelecimentoSaudeMarcacao() {
    return telefoneEstabelecimentoSaudeMarcacao;
  }

  public void setTelefoneEstabelecimentoSaudeMarcacao(String telefoneEstabelecimentoSaudeMarcacao) {
    this.telefoneEstabelecimentoSaudeMarcacao = telefoneEstabelecimentoSaudeMarcacao;
  }

  public Integer getCnesEstabelecimentoSaudeAtendimento() {
    return cnesEstabelecimentoSaudeAtendimento;
  }

  public void setCnesEstabelecimentoSaudeAtendimento(Integer cnesEstabelecimentoSaudeAtendimento) {
    this.cnesEstabelecimentoSaudeAtendimento = cnesEstabelecimentoSaudeAtendimento;
  }

  public String getNomeEstabelecimentoSaudeAtendimento() {
    return nomeEstabelecimentoSaudeAtendimento;
  }

  public void setNomeEstabelecimentoSaudeAtendimento(String nomeEstabelecimentoSaudeAtendimento) {
    this.nomeEstabelecimentoSaudeAtendimento = nomeEstabelecimentoSaudeAtendimento;
  }

  public String getEnderecoEstabelecimentoSaudeAtendimento() {
    return enderecoEstabelecimentoSaudeAtendimento;
  }

  public void setEnderecoEstabelecimentoSaudeAtendimento(
      String enderecoEstabelecimentoSaudeAtendimento) {
    this.enderecoEstabelecimentoSaudeAtendimento = enderecoEstabelecimentoSaudeAtendimento;
  }

  public String getTelefoneEstabelecimentoSaudeAtendimento() {
    return telefoneEstabelecimentoSaudeAtendimento;
  }

  public void setTelefoneEstabelecimentoSaudeAtendimento(
      String telefoneEstabelecimentoSaudeAtendimento) {
    this.telefoneEstabelecimentoSaudeAtendimento = telefoneEstabelecimentoSaudeAtendimento;
  }

  public String getFormaAviso() {
    return formaAviso;
  }

  public void setFormaAviso(String formaAviso) {
    this.formaAviso = formaAviso;
  }

  public String getOrientacoes() {
    return orientacoes;
  }

  public void setOrientacoes(String orientacoes) {
    this.orientacoes = orientacoes;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Medico getMedicoSolicitante() {
    return medicoSolicitante;
  }

  public void setMedicoSolicitante(Medico medicoSolicitante) {
    this.medicoSolicitante = medicoSolicitante;
  }

  public Medico getMedicoExecutante() {
    return medicoExecutante;
  }

  public void setMedicoExecutante(Medico medicoExecutante) {
    this.medicoExecutante = medicoExecutante;
  }

  @JsonInclude(Include.NON_NULL)
  public class Medico implements Serializable {

    private static final long serialVersionUID = -8843620769068034354L;

    @NotBlank(message = "O campo cpfMedico é obrigatório")
    private String cpfMedico;

    @NotBlank(message = "O campo nomeMedico é obrigatório")
    private String nomeMedico;

    @NotBlank(message = "O campo cbo é obrigatório")
    private String cbo;

    public String getCpfMedico() {
      return cpfMedico;
    }

    public void setCpfMedico(String cpfMedico) {
      this.cpfMedico = cpfMedico;
    }

    public String getNomeMedico() {
      return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
      this.nomeMedico = nomeMedico;
    }

    public String getCbo() {
      return cbo;
    }

    public void setCbo(String cbo) {
      this.cbo = cbo;
    }

  }

}
