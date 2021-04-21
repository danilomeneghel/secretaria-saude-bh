package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@SequenceGenerator(sequenceName = "sq_empresa", name = "sq_empresa", allocationSize = 1)
@Table(name = "empresa")
@Audited
public class Empresa extends EntidadeBase<Long> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_empresa")
  @Column(name = "id_empresa", nullable = false, unique = true)
  private Long id;

  @Column(name = "nome_empresarial", nullable = false, length = 100)
  private String nomeEmpresarial;

  @Column(name = "nome_fantasia", nullable = false, length = 100)
  private String nomeFantasia;

  @Column(name = "cnpj", nullable = true, length = 14)
  private String cnpj;

  @Column(name = "codigo_cnes", nullable = false)
  private Long codigoCnes;

  @Column(name = "site", nullable = false)
  private String site;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum ativo;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeEmpresarial() {
    return nomeEmpresarial;
  }

  public void setNomeEmpresarial(String nomeEmpresarial) {
    this.nomeEmpresarial = nomeEmpresarial;
  }

  public String getNomeFantasia() {
    return nomeFantasia;
  }

  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public Long getCodigoCnes() {
    return codigoCnes;
  }

  public void setCodigoCnes(Long codigoCnes) {
    this.codigoCnes = codigoCnes;
  }

  public StatusEnum getAtivo() {
    return ativo;
  }

  public void setAtivo(StatusEnum ativo) {
    this.ativo = ativo;
  }

}
