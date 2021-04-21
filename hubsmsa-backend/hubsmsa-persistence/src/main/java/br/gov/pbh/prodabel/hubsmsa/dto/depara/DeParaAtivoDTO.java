package br.gov.pbh.prodabel.hubsmsa.dto.depara;

import java.io.Serializable;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

// TODO: Auto-generated Javadoc
public class DeParaAtivoDTO implements Serializable {

  private static final long serialVersionUID = -5278454808498240748L;

  private String nomeDePara;

  private StatusEnum statusDePara;

  private String tipoDepara;

  private StatusEnum statusTipoDePara;

  private String sistemaPrimario;

  private StatusEnum statusSistemaPrimario;

  private String sistemaSecundario;

  private StatusEnum statusSistemaSecundario;

  private String empresaSistemaPrimario;

  private StatusEnum statusEmpresaSistemaPrimario;

  private String empresaSistemaSecundario;

  private StatusEnum statusEmpresaSistemaSecundario;

  /**
   * Instantiates a new de para ativo DTO.
   *
   * @param nomeDepara the nome depara
   * @param statusDePara the status de para
   * @param tipoDepara the tipo depara
   * @param statusTipoDePara the status tipo de para
   * @param sistemaPrimario the sistema primario
   * @param statusSistemaPrimario the status sistema primario
   * @param sistemaSecundario the sistema secundario
   * @param statusSistemaSecundario the status sistema secundario
   * @param empresaSistemaPrimario the empresa sistema primario
   * @param statusEmpresaSistemaPrimario the status empresa sistema primario
   * @param empresaSistemaSecundario the empresa sistema secundario
   * @param statusEmpresaSistemaSecundario the status empresa sistema secundario
   */
  public DeParaAtivoDTO(String nomeDepara, StatusEnum statusDePara, String tipoDepara,
      StatusEnum statusTipoDePara, String sistemaPrimario, StatusEnum statusSistemaPrimario,
      String sistemaSecundario, StatusEnum statusSistemaSecundario, String empresaSistemaPrimario,
      StatusEnum statusEmpresaSistemaPrimario, String empresaSistemaSecundario,
      StatusEnum statusEmpresaSistemaSecundario) {
    super();
    this.nomeDePara = nomeDepara;
    this.statusDePara = statusDePara;
    this.tipoDepara = tipoDepara;
    this.statusTipoDePara = statusTipoDePara;
    this.sistemaPrimario = sistemaPrimario;
    this.statusSistemaPrimario = statusSistemaPrimario;
    this.sistemaSecundario = sistemaSecundario;
    this.statusSistemaSecundario = statusSistemaSecundario;
    this.empresaSistemaPrimario = empresaSistemaPrimario;
    this.statusEmpresaSistemaPrimario = statusEmpresaSistemaPrimario;
    this.empresaSistemaSecundario = empresaSistemaSecundario;
    this.statusEmpresaSistemaSecundario = statusEmpresaSistemaSecundario;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDepara) {
    this.nomeDePara = nomeDepara;
  }

  public StatusEnum getStatusDePara() {
    return statusDePara;
  }

  public void setStatusDePara(StatusEnum statusDePara) {
    this.statusDePara = statusDePara;
  }

  public String getTipoDepara() {
    return tipoDepara;
  }

  public void setTipoDepara(String tipoDepara) {
    this.tipoDepara = tipoDepara;
  }

  public StatusEnum getStatusTipoDePara() {
    return statusTipoDePara;
  }

  public void setStatusTipoDePara(StatusEnum statusTipoDePara) {
    this.statusTipoDePara = statusTipoDePara;
  }

  public String getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(String sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public StatusEnum getStatusSistemaPrimario() {
    return statusSistemaPrimario;
  }

  public void setStatusSistemaPrimario(StatusEnum statusSistemaPrimario) {
    this.statusSistemaPrimario = statusSistemaPrimario;
  }

  public String getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(String sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public StatusEnum getStatusSistemaSecundario() {
    return statusSistemaSecundario;
  }

  public void setStatusSistemaSecundario(StatusEnum statusSistemaSecundario) {
    this.statusSistemaSecundario = statusSistemaSecundario;
  }

  public String getEmpresaSistemaPrimario() {
    return empresaSistemaPrimario;
  }

  public void setEmpresaSistemaPrimario(String empresaSistemaPrimario) {
    this.empresaSistemaPrimario = empresaSistemaPrimario;
  }

  public StatusEnum getStatusEmpresaSistemaPrimario() {
    return statusEmpresaSistemaPrimario;
  }

  public void setStatusEmpresaSistemaPrimario(StatusEnum statusEmpresaSistemaPrimario) {
    this.statusEmpresaSistemaPrimario = statusEmpresaSistemaPrimario;
  }

  public String getEmpresaSistemaSecundario() {
    return empresaSistemaSecundario;
  }

  public void setEmpresaSistemaSecundario(String empresaSistemaSecundario) {
    this.empresaSistemaSecundario = empresaSistemaSecundario;
  }

  public StatusEnum getStatusEmpresaSistemaSecundario() {
    return statusEmpresaSistemaSecundario;
  }

  public void setStatusEmpresaSistemaSecundario(StatusEnum statusEmpresaSistemaSecundario) {
    this.statusEmpresaSistemaSecundario = statusEmpresaSistemaSecundario;
  }
  
  @Override
  public String toString() {
    return "DeParaAtivoDTO [nomeDepara=" + nomeDePara + ", statusDePara=" + statusDePara
        + ", tipoDepara=" + tipoDepara + ", statusTipoDePara=" + statusTipoDePara
        + ", sistemaPrimario=" + sistemaPrimario + ", statusSistemaPrimario="
        + statusSistemaPrimario + ", sistemaSecundario=" + sistemaSecundario
        + ", statusSistemaSecundario=" + statusSistemaSecundario + ", empresaSistemaPrimario="
        + empresaSistemaPrimario + ", statusEmpresaSistemaPrimario=" + statusEmpresaSistemaPrimario
        + ", empresaSistemaSecundario=" + empresaSistemaSecundario
        + ", statusEmpresaSistemaSecundario=" + statusEmpresaSistemaSecundario + "]";
  }

  /**
   * Ativo.
   *
   * @return true, if successful
   */
  public boolean ativo() {
    return statusDePara == StatusEnum.A && statusTipoDePara == StatusEnum.A
        && statusSistemaPrimario == StatusEnum.A && statusSistemaSecundario == StatusEnum.A
        && statusEmpresaSistemaPrimario == StatusEnum.A
        && statusEmpresaSistemaSecundario == StatusEnum.A;
  }
  
  public String getInativo() {
    if (statusDePara == StatusEnum.I)
      return "De/Para";

    if (statusTipoDePara == StatusEnum.I)
      return "Tipo de De/Para";

    if (statusSistemaPrimario == StatusEnum.I)
      return "Sistema";

    if (statusSistemaSecundario == StatusEnum.I)
      return "Sistema";

    if (statusEmpresaSistemaPrimario == StatusEnum.I)
      return "Empresa";

    if (statusEmpresaSistemaSecundario == StatusEnum.I)
      return "Empresa";

    return nomeDePara;

  }

}
