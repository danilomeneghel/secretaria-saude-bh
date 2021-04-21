package br.gov.pbh.prodabel.hubsmsa.persistence.audit;

import java.io.Serializable;

public class AuditUser implements Serializable {

  private static final long serialVersionUID = 639653495435829810L;

  private String nomeUsuario;
  private String matricula;
  private String email;

  public AuditUser(final String nomeUsuario, final String matricula, final String email) {
    super();
    this.nomeUsuario = nomeUsuario;
    this.matricula = matricula;
    this.email = email;
  }

  public String getNomeUsuario() {
    return this.nomeUsuario;
  }

  public void setNomeUsuario(final String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getMatricula() {
    return this.matricula;
  }

  public void setMatricula(final String matricula) {
    this.matricula = matricula;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

}
