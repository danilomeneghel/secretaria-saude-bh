package br.gov.pbh.prodabel.hubsmsa.ws.dto;

import java.io.Serializable;

public class ServicoResponseDTO implements Serializable {

  private static final long serialVersionUID = -2767578641273955663L;

  private Long codigoRetorno;

  private String descricaoErro;

  public Long getCodigoRetorno() {
    return codigoRetorno;
  }

  public void setCodigoRetorno(Long codigoRetorno) {
    this.codigoRetorno = codigoRetorno;
  }

  public String getDescricaoErro() {
    return descricaoErro;
  }

  public void setDescricaoErro(String descricaoErro) {
    this.descricaoErro = descricaoErro;
  }

}
