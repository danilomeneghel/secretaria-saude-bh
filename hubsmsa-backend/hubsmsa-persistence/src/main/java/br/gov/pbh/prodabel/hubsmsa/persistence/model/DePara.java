package br.gov.pbh.prodabel.hubsmsa.persistence.model;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@Audited
@Table(name = "de_para")
@SequenceGenerator(sequenceName = "sq_de_para", name = "sq_de_para", allocationSize = 1)
@NamedEntityGraph(name = "de-para-load-graph",
    attributeNodes = {
        @NamedAttributeNode("id"), 
        @NamedAttributeNode("tipoDePara"),
        @NamedAttributeNode(value = "sistemaPrimario", subgraph = "empresa-subgraph"),
        @NamedAttributeNode(value = "sistemaSecundario", subgraph = "empresa-subgraph"),
        @NamedAttributeNode("deParaPrimario"), 
        @NamedAttributeNode("deParaSecundario"),
        @NamedAttributeNode("nome"),
    }, subgraphs = {
        @NamedSubgraph(name = "empresa-subgraph",
        attributeNodes = {
            @NamedAttributeNode("empresa")
        })
    })
public class DePara extends EntidadeBase<Long> {

  private static final long serialVersionUID = -6360125537072481806L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_de_para")
  @Column(name = "id_de_para", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_de_para", foreignKey = @ForeignKey(name = "fk_tipo_de_para_de_para"),
      nullable = false)
  private TipoDePara tipoDePara;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_primario",
      foreignKey = @ForeignKey(name = "fk_sistema_de_para_primario"), nullable = false)
  private Sistema sistemaPrimario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_secundario",
      foreignKey = @ForeignKey(name = "fk_sistema_de_para_secundario"), nullable = false)
  private Sistema sistemaSecundario;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dePara", cascade = {CascadeType.ALL})
  private Set<DeParaPrimario> deParaPrimario;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "dePara", cascade = {CascadeType.ALL})
  private Set<DeParaSecundario> deParaSecundario;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @NotAudited
  @Column(name = "data_atualizacao", nullable = false)
  private LocalDate dataAtualizacao;

  @Column(name = "ind_forma_cadastro", nullable = false)
  @Enumerated(EnumType.STRING)
  private FormaCadastroEnum formaCadastro;

  @Transient
  private String tipoDeParaNome;

  @Transient
  private String empresaPrimarioNome;

  @Transient
  private String empresaSecundarioNome;

  @Transient
  private String sistemaPrimarioNome;

  @Transient
  private String sistemaSecundarioNome;

  @Transient
  private String codigosSistemaPrimario;

  @Transient
  private String codigosSistemaSecundario;

  @Transient
  private String descricaoSistemaPrimario;

  @Transient
  private String descricaoSistemaSecundario;

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

  public Set<DeParaPrimario> getDeParaPrimario() {
    return this.deParaPrimario;
  }

  public void setDeParaPrimario(final Set<DeParaPrimario> deParaPrimario) {
    this.deParaPrimario = deParaPrimario;
  }

  public Set<DeParaSecundario> getDeParaSecundario() {
    return this.deParaSecundario;
  }

  public void setDeParaSecundario(final Set<DeParaSecundario> deParaSecundario) {
    this.deParaSecundario = deParaSecundario;
  }

  public String getTipoDeParaNome() {
    return tipoDeParaNome;
  }

  public void setTipoDeParaNome(String tipoDeParaNome) {
    this.tipoDeParaNome = tipoDeParaNome;
  }

  public String getSistemaPrimarioNome() {
    return sistemaPrimarioNome;
  }

  public void setSistemaPrimarioNome(String sistemaPrimarioNome) {
    this.sistemaPrimarioNome = sistemaPrimarioNome;
  }

  public String getSistemaSecundarioNome() {
    return sistemaSecundarioNome;
  }

  public void setSistemaSecundarioNome(String sistemaSecundarioNome) {
    this.sistemaSecundarioNome = sistemaSecundarioNome;
  }

  public String getCodigosSistemaPrimario() {
    return codigosSistemaPrimario;
  }

  public void setCodigosSistemaPrimario(String codigosSistemaPrimario) {
    this.codigosSistemaPrimario = codigosSistemaPrimario;
  }

  public String getCodigosSistemaSecundario() {
    return codigosSistemaSecundario;
  }

  public void setCodigosSistemaSecundario(String codigosSistemaSecundario) {
    this.codigosSistemaSecundario = codigosSistemaSecundario;
  }

  public String getDescricaoSistemaPrimario() {
    return descricaoSistemaPrimario;
  }

  public void setDescricaoSistemaPrimario(String descricaoSistemaPrimario) {
    this.descricaoSistemaPrimario = descricaoSistemaPrimario;
  }

  public String getDescricaoSistemaSecundario() {
    return descricaoSistemaSecundario;
  }

  public void setDescricaoSistemaSecundario(String descricaoSistemaSecundario) {
    this.descricaoSistemaSecundario = descricaoSistemaSecundario;
  }

  public String getEmpresaPrimarioNome() {
    return empresaPrimarioNome;
  }

  public void setEmpresaPrimarioNome(String empresaPrimarioNome) {
    this.empresaPrimarioNome = empresaPrimarioNome;
  }

  public String getEmpresaSecundarioNome() {
    return empresaSecundarioNome;
  }

  public void setEmpresaSecundarioNome(String empresaSecundarioNome) {
    this.empresaSecundarioNome = empresaSecundarioNome;
  }


}
