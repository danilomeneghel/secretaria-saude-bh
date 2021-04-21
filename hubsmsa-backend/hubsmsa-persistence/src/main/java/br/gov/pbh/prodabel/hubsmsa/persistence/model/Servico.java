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
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@SequenceGenerator(sequenceName = "sq_servico", name = "sq_servico", allocationSize = 1)
@Table(name = "servico")
public class Servico extends EntidadeBase<Long> {
  
  private static final long serialVersionUID = -8051263385457208410L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_servico")
  @Column(name = "id_servico", nullable = false, unique = true)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_primario",
      foreignKey = @ForeignKey(name = "fk_sistema_de_para_primario"), nullable = false)
  private Sistema sistemaPrimario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_sistema_secundario",
      foreignKey = @ForeignKey(name = "fk_sistema_de_para_secundario"), nullable = false)
  private Sistema sistemaSecundario;
  
  @Column(name = "nome", nullable = false, length = 50)
  private String nome;
  
  @Column(name = "descricao", nullable = false, length = 300)
  private String descricao;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Sistema getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(Sistema sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public Sistema getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(Sistema sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }
  
}
