package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.io.Serializable;
import java.util.Date;

public class MedicoSolicitante implements Serializable {

  private static final long serialVersionUID = -2976164347978558524L;

  private Long numeroConselhoProfissional;

  private String numeroRegistroProfissional;

  private String ufRegistroConselho;

  private Date dataHoraSolicitacao;

  private Long cnesSolicitante;

  public Long getNumeroConselhoProfissional() {
    return numeroConselhoProfissional;
  }

  public void setNumeroConselhoProfissional(Long numeroConselhoProfissional) {
    this.numeroConselhoProfissional = numeroConselhoProfissional;
  }

  public String getNumeroRegistroProfissional() {
    return numeroRegistroProfissional;
  }

  public void setNumeroRegistroProfissional(String numeroRegistroProfissional) {
    this.numeroRegistroProfissional = numeroRegistroProfissional;
  }

  public String getUfRegistroConselho() {
    return ufRegistroConselho;
  }

  public void setUfRegistroConselho(String ufRegistroConselho) {
    this.ufRegistroConselho = ufRegistroConselho;
  }

  public Date getDataHoraSolicitacao() {
    return dataHoraSolicitacao;
  }

  public void setDataHoraSolicitacao(Date dataHoraSolicitacao) {
    this.dataHoraSolicitacao = dataHoraSolicitacao;
  }

  public Long getCnesSolicitante() {
    return cnesSolicitante;
  }

  public void setCnesSolicitante(Long cnesSolicitante) {
    this.cnesSolicitante = cnesSolicitante;
  }
}
