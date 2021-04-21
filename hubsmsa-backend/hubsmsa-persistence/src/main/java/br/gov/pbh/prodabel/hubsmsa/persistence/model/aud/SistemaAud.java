package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

import java.time.LocalDate;
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
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "sistema_aud")
@SequenceGenerator(name = "sq_sistema_aud", sequenceName = "sq_sistema_aud")
public class SistemaAud extends EntidadeBase<Long>{

  private static final long serialVersionUID = 4241671695976551820L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_sistema_aud")
  @Column(name = "id_sistema", nullable = false, unique = true)
  private Long id;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "descricao", nullable = false, length = 250)
  private String descricao;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @NotAudited
  @Column(name = "data_atualizacao", nullable = false)
  private LocalDate dataAtualizacao;

  @Column(name = "ind_forma_cadastro", nullable = false)
  @Enumerated(EnumType.STRING)
  private FormaCadastroEnum formaCadastro;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_sistema_empresa"), nullable = false)
  private Empresa empresa;

  @JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_empresa_aud"), nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Revision revisao;

  @Column(name = "rev_type")
  @Enumerated(EnumType.ORDINAL)
  private RevisionType revType;

  @Override
  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  public StatusEnum getStatus() {
    return this.status;
  }

  public void setStatus(final StatusEnum status) {
    this.status = status;
  }

  public LocalDate getDataAtualizacao() {
    return this.dataAtualizacao;
  }

  public void setDataAtualizacao(final LocalDate dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  public FormaCadastroEnum getFormaCadastro() {
    return this.formaCadastro;
  }

  public void setFormaCadastro(final FormaCadastroEnum formaCadastro) {
    this.formaCadastro = formaCadastro;
  }

  public Empresa getEmpresa() {
    return this.empresa;
  }

  public void setEmpresa(final Empresa empresa) {
    this.empresa = empresa;
  }

  public Revision getRevisao() {
    return this.revisao;
  }

  public void setRevisao(final Revision revisao) {
    this.revisao = revisao;
  }

  public RevisionType getRevType() {
    return this.revType;
  }

  public void setRevType(final RevisionType revType) {
    this.revType = revType;
  }

}
