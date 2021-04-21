package br.gov.pbh.prodabel.hubsmsa.ws.dto;

import java.io.Serializable;

public class MedicoDTO implements Serializable {

  private static final long serialVersionUID = 7295887269528743608L;

  private String nomeMedico;

  private String cpfMedico;

  private String tipoSolicitante;

  public String getNomeMedico() {
    return nomeMedico;
  }

  public void setNomeMedico(String nomeMedico) {
    this.nomeMedico = nomeMedico;
  }

  public String getCpfMedico() {
    return cpfMedico;
  }

  public void setCpfMedico(String cpfMedico) {
    this.cpfMedico = cpfMedico;
  }

  public String getTipoSolicitante() {
    return tipoSolicitante;
  }

  public void setTipoSolicitante(String tipoSolicitante) {
    this.tipoSolicitante = tipoSolicitante;
  }

}
