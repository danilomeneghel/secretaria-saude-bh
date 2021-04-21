package br.gov.pbh.prodabel.hubsmsa.dto.sistema;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

// TODO: Auto-generated Javadoc
public class DadosSistemaAudDTO implements Serializable {

  private static final long serialVersionUID = -4945630010929190715L;

  private Long id;
  Revision revisao;
  RevisionType revType;
  private String descricao;
  private FormaCadastroEnum formaCadastro;
  private String nome;
  private StatusEnum status;
  private String nomeEmpresa;
  private Date dtRevisao;
  private String matricula;

  /**
   * Instantiates a new dados tipode para aud DTO.
   */
  public DadosSistemaAudDTO() {}

  /**
   * Instantiates a new dados sistema aud DTO.
   *
   * @param id the id
   * @param revisao the revisao
   * @param revType the rev type
   * @param descricao the descricao
   * @param formaCadastro the forma cadastro
   * @param nome the nome
   * @param status the status
   * @param nomeEmpresa the nome fantasia
   * @param dtRevisao the dt revisao
   * @param matricula the matricula
   */
  public DadosSistemaAudDTO(Long id, Revision revisao, RevisionType revType, String descricao,
      FormaCadastroEnum formaCadastro, String nome, StatusEnum status, String nomeEmpresa,
      Date dtRevisao, String matricula) {
    super();
    this.id = id;
    this.revisao = revisao;
    this.revType = revType;
    this.descricao = descricao;
    this.formaCadastro = formaCadastro;
    this.nome = nome;
    this.status = status;
    this.nomeEmpresa = nomeEmpresa;
    this.dtRevisao = dtRevisao;
    this.matricula = matricula;
  }



  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Revision getRevisao() {
    return revisao;
  }
  public void setRevisao(Revision revisao) {
    this.revisao = revisao;
  }

  public RevisionType getRevType() {
    return revType;
  }

  public void setRevType(RevisionType rev) {
    this.revType = rev;
  }
  public String getDescricao() {
    return descricao;
  }
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  public FormaCadastroEnum getFormaCadastro() {
    return formaCadastro;
  }
  public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
    this.formaCadastro = formaCadastro;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
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
