package br.gov.pbh.prodabel.hubsmsa.dto.depara;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class CampoDeParaDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = -8971686497864844292L;
  private String codigo;
  private String descricao;

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
