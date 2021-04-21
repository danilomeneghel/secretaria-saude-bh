package br.gov.pbh.prodabel.hubsmsa.dto.depara;

import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.dto.aud.AudGenericoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

public class DeParaAudDTO extends AudGenericoDTO {

  private static final long serialVersionUID = 1L;

  private String nomeTipoDePara;

  private String nomeDePara;

  private StatusEnum status;

  private String nomeSistemaPrimario;

  private String nomeSistemaSecundario;

  private String nomeEmpresaPrimario;

  private String nomeEmpresaSecundario;

  /**
   * Instantiates a new de para aud DTO.
   */
  public DeParaAudDTO() {}

  /**
   * Instantiates a new de para aud DTO.
   *
   * @param nomeTipoDePara the nome tipo de para
   * @param nomeDePara the nome de para
   * @param status the status
   * @param nomeSistemaPrimario the nome sistema primario
   * @param nomeSistemaSecundario the nome sistema secundario
   * @param nomeEmpresaPrimario the nome empresa primario
   * @param nomeEmpresaSecundario the nome empresa secundario
   */
  public DeParaAudDTO(Long idDePara, Revision revisao, RevisionType revType, String nomeTipoDePara,
      String nomeDePara, StatusEnum status, String nomeSistemaPrimario,
      String nomeSistemaSecundario,
      String nomeEmpresaPrimario, String nomeEmpresaSecundario) {

    this.setId(idDePara);
    this.setRevisao(revisao);
    this.setRevType(revType);

    this.nomeTipoDePara = nomeTipoDePara;
    this.nomeDePara = nomeDePara;
    this.status = status;
    this.nomeSistemaPrimario = nomeSistemaPrimario;
    this.nomeSistemaSecundario = nomeSistemaSecundario;
    this.nomeEmpresaPrimario = nomeEmpresaPrimario;
    this.nomeEmpresaSecundario = nomeEmpresaSecundario;
  }

  public String getNomeTipoDePara() {
    return nomeTipoDePara;
  }

  public void setNomeTipoDePara(String nomeTipoDePara) {
    this.nomeTipoDePara = nomeTipoDePara;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getNomeSistemaPrimario() {
    return nomeSistemaPrimario;
  }

  public void setNomeSistemaPrimario(String nomeSistemaPrimario) {
    this.nomeSistemaPrimario = nomeSistemaPrimario;
  }

  public String getNomeSistemaSecundario() {
    return nomeSistemaSecundario;
  }

  public void setNomeSistemaSecundario(String nomeSistemaSecundario) {
    this.nomeSistemaSecundario = nomeSistemaSecundario;
  }

  public String getNomeEmpresaPrimario() {
    return nomeEmpresaPrimario;
  }

  public void setNomeEmpresaPrimario(String nomeEmpresaPrimario) {
    this.nomeEmpresaPrimario = nomeEmpresaPrimario;
  }

  public String getNomeEmpresaSecundario() {
    return nomeEmpresaSecundario;
  }

  public void setNomeEmpresaSecundario(String nomeEmpresaSecundario) {
    this.nomeEmpresaSecundario = nomeEmpresaSecundario;
  }

}
