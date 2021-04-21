package br.gov.pbh.prodabel.hubsmsa.dto.empresa;

import java.io.Serializable;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

/**
 * 
 * @author weverton.lucena@ctis.com.br Classe responsável por transportar dados de EmpresaAud
 *
 */
public class DadosEmpresaAudDTO implements Serializable {

  private static final long serialVersionUID = 7256870475281320370L;

  private Long id;
  private Revision revisao;
  private RevisionType revType;
  private String nomeEmpresarial;
  private String nomeFantasia;
  private String cnpj;
  private Long codigoCnes;
  private String site;
  private StatusEnum ativo;

  /**
   * Instantiates a new dados empresa aud DTO.
   */
  public DadosEmpresaAudDTO() {}

  /**
   * Instantiates a new dados empresa aud DTO.
   *
   * @param id the id
   * @param revisao the revisao
   * @param revType the rev type
   * @param nomeEmpresarial the nome empresarial
   * @param nomeFantasia the nome fantasia
   * @param cnpj the cnpj
   * @param codigoCnes the codigo cnes
   * @param site the site
   * @param status the status
   */
  public DadosEmpresaAudDTO(Long id, Revision revisao, RevisionType revType, String nomeEmpresarial,
      String nomeFantasia, String cnpj, Long codigoCnes, String site, StatusEnum ativo) {
    super();
    this.id = id;
    this.revisao = revisao;
    this.revType = revType;
    this.nomeEmpresarial = nomeEmpresarial;
    this.nomeFantasia = nomeFantasia;
    this.cnpj = cnpj;
    this.codigoCnes = codigoCnes;
    this.site = site;
    this.ativo = ativo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getCodigoCnes() {
    return codigoCnes;
  }

  public void setCodigoCnes(Long codigoCnes) {
    this.codigoCnes = codigoCnes;
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

  public void setAtivo(StatusEnum status) {
    this.ativo = status;
  }

    
}
