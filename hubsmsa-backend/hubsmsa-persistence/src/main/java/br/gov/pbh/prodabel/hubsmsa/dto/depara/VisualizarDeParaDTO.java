package br.gov.pbh.prodabel.hubsmsa.dto.depara;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

@JsonInclude(value = Include.NON_NULL)
public class VisualizarDeParaDTO extends BaseDTO {

  private static final long serialVersionUID = -6585439188930328973L;

  private Long id;
  private String nomeDePara;
  private String nomeTipoDePara;
  private String status;
  private VisualizarSistemaDeParaDTO sistemaPrimario;
  private VisualizarSistemaDeParaDTO sistemaSecundario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public String getNomeTipoDePara() {
    return nomeTipoDePara;
  }

  public void setNomeTipoDePara(String nomeTipoDePara) {
    this.nomeTipoDePara = nomeTipoDePara;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public VisualizarSistemaDeParaDTO getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(VisualizarSistemaDeParaDTO sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public VisualizarSistemaDeParaDTO getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(VisualizarSistemaDeParaDTO sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

}
