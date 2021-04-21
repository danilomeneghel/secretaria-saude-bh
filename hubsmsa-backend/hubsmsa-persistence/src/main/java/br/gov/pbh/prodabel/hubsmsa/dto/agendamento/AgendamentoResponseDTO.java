package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateDeserializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateSerializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.Temporal;

// TODO: Auto-generated Javadoc
@JsonInclude(Include.NON_NULL)
public class AgendamentoResponseDTO implements Serializable {

  private static final long serialVersionUID = -5104241319663752901L;
  
  
  private Integer codigoRetorno;

  @JsonProperty("dadosDoAgendamento")
  private Agendamento agendamento;

  @JsonProperty("dadosDoProfissionalExecutante")
  private ProfissionalExecutante profissionalExecutante;

  private String descricaoErro;

  /**
   * Instantiates a new agendamento response DTO.
   *
   * @param codigoRetorno the codigo retorno
   * @param agendamento the agendamento
   * @param profissionalExecutante the profissional executante
   * @param descricaoErro the descricao erro
   */
  public AgendamentoResponseDTO(Integer codigoRetorno, Agendamento agendamento,
      ProfissionalExecutante profissionalExecutante, String descricaoErro) {
    this.codigoRetorno = codigoRetorno;
    this.agendamento = agendamento;
    this.profissionalExecutante = profissionalExecutante;
    this.descricaoErro = descricaoErro;
  }

  /**
   * Instantiates a new agendamento response DTO.
   */
  public AgendamentoResponseDTO() {}

  public Integer getCodigoRetorno() {
    return codigoRetorno;
  }

  public void setCodigoRetorno(Integer codigoRetorno) {
    this.codigoRetorno = codigoRetorno;
  }

  public Agendamento getAgendamento() {
    return agendamento;
  }

  public void setAgendamento(Agendamento agendamento) {
    this.agendamento = agendamento;
  }

  public ProfissionalExecutante getProfissionalExecutante() {
    return profissionalExecutante;
  }

  public void setProfissionalExecutante(ProfissionalExecutante profissionalExecutante) {
    this.profissionalExecutante = profissionalExecutante;
  }

  public String getDescricaoErro() {
    return descricaoErro;
  }

  public void setDescricaoErro(String descricaoErro) {
    this.descricaoErro = descricaoErro;
  }

  @JsonInclude(Include.NON_NULL)
  public class Agendamento implements Serializable {

    private static final long serialVersionUID = 5182157845616533251L;

    private Integer numeroAgendamentoSigrah;

    private String statusAgendamento;

    private String tipoAtendimento;

