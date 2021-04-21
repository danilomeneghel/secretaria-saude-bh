package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.annotations.NotEmpty;

@JsonInclude(Include.NON_NULL)
public class Agendamento implements Serializable {

  private static final long serialVersionUID = 3504618984134554713L;

  @NotNull(message = "O campo numeroSolicitacaoSISREDE é obrigatório.")
  private Integer numeroSolicitacaoSISREDE;

  @NotNull(message = "O campo tipoBusca é obrigatório.")
  private Integer tipoBusca;

  @NotNull(message = "O campo tipoConsulta é obrigatório.")
  private Integer tipoConsulta;

  @NotNull(message = "O campo tipoAtendimento é obrigatório.")
  private Integer tipoAtendimento;

  @NotEmpty(message = "O campo codigoProcedimento é obrigatório.")
  private String codigoProcedimento;

  @NotNull(message = "O campo prioridade é obrigatório.")
  private Integer prioridade;

  @NotNull(message = "O campo regulacao é obrigatório.")
  private Boolean regulacao;

  private Integer numeroSolicitacaoExterno;

  @NotEmpty(message = "O campo formaAviso é obrigatório.")
  private String formaAviso;

  @NotEmpty(message = "O campo motivoEncaminhamento é obrigatório.")
  private String motivoEncaminhamento;

  private String cpfMedicoRetorno;

  private String cnesUnidadeRetorno;

  @NotEmpty(message = "O campo orientacoes é obrigatório.")
  private String orientacoes;

  public Integer getNumeroSolicitacaoSISREDE() {
    return numeroSolicitacaoSISREDE;
  }

  public void setNumeroSolicitacaoSISREDE(Integer numeroSolicitacaoSISREDE) {
    this.numeroSolicitacaoSISREDE = numeroSolicitacaoSISREDE;
  }

  public Integer getTipoBusca() {
    return tipoBusca;
  }

  public void setTipoBusca(Integer tipoBusca) {
    this.tipoBusca = tipoBusca;
  }

  public Integer getTipoConsulta() {
    return tipoConsulta;
  }

  public void setTipoConsulta(Integer tipoConsulta) {
    this.tipoConsulta = tipoConsulta;
  }

  public Integer getTipoAtendimento() {
    return tipoAtendimento;
  }

  public void setTipoAtendimento(Integer tipoAtendimento) {
    this.tipoAtendimento = tipoAtendimento;
  }

  public String getCodigoProcedimento() {
    return codigoProcedimento;
  }

  public void setCodigoProcedimento(String codigoProcedimento) {
    this.codigoProcedimento = codigoProcedimento;
  }

  public Integer getPrioridade() {
    return prioridade;
  }

  public void setPrioridade(Integer prioridade) {
    this.prioridade = prioridade;
  }

  public Boolean getRegulacao() {
    return regulacao;
  }

  public void setRegulacao(Boolean regulacao) {
    this.regulacao = regulacao;
  }

  public Integer getNumeroSolicitacaoExterno() {
    return numeroSolicitacaoExterno;
  }

  public void setNumeroSolicitacaoExterno(Integer numeroSolicitacaoExterno) {
    this.numeroSolicitacaoExterno = numeroSolicitacaoExterno;
  }

  public String getFormaAviso() {
    return formaAviso;
  }

  public void setFormaAviso(String formaAviso) {
    this.formaAviso = formaAviso;
  }

  public String getMotivoEncaminhamento() {
    return motivoEncaminhamento;
  }

  public void setMotivoEncaminhamento(String motivoEncaminhamento) {
    this.motivoEncaminhamento = motivoEncaminhamento;
  }

  public String getCpfMedicoRetorno() {
    return cpfMedicoRetorno;
  }

  public void setCpfMedicoRetorno(String cpfMedicoRetorno) {
    this.cpfMedicoRetorno = cpfMedicoRetorno;
  }

  public String getCnesUnidadeRetorno() {
    return cnesUnidadeRetorno;
  }

  public void setCnesUnidadeRetorno(String cnesUnidadeRetorno) {
    this.cnesUnidadeRetorno = cnesUnidadeRetorno;
  }

  public String getOrientacoes() {
    return orientacoes;
  }

  public void setOrientacoes(String orientacoes) {
    this.orientacoes = orientacoes;
  }

}
