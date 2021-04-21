package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.gov.pbh.prodabel.hubsmsa.enumeration.SimNaoEnum;

@Entity
@Table(name = "contato_empresa_servico")
@SequenceGenerator(sequenceName = "sq_contato_empresa_servico", name = "sq_contato_empresa_servico",
    allocationSize = 1)
public class ContatoEmpresaServico extends EntidadeBase<Long> {

  private static final long serialVersionUID = -3979940003443957049L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_contato_empresa_servico")
  @Column(name = "id_contato_empresa_servico", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_contato_empresa", foreignKey = @ForeignKey(name = "fk_contato_empresa"),
      nullable = false)
  private ContatoEmpresa contatoEmpresa;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_servico", foreignKey = @ForeignKey(name = "fk_servico"), nullable = false)
  private Servico servico;

  @Column(name = "notificacao_sucesso", length = 1, nullable = false)
  @Enumerated(EnumType.STRING)
  private SimNaoEnum notificacaoSucesso;

  @Column(name = "notificacao_falha", length = 1, nullable = false)
  @Enumerated(EnumType.STRING)
  private SimNaoEnum notificacaoFalha;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ContatoEmpresa getContatoEmpresa() {
    return contatoEmpresa;
  }

  public void setContatoEmpresa(ContatoEmpresa contatoEmpresa) {
    this.contatoEmpresa = contatoEmpresa;
  }

  public Servico getServico() {
    return servico;
  }

  public void setServico(Servico servico) {
    this.servico = servico;
  }

  public SimNaoEnum getNotificacaoSucesso() {
    return notificacaoSucesso;
  }

  public void setNotificacaoSucesso(SimNaoEnum notificacaoSucesso) {
    this.notificacaoSucesso = notificacaoSucesso;
  }

  public SimNaoEnum getNotificacaoFalha() {
    return notificacaoFalha;
  }

  public void setNotificacaoFalha(SimNaoEnum notificacaoFalha) {
    this.notificacaoFalha = notificacaoFalha;
  }


}
