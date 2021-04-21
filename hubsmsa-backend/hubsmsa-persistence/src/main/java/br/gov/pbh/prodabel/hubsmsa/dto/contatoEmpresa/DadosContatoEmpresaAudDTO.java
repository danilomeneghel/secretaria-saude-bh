package br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa;

import java.util.Date;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.dto.aud.AudGenericoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

/**
 * 
 * @author weverton.lucena@ctis.com.br
 * 
 * Classe responsável por transportar dados de ContatoEmpresaAud
 *
 */
public class DadosContatoEmpresaAudDTO extends AudGenericoDTO {

  private static final long serialVersionUID = 4368077387737964701L;
  
  private String email;
  private String nome;
  private String telefone;
  private String setor;
  private StatusEnum status;
  private String nomeEmpresa;
  private Date dtRevisao;
  private String matricula;

  /**
   * Instantiates a new dados contato empresa aud DTO.
   */
  public DadosContatoEmpresaAudDTO() {}

  /**
   * Instantiates a new dados contato empresa aud DTO.
   *
   * @param id the id
   * @param revisao the revisao
   * @param revType the rev type
   * @param email the email
   * @param nome the nome
   * @param telefone the telefone
   * @param setor the setor
   * @param status the status
   * @param nomeEmpresa the nome empresa
   * @param dtRevisao the dt revisao
   * @param matricula the matricula
   */
  public DadosContatoEmpresaAudDTO(Long id, Revision revisao, RevisionType revType, String email,
      String nome, String telefone, String setor, StatusEnum status, String nomeEmpresa,
      Date dtRevisao, String matricula) {
    this.setId(id);
    this.setRevisao(revisao);
    this.setRevType(revType);
    this.email = email;
    this.nome = nome;
    this.telefone = telefone;
    this.setor = setor;
    this.status = status;
    this.nomeEmpresa = nomeEmpresa;
    this.dtRevisao = dtRevisao;
    this.matricula = matricula;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getTelefone() {
    return telefone;
  }
  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
  public String getSetor() {
    return setor;
  }
  public void setSetor(String setor) {
    this.setor = setor;
  }
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }
  public String getNomeEmpresa() {
    return nomeEmpresa;
  }
  public void setNomeEmpresa(String nomeEmpresa) {
    this.nomeEmpresa = nomeEmpresa;
  }
  public Date getDtRevisao() {
    return dtRevisao;
  }
  public void setDtRevisao(Date dtRevisao) {
    this.dtRevisao = dtRevisao;
  }
  public String getMatricula() {
    return matricula;
  }
  public void setMatricula(String matricula) {
    this.matricula = matricula;
  }
  
  

}
