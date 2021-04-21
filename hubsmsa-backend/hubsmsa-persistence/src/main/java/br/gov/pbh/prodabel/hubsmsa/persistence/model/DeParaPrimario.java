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
import org.hibernate.envers.Audited;

@Entity
@SequenceGenerator(sequenceName = "sq_de_para_primario", name = "sq_de_para_primario", allocationSize = 1)
@Table(name = "de_para_primario")
@Audited
public class DeParaPrimario extends EntidadeBase<Long> {



  /**
   * 
   */
  private static final long serialVersionUID = 4117515914534649587L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_de_para_primario")
  @Column(name = "id_de_para_primario", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_de_para", foreignKey = @ForeignKey(name = "fk_de_para_de_para_primario"))
  private DePara dePara;

  @Column(name = "codigo", nullable = false, length = 10)
  private String codigo;

  @Column(name = "descricao", nullable = false, length = 75)
  private String descricao;


  @Override
  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public DePara getDePara() {
    return this.dePara;
  }

  public void setDePara(final DePara dePara) {
    this.dePara = dePara;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public void setDescricao(final String descricao) {
    this.descricao = descricao;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (this.codigo == null ? 0 : this.codigo.hashCode());
    result = prime * result + (this.descricao == null ? 0 : this.descricao.hashCode());
    result = prime * result + (this.id == null ? 0 : this.id.hashCode());
    result = prime * result + (this.dePara == null ? 0 : this.dePara.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DeParaPrimario other = (DeParaPrimario) obj;
    if (this.codigo == null) {
      if (other.codigo != null) {
        return false;
      }
    } else if (!this.codigo.equals(other.codigo)) {
      return false;
    }
    if (this.descricao == null) {
      if (other.descricao != null) {
        return false;
      }
    } else if (!this.descricao.equals(other.descricao)) {
      return false;
    }
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.dePara == null) {
      if (other.dePara != null) {
        return false;
      }
    } else if (!this.dePara.equals(other.dePara)) {
      return false;
    }
    return true;
  }
  
}