    @Temporal(format = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private LocalDateTime dataHoraAgendamento;

    private Integer cnesEstabelecimentoSaudeMarcacao;

    private String nomeEstabelecimentoSaudeMarcacao;

    private String enderecoEstabelecimentoSaudeMarcacao;

    private String telefoneEstabelecimentoSaudeMarcacao;

    private Integer cnesEstabelecimentoSaudeAtendimento;

    private String nomeEstabelecimentoSaudeAtendimento;

    private String enderecoEstabelecimentoSaudeAtendimento;

    private String telefoneEstabelecimentoSaudeAtendimento;

    private String central;

    private String orientacoes;

    /**
     * Instantiates a new agendamento.
     */
    public Agendamento() {}

    /**
     * Instantiates a new agendamento.
     *
     * @param numeroAgendamentoSigrah the numero agendamento sigrah
     * @param statusAgendamento the status agendamento
     * @param tipoAtendimento the tipo atendimento
     * @param dataHoraAgendamento the data hora agendamento
     * @param cnesEstabelecimentoSaudeMarcacao the cnes estabelecimento saude marcacao
     * @param nomeEstabelecimentoSaudeMarcacao the nome estabelecimento saude marcacao
     * @param enderecoEstabelecimentoSaudeMarcacao the endereco estabelecimento saude marcacao
     * @param telefoneEstabelecimentoSaudeMarcacao the telefone estabelecimento saude marcacao
     * @param cnesEstabelecimentoSaudeAtendimento the cnes estabelecimento saude atendimento
     * @param nomeEstabelecimentoSaudeAtendimento the nome estabelecimento saude atendimento
     * @param enderecoEstabelecimentoSaudeAtendimento the endereco estabelecimento saude atendimento
     * @param telefoneEstabelecimentoSaudeAtendimento the telefone estabelecimento saude atendimento
     * @param central the central
     * @param orientacoes the orientacoes
     */
    public Agendamento(Integer numeroAgendamentoSigrah, String statusAgendamento,
        String tipoAtendimento, LocalDateTime dataHoraAgendamento,
        Integer cnesEstabelecimentoSaudeMarcacao, String nomeEstabelecimentoSaudeMarcacao,
        String enderecoEstabelecimentoSaudeMarcacao, String telefoneEstabelecimentoSaudeMarcacao,
        Integer cnesEstabelecimentoSaudeAtendimento, String nomeEstabelecimentoSaudeAtendimento,
        String enderecoEstabelecimentoSaudeAtendimento,
        String telefoneEstabelecimentoSaudeAtendimento, String central, String orientacoes) {
      this.numeroAgendamentoSigrah = numeroAgendamentoSigrah;
      this.statusAgendamento = statusAgendamento;
      this.tipoAtendimento = tipoAtendimento;
      this.dataHoraAgendamento = dataHoraAgendamento;
      this.cnesEstabelecimentoSaudeMarcacao = cnesEstabelecimentoSaudeMarcacao;
      this.nomeEstabelecimentoSaudeMarcacao = nomeEstabelecimentoSaudeMarcacao;
      this.enderecoEstabelecimentoSaudeMarcacao = enderecoEstabelecimentoSaudeMarcacao;
      this.telefoneEstabelecimentoSaudeMarcacao = telefoneEstabelecimentoSaudeMarcacao;
      this.cnesEstabelecimentoSaudeAtendimento = cnesEstabelecimentoSaudeAtendimento;
      this.nomeEstabelecimentoSaudeAtendimento = nomeEstabelecimentoSaudeAtendimento;
      this.enderecoEstabelecimentoSaudeAtendimento = enderecoEstabelecimentoSaudeAtendimento;
      this.telefoneEstabelecimentoSaudeAtendimento = telefoneEstabelecimentoSaudeAtendimento;
      this.central = central;
      this.orientacoes = orientacoes;
    }

    public Integer getNumeroAgendamentoSigrah() {
      return numeroAgendamentoSigrah;
    }

    public void setNumeroAgendamentoSigrah(Integer numeroAgendamentoSigrah) {
      this.numeroAgendamentoSigrah = numeroAgendamentoSigrah;
    }

    public String getStatusAgendamento() {
      return statusAgendamento;
    }

    public void setStatusAgendamento(String statusAgendamento) {
      this.statusAgendamento = statusAgendamento;
    }

    public String getTipoAtendimento() {
      return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
      this.tipoAtendimento = tipoAtendimento;
    }

    public LocalDateTime getDataHoraAgendamento() {
      return dataHoraAgendamento;
    }

    public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
      this.dataHoraAgendamento = dataHoraAgendamento;
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

    public void setEnderecoEstabelecimentoSaudeMarcacao(
        String enderecoEstabelecimentoSaudeMarcacao) {
      this.enderecoEstabelecimentoSaudeMarcacao = enderecoEstabelecimentoSaudeMarcacao;
    }

    public String getTelefoneEstabelecimentoSaudeMarcacao() {
      return telefoneEstabelecimentoSaudeMarcacao;
    }

    public void setTelefoneEstabelecimentoSaudeMarcacao(
        String telefoneEstabelecimentoSaudeMarcacao) {
      this.telefoneEstabelecimentoSaudeMarcacao = telefoneEstabelecimentoSaudeMarcacao;
    }

    public Integer getCnesEstabelecimentoSaudeAtendimento() {
      return cnesEstabelecimentoSaudeAtendimento;
    }

    public void setCnesEstabelecimentoSaudeAtendimento(
        Integer cnesEstabelecimentoSaudeAtendimento) {
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

    public String getCentral() {
      return central;
    }

    public void setCentral(String central) {
      this.central = central;
    }

    public String getOrientacoes() {
      return orientacoes;
    }

    public void setOrientacoes(String orientacoes) {
      this.orientacoes = orientacoes;
    }

  }

  @JsonInclude(Include.NON_NULL)
  class ProfissionalExecutante implements Serializable {

    private static final long serialVersionUID = -8673455363530235637L;

    private String nomeMedico;

    private String cpfMedico;

    private String tipoSolicitante;

    /**
     * Instantiates a new profissional executante.
     */
    public ProfissionalExecutante() {}

    /**
     * Instantiates a new profissional executante.
     *
     * @param nomeMedico the nome medico
     * @param cpfMedico the cpf medico
     * @param tipoSolicitante the tipo solicitante
     */
    public ProfissionalExecutante(String nomeMedico, String cpfMedico, String tipoSolicitante) {
      this.nomeMedico = nomeMedico;
      this.cpfMedico = cpfMedico;
      this.tipoSolicitante = tipoSolicitante;
    }

    public String getNomeMedico() {
      return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
      this.nomeMedico = nomeMedico;
    }

    public String getCpfMedico() {
      return cpfMedico;
    }

    public void setCpfMedico(String cpfMedico) {
      this.cpfMedico = cpfMedico;
    }

    public String getTipoSolicitante() {
      return tipoSolicitante;
    }

    public void setTipoSolicitante(String tipoSolicitante) {
      this.tipoSolicitante = tipoSolicitante;
    }

  }
}
