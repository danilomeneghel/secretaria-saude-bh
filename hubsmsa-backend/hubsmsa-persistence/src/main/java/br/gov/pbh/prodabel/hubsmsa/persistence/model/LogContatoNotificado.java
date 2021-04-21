package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "log_contato_notificado")
@SequenceGenerator(sequenceName = "sq_log_contato_notificado", name = "sq_log_contato_notificado",
    allocationSize = 1)
public class LogContatoNotificado extends EntidadeBase<Long> {

  private static final long serialVersionUID = 284146645473410931L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_log_contato_notificado")
  @Column(name = "id_log_contato_notificado", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_log_servico", foreignKey = @ForeignKey(name = "fk_log_servico"),
      nullable = false)
  private LogServico logServico;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_contato_empresa_servico",
      foreignKey = @ForeignKey(name = "fk_contato_empresa_servico"), nullable = false)
  private ContatoEmpresaServico contatoEmpresaServico;

  @Column(name = "notificacao_sucesso", length = 1)
  private String notificacaoSucesso;

  @Column(name = "notificacao_falha", length = 1)
  private String notificacaoFalha;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LogServico getLogServico() {
    return logServico;
  }

  public void setLogServico(LogServico logServico) {
    this.logServico = logServico;
  }

  public ContatoEmpresaServico getContatoEmpresaServico() {
    return contatoEmpresaServico;
  }

  public void setContatoEmpresaServico(ContatoEmpresaServico contatoEmpresaServico) {
    this.contatoEmpresaServico = contatoEmpresaServico;
  }

  public String getNotificacaoSucesso() {
    return notificacaoSucesso;
  }

  public void setNotificacaoSucesso(String notificacaoSucesso) {
    this.notificacaoSucesso = notificacaoSucesso;
  }

  public String getNotificacaoFalha() {
    return notificacaoFalha;
  }

  public void setNotificacaoFalha(String notificacaoFalha) {
    this.notificacaoFalha = notificacaoFalha;
  }

}
