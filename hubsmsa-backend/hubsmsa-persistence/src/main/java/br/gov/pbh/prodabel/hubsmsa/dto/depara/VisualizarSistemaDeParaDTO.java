package br.gov.pbh.prodabel.hubsmsa.dto.depara;

import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class VisualizarSistemaDeParaDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = 8593609776606941536L;
  private String nomeEmpresa;
  private String nomeSistema;
  private List<CampoDeParaDTO> camposDePara;

  public String getNomeEmpresa() {
    return nomeEmpresa;
  }

  public void setNomeEmpresa(String nomeEmpresa) {
    this.nomeEmpresa = nomeEmpresa;
  }

  public String getNomeSistema() {
    return nomeSistema;
  }

  public void setNomeSistema(String nomeSistema) {
    this.nomeSistema = nomeSistema;
  }

  public List<CampoDeParaDTO> getCamposDePara() {
    return camposDePara;
  }

  public void setCamposDePara(List<CampoDeParaDTO> camposDePara) {
    this.camposDePara = camposDePara;
  }

}
