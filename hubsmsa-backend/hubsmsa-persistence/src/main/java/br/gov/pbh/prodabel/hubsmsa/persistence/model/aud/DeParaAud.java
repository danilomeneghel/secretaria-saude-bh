package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;

@Entity
@Table(name = "de_para_aud")
@SequenceGenerator(name = "sq_de_para_aud", sequenceName = "sq_de_para_aud")
public class DeParaAud extends EntidadeBase<Long> {

  private static final long serialVersionUID = 8980467199708529279L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_de_para_aud")
  @Column(name = "id_de_para", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_de_para", foreignKey = @ForeignKey(name = "fk_tipo_de_para_de_para"), nullable = false)
  private TipoDePara tipoDePara;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_primario", foreignKey = @ForeignKey(name = "fk_sistema_de_para_primario"), nullable = false)
  private Sistema sistemaPrimario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_secundario", foreignKey = @ForeignKey(name = "fk_sistema_de_para_secundario"), nullable = false)
  private Sistema sistemaSecundario;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "codigo_primario", nullable = false)
  private Long codigoPrimario;

  @Column(name = "codigo_secundario", nullable = false)
  private Long codigoSecundario;

  @Column(name = "descricao_primario", nullable = false, length = 75)
  private String descricaoPrimario;

  @Column(name = "descricao_secundario", nullable = false, length = 75)
  private String descricaoSecundario;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dePara", cascade = {CascadeType.ALL})
  private Set<DeParaPrimario> deParaPrimario;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dePara", cascade = {CascadeType.ALL})
  private Set<DeParaSecundario> deParaSecundario;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @NotAudited
  @Column(name = "data_atualizacao", nullable = false)
  private LocalDate dataAtualizacao;

  @Column(name = "ind_forma_cadastro", nullable = false)
  @Enumerated(EnumType.STRING)
  private FormaCadastroEnum formaCadastro;

  @JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_de_para_aud"), nullable = false)
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

  public TipoDePara getTipoDePara() {
    return this.tipoDePara;
  }

  public void setTipoDePara(final TipoDePara tipoDePara) {
    this.tipoDePara = tipoDePara;
  }

  public Sistema getSistemaPrimario() {
    return this.sistemaPrimario;
  }

  public void setSistemaPrimario(final Sistema sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public Sistema getSistemaSecundario() {
    return this.sistemaSecundario;
  }

  public void setSistemaSecundario(final Sistema sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(final String nome) {
    this.nome = nome;
  }

  public Long getCodigoPrimario() {
    return this.codigoPrimario;
  }

  public void setCodigoPrimario(final Long codigoPrimario) {
    this.codigoPrimario = codigoPrimario;
  }

  public Long getCodigoSecundario() {
    return this.codigoSecundario;
  }

  public void setCodigoSecundario(final Long codigoSecundario) {
    this.codigoSecundario = codigoSecundario;
  }

  public String getDescricaoPrimario() {
    return this.descricaoPrimario;
  }

  public void setDescricaoPrimario(final String descricaoPrimario) {
    this.descricaoPrimario = descricaoPrimario;
  }

  public String getDescricaoSecundario() {
    return this.descricaoSecundario;
  }

  public void setDescricaoSecundario(final String descricaoSecundario) {
    this.descricaoSecundario = descricaoSecundario;
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

  public Set<DeParaPrimario> getDeParaPrimario() {
    return deParaPrimario;
  }

  public void setDeParaPrimario(Set<DeParaPrimario> deParaPrimario) {
    this.deParaPrimario = deParaPrimario;
  }

  public Set<DeParaSecundario> getDeParaSecundario() {
    return deParaSecundario;
  }

  public void setDeParaSecundario(Set<DeParaSecundario> deParaSecundario) {
    this.deParaSecundario = deParaSecundario;
  }

}
