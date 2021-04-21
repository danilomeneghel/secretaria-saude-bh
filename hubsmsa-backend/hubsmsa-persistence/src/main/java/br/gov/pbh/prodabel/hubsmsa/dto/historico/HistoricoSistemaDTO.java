package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

@JsonInclude(Include.NON_NULL)
public class HistoricoSistemaDTO implements Serializable {

  private static final long serialVersionUID = 1753359742272261454L;

  private Long id;
  private String dataEvento;
	private String nomeUsuario;
	private String email;
	private String login;
	private String revisao;
    private String nome;
    private String status;
    private Revision revision;
    private String tipoRevisao;
    private String nomeEmpresa;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    private String descricao;

    public Revision getRevision() {
      return revision;
    }

    public void setRevision(Revision revision) {
      this.revision = revision;
    }
  public String getDataEvento() {
    return dataEvento;
  }
  public void setDataEvento(String dataEvento) {
    this.dataEvento = dataEvento;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String usuario) {
    this.nomeUsuario = usuario;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }
  public String getRevisao() {
    return revisao;
  }
  public void setRevisao(String revisao) {
    this.revisao = revisao;
  }



  public String getNome() {
    return nome;
  }

  public void setNome(String tipoDePara) {
    this.nome = tipoDePara;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricaoTipo) {
    this.descricao = descricaoTipo;
  }

  public String getTipoRevisao() {
    return tipoRevisao;
  }

  public void setTipoRevisao(String tipoRevisao) {
    this.tipoRevisao = tipoRevisao;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNomeEmpresa() {
    return nomeEmpresa;
  }

  public void setNomeEmpresa(String nomeEmpresa) {
    this.nomeEmpresa = nomeEmpresa;
  }

}
