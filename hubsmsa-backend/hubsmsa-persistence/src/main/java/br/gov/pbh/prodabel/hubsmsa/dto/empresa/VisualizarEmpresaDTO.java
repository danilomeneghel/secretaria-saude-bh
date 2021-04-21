package br.gov.pbh.prodabel.hubsmsa.dto.empresa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@JsonInclude(Include.NON_NULL)
public class VisualizarEmpresaDTO extends BaseDTO {

  private static final long serialVersionUID = 6562450424844788816L;

  private Long id;

  private String nomeEmpresarial;

  private String nomeFantasia;

  private String cnpj;

  private Long cnes;

  private String site;

  private StatusEnum status;

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

  public Long getCnes() {
    return cnes;
  }

  public void setCnes(Long cnes) {
    this.cnes = cnes;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

}
