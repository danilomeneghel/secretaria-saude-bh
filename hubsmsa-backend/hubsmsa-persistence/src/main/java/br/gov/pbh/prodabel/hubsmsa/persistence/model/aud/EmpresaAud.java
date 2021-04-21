package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "empresa_aud")
public class EmpresaAud extends EntidadeBase<Long> {

  private static final long serialVersionUID = 3136288753409133915L;

  @Id
  @Column(name = "id_empresa", nullable = false, unique = true)
  private Long id;

  @Column(name = "nome_empresarial", nullable = false, length = 4)
  private String nomeEmpresarial;

  @Column(name = "nome_fantasia", nullable = false, length = 100)
  private String nomeFantasia;

  @Column(name = "cnpj", nullable = false)
  private String cnpj;

  @Column(name = "codigo_cnes", nullable = false)
  private Long codigoCnes;

  @Column(name = "site", nullable = false)
  private String site;

  @Column(name = "ativo", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusEnum ativo;

  @JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_empresa_aud"),
      nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Revision revisao;

  @Column(name = "rev_type")
  @Enumerated(EnumType.ORDINAL)
  private RevisionType revType;

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

  public StatusEnum getAtivo() {
    return ativo;
  }

  public void setAtivo(StatusEnum ativo) {
    this.ativo = ativo;
  }

  public Long getCodigoCnes() {
    return codigoCnes;
  }

  public void setCodigoCnes(Long codigoCnes) {
    this.codigoCnes = codigoCnes;
  }

  public Revision getRevisao() {
    return revisao;
  }

  public void setRevisao(Revision revisao) {
    this.revisao = revisao;
  }

  public RevisionType getRevType() {
    return revType;
  }

  public void setRevType(RevisionType revType) {
    this.revType = revType;
  }

}
