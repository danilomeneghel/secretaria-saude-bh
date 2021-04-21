package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

@JsonInclude(Include.NON_NULL)
public class HistoricoContatoEmpresaDTO implements Serializable {

  private static final long serialVersionUID = -3167519610554053553L;

  private Long idContatoEmpresa;

  private String nome;

  private String setor;

  private String nomeUsuario;

  private String emailUsuario;

  private String email;

  private String login;

  private String telefone;

  private String status;

  private String nomeEmpresa;

  private String revisao;

  private String tipoRevisao;

  private String dataEvento;

  private Revision revision;

  public Long getIdContatoEmpresa() {
    return idContatoEmpresa;
  }

  public void setIdContatoEmpresa(Long idContatoEmpresa) {
    this.idContatoEmpresa = idContatoEmpresa;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSetor() {
    return setor;
  }

  public void setSetor(String setor) {
    this.setor = setor;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRevisao() {
    return revisao;
  }

  public void setRevisao(String revisao) {
    this.revisao = revisao;
  }

  public String getTipoRevisao() {
    return tipoRevisao;
  }

  public void setTipoRevisao(String tipoRevisao) {
    this.tipoRevisao = tipoRevisao;
  }

  public String getDataEvento() {
    return dataEvento;
  }

  public void setDataEvento(String dataEvento) {
    this.dataEvento = dataEvento;
  }

  public Revision getRevision() {
    return revision;
  }

  public void setRevision(Revision revision) {
    this.revision = revision;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getNomeEmpresa() {
    return nomeEmpresa;
  }

  public void setNomeEmpresa(String nomeEmpresa) {
    this.nomeEmpresa = nomeEmpresa;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getEmailUsuario() {
    return emailUsuario;
  }

  public void setEmailUsuario(String emailUsuario) {
    this.emailUsuario = emailUsuario;
  }

}
