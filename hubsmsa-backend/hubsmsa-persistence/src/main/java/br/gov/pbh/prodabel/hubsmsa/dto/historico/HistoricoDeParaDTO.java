package br.gov.pbh.prodabel.hubsmsa.dto.historico;

public class HistoricoDeParaDTO extends HistoricoGenericoDTO {

  private static final long serialVersionUID = 991992105045387284L;

  private Long idDePara;

  private String tipoDePara;

  private String nomeDePara;

  private String codigoSistemaPrimario;

  private String codigoSistemaSecundario;

  private String empresaPrimaria;

  private String empresaSecundaria;

  private String deParaPrimario;

  private String deParaSecundario;

  private String ativo;

  public Long getIdDePara() {
    return idDePara;
  }

  public void setIdDePara(Long idDePara) {
    this.idDePara = idDePara;
  }

  public String getTipoDePara() {
    return tipoDePara;
  }

  public void setTipoDePara(String tipoDePara) {
    this.tipoDePara = tipoDePara;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public String getCodigoSistemaPrimario() {
    return codigoSistemaPrimario;
  }

  public void setCodigoSistemaPrimario(String codigoSistemaPrimario) {
    this.codigoSistemaPrimario = codigoSistemaPrimario;
  }

  public String getCodigoSistemaSecundario() {
    return codigoSistemaSecundario;
  }

  public void setCodigoSistemaSecundario(String codigoSistemaSecundario) {
    this.codigoSistemaSecundario = codigoSistemaSecundario;
  }

  public String getEmpresaPrimaria() {
    return empresaPrimaria;
  }

  public void setEmpresaPrimaria(String empresaPrimaria) {
    this.empresaPrimaria = empresaPrimaria;
  }

  public String getEmpresaSecundaria() {
    return empresaSecundaria;
  }

  public void setEmpresaSecundaria(String empresaSecundaria) {
    this.empresaSecundaria = empresaSecundaria;
  }

  public String getDeParaPrimario() {
    return deParaPrimario;
  }

  public void setDeParaPrimario(String deParaPrimario) {
    this.deParaPrimario = deParaPrimario;
  }

  public String getDeParaSecundario() {
    return deParaSecundario;
  }

  public void setDeParaSecundario(String deParaSecundario) {
    this.deParaSecundario = deParaSecundario;
  }

  public String getAtivo() {
    return ativo;
  }

  public void setAtivo(String ativo) {
    this.ativo = ativo;
  }

}
