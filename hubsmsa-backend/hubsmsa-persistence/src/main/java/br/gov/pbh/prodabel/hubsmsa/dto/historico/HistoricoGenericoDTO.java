package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;

public class HistoricoGenericoDTO implements Serializable {

  private static final long serialVersionUID = -6555144505933486799L;

  private String nomeUsuario;

  private String emailUsuario;

  private String login;

  private Long idRevisao;

  private String tipoRevisao;

  private String dataEvento;

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getEmailUsuario() {
    return emailUsuario;
  }

  public void setEmailUsuario(String emailUsuario) {
    this.emailUsuario = emailUsuario;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public Long getIdRevisao() {
    return idRevisao;
  }

  public void setIdRevisao(Long idRevisao) {
    this.idRevisao = idRevisao;
  }

  public String getTipoRevisao() {
    return tipoRevisao;
  }

  public void setTipoRevisao(String tipoRevisao) {
    this.tipoRevisao = tipoRevisao;
  }

  public String getDataEvento() {
    return dataEvento;
  }

  public void setDataEvento(String dataEvento) {
    this.dataEvento = dataEvento;
  }

}
