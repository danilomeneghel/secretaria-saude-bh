package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

@JsonInclude(Include.NON_NULL)
public class HistoricoEmpresaDTO implements Serializable {

  private static final long serialVersionUID = 1753359742272261454L;

  private Long id;
  private String dataEvento;
	private String nomeUsuario;
	private String email;
	private String login;
	private String revisao;
	private String nomeEmpresarial;
	private String nomeFantasia;
	private String cnpj;
    private Long cnes;
	private String status;
	private String site;
    private Revision revision;
    private String tipoRevisao;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

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
  public String getNomeEmpresarial() {
    return nomeEmpresarial;
  }
  public void setNomeEmpresarial(String nomeEmpresarial) {
    this.nomeEmpresarial = nomeEmpresarial;
  }
  public String getNomeFantasia() {
    return nomeFantasia;
  }
  public void setNomeFantasia(String nomeFantasia) {
    this.nomeFantasia = nomeFantasia;
  }
  public String getCnpj() {
    return cnpj;
  }
  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public Long getCnes() {
    return cnes;
  }

  public void setCnes(Long cnes) {
    this.cnes = cnes;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String ativo) {

    this.status = ativo;
  }
  public String getSite() {
    return site;
  }
  public void setSite(String site) {
    this.site = site;
  }

  public String getTipoRevisao() {
    return tipoRevisao;
  }

  public void setTipoRevisao(String tipoRevisao) {
    this.tipoRevisao = tipoRevisao;
  }

}
