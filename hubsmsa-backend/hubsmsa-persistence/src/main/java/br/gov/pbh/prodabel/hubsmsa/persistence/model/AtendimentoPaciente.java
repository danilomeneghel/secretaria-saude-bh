package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atendimento_paciente")
public class AtendimentoPaciente extends EntidadeBase<Long> implements Serializable {

  private static final long serialVersionUID = -235518446308054724L;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "ID_ATENDIMENTO_REALIZADO")
  private Long id;

  @Column(name = "NU_FICHA_ATEND")
  private Long numeroAgendamentoSigrah;

  @Column(name = "STATUS")
  private String statusAtendimento;

  @Column(name = "NU_SOLICITACAO")
  private Long numeroSolicitacaoSISREDE;

  @Column(name = "NM_PESS_CONS")
  private String nomePaciente;

  @Column(name = "NM_MAE_CONS")
  private String nomeMaePaciente;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "NU_CNS_MS")
  private String cns;

  @Column(name = "DT_NASC")
  private String dataNascimento;

  @Column(name = "CD_SEXO")
  private String sexo;

  @Column(name = "CD_MUNC_NASC")
  private String municipioNascimento;

  @Column(name = "TELEFONE_PACIENTE")
  private String telefone;

  // campo incluído para ser usado de filtro na query
  @Column(name = "DT_ATEND")
  private LocalDateTime dataAtendimento;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getNumeroAgendamentoSigrah() {
    return numeroAgendamentoSigrah;
  }

  public void setNumeroAgendamentoSigrah(Long numeroAgendamentoSigrah) {
    this.numeroAgendamentoSigrah = numeroAgendamentoSigrah;
  }

  public Long getNumeroSolicitacaoSISREDE() {
    return numeroSolicitacaoSISREDE;
  }

  public void setNumeroSolicitacaoSISREDE(Long numeroSolicitacaoSISREDE) {
    this.numeroSolicitacaoSISREDE = numeroSolicitacaoSISREDE;
  }

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public String getNomeMaePaciente() {
    return nomeMaePaciente;
  }

  public void setNomeMaePaciente(String nomeMaePaciente) {
    this.nomeMaePaciente = nomeMaePaciente;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCns() {
    return cns;
  }

  public void setCns(String cns) {
    this.cns = cns;
  }

  public String getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(String dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getMunicipioNascimento() {
    return municipioNascimento;
  }

  public void setMunicipioNascimento(String municipioNascimento) {
    this.municipioNascimento = municipioNascimento;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public LocalDateTime getDataAtendimento() {
    return dataAtendimento;
  }

  public void setDataAtendimento(LocalDateTime dataAtendimento) {
    this.dataAtendimento = dataAtendimento;
  }

  public String getStatusAtendimento() {
    return statusAtendimento;
  }

  public void setStatusAtendimento(String statusAtendimento) {
    this.statusAtendimento = statusAtendimento;
  }

}
