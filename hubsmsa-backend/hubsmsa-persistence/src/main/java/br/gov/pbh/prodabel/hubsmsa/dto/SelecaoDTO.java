package br.gov.pbh.prodabel.hubsmsa.dto;

import java.io.Serializable;

/**
 * 
 * @author claudivan.moreira
 * DTO utilizado nos campos de selecao (combo, multiselects, checkbox, radios)
 */
public class SelecaoDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = 7769576076378927713L;
  private Serializable id;
  private String descricao;

  public SelecaoDTO() {}

  public SelecaoDTO(Serializable id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  public Serializable getId() {
    return id;
  }

  public void setId(Serializable id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
