package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateDeserializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.DateSerializer;
import br.gov.pbh.prodabel.hubsmsa.util.json.date.Temporal;

@Entity
@Table(name = "agendamento_realizado")
public class AgendamentoRealizado extends EntidadeBase<Long> {

  private static final long serialVersionUID = -7910487175348483684L;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "ID_AGENDAMENTO_REALIZADO")
  private Long id;

  @Column(name = "NU_FICHA_ATEND")
  private Integer numeroAgendamentoSigrah;

  @Column(name = "TP_ATEND")
  private Integer tipoAtendimento;

  @Column(name = "DATA_AGENDA")
  @Temporal(format = "yyyy-MM-dd HH:mm")
  @JsonDeserialize(using = DateDeserializer.class)
  @JsonSerialize(using = DateSerializer.class)
  private LocalDateTime dataHoraAgendamento;

  @Column(name = "PRIORIDADE")
  private Integer prioridade;

  @Column(name = "TP_MARCA")
  private Integer tipoConsulta;

  @Column(name = "CD_PROCD")
  private String codigoProcedimento;

  @Column(name = "CNES_SOLICITANTE")
  private Integer cnesEstabelecimentoSaudeMarcacao;

  @Column(name = "NOME_ESTABELECIMENTO_SAUDE_MARCADO")
  private String nomeEstabelecimentoSaudeMarcacao;

  @Column(name = "ENDERECO_ESTABELECIMENTO_SAUDE_MARCADO")
  private String enderecoEstabelecimentoSaudeMarcacao;

  @Column(name = "TELEFONE_ESTABELECIMENTO_SAUDE_MARCADO")
  private String telefoneEstabelecimentoSaudeMarcacao;

  @Column(name = "CNES_EXEC")
  private Integer cnesEstabelecimentoSaudeAtendimento;

  @Column(name = "NOME_ESTABELECIMENTO_SAUDE")
  private String nomeEstabelecimentoSaudeAtendimento;

  @Column(name = "ENDERECO_ESTABELECIMENTO_SAUDE")
  private String enderecoEstabelecimentoSaudeAtendimento;

  @Column(name = "TELEFONE_ESTABELECIMENTO_SAUDE")
  private String telefoneEstabelecimentoSaudeAtendimento;

  @Column(name = "EN_REF")
  private String formaAviso;

  @Column(name = "PREPARO")
  private String orientacoes;

  @Column(name = "NM_PESS_CONS")
  private String nomePaciente;

  @Column(name = "NM_MAE_CONS")
  private String nomeMaePaciente;

  @Column(name = "CPF")
  private String cpf;

  @Column(name = "NUMERO_CNS")
  private String cns;

  @Column(name = "DT_NASC")
  private String dataNascimento;

  @Column(name = "SEXO_PACIENTE")
  private String sexo;

  @Column(name = "NATURALIDADE_PACIENTE")
  private String municipioNascimento;

  @Column(name = "DISTRITO")
  private Integer distrito;

  @Column(name = "TELEFONE_PACIENTE")
  private String telefone;

  @Column(name = "CPF_PROFISSIONAL_SAUDE")
  private String cpfMedicoSolicitante;

  @Column(name = "NOME_PROFISSIONAL_SAUDE")
  private String nomeMedicoSolicitante;

  @Column(name = "CBO_PROFISSIONAL_SAUDE")
  private String cboMedicoSolicitante;

  @Column(name = "CPF_PROFISSIONAL_SAUDE_ATENDIMENTO")
  private String cpfMedicoExecutante;

  @Column(name = "NOME_PROFISSIONAL_SAUDE_AGENDADO")
  private String nomeMedicoExecutante;

  @Column(name = "CBO_PROFISSIONAL_SAUDE_ATENDIMENTO")
  private String cboMedicoExecutante;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Integer getDistrito() {
    return distrito;
  }

  public void setDistrito(Integer distrito) {
    this.distrito = distrito;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getCpfMedicoSolicitante() {
    return cpfMedicoSolicitante;
  }

  public void setCpfMedicoSolicitante(String cpfMedicoSolicitante) {
    this.cpfMedicoSolicitante = cpfMedicoSolicitante;
  }

  public String getNomeMedicoSolicitante() {
    return nomeMedicoSolicitante;
  }

  public void setNomeMedicoSolicitante(String nomeMedicoSolicitante) {
    this.nomeMedicoSolicitante = nomeMedicoSolicitante;
  }

  public String getCboMedicoSolicitante() {
    return cboMedicoSolicitante;
  }

  public void setCboMedicoSolicitante(String cboMedicoSolicitante) {
    this.cboMedicoSolicitante = cboMedicoSolicitante;
  }

  public String getCpfMedicoExecutante() {
    return cpfMedicoExecutante;
  }

  public void setCpfMedicoExecutante(String cpfMedicoExecutante) {
    this.cpfMedicoExecutante = cpfMedicoExecutante;
  }

  public String getNomeMedicoExecutante() {
    return nomeMedicoExecutante;
  }

  public void setNomeMedicoExecutante(String nomeMedicoExecutante) {
    this.nomeMedicoExecutante = nomeMedicoExecutante;
  }

  public String getCboMedicoExecutante() {
    return cboMedicoExecutante;
  }

  public void setCboMedicoExecutante(String cboMedicoExecutante) {
    this.cboMedicoExecutante = cboMedicoExecutante;
  }

}
