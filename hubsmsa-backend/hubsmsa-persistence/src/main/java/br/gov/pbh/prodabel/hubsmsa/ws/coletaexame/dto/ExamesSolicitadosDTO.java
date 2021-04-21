package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.io.Serializable;

public class ExamesSolicitadosDTO implements Serializable {

  private static final long serialVersionUID = -9075029911777625109L;

  private Long numeroExamePaciente;

  private Long codigoExamePaciente;

  private String descricaoExamePaciente;

  private Long codigoMaterial;

  private String descricaoMaterial;

  private String observacoesColeta;

  private Long codigoSigtapExame;

  private Long codigoProcedimentoLocal;

  private Long statusColeta;

  public Long getNumeroExamePaciente() {
    return numeroExamePaciente;
  }

  public void setNumeroExamePaciente(Long numeroExamePaciente) {
    this.numeroExamePaciente = numeroExamePaciente;
  }

  public Long getCodigoExamePaciente() {
    return codigoExamePaciente;
  }

  public void setCodigoExamePaciente(Long codigoExamePaciente) {
    this.codigoExamePaciente = codigoExamePaciente;
  }

  public String getDescricaoExamePaciente() {
    return descricaoExamePaciente;
  }

  public void setDescricaoExamePaciente(String descricaoExamePaciente) {
    this.descricaoExamePaciente = descricaoExamePaciente;
  }

  public Long getCodigoMaterial() {
    return codigoMaterial;
  }

  public void setCodigoMaterial(Long codigoMaterial) {
    this.codigoMaterial = codigoMaterial;
  }

  public String getDescricaoMaterial() {
    return descricaoMaterial;
  }

  public void setDescricaoMaterial(String descricaoMaterial) {
    this.descricaoMaterial = descricaoMaterial;
  }

  public String getObservacoesColeta() {
    return observacoesColeta;
  }

  public void setObservacoesColeta(String observacoesColeta) {
    this.observacoesColeta = observacoesColeta;
  }

  public Long getCodigoSigtapExame() {
    return codigoSigtapExame;
  }

  public void setCodigoSigtapExame(Long codigoSigtapExame) {
    this.codigoSigtapExame = codigoSigtapExame;
  }

  public Long getCodigoProcedimentoLocal() {
    return codigoProcedimentoLocal;
  }

  public void setCodigoProcedimentoLocal(Long codigoProcedimentoLocal) {
    this.codigoProcedimentoLocal = codigoProcedimentoLocal;
  }

  public Long getStatusColeta() {
    return statusColeta;
  }

  public void setStatusColeta(Long statusColeta) {
    this.statusColeta = statusColeta;
  }

}
