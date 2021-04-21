package br.gov.pbh.prodabel.hubsmsa.persistence.audit;

import javax.ejb.Singleton;

@Singleton
public class UserRevisionService {

  private AuditUser user = new AuditUser("hubsmsa", "us-hubsmsa", "hubsmsa");

  /**
   * Salvar detalhes do usuário autenticado para auditoria na requisição
   *
   * @param nome - Nome do usuário autenticado
   * @param matricula - Matrícula ou CPF/CNPJ do usuário autenticado
   * @param email - Email do usuário autenticado
   */
  public void salvar(final String nome, final String matricula, final String email) {
    this.user = new AuditUser(nome, matricula, email);
  }

  /**
   * Recuperar usuário para auditoria
   *
   * @return {@link AuditUser} - Usuário de auditoria
   */
  public AuditUser getAuditUser() {
    return this.user;
  }

}