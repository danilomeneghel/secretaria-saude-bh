package br.gov.pbh.prodabel.hubsmsa.dto.tipodepara;

import java.util.Date;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.dto.aud.AudGenericoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;

/**
 * 
 * @author weverton.lucena@ctis.com.br
 * 
 * Classe responsável por transportar dados do TipoDeParaAud
 */
public class DadosTipodeParaAudDTO extends AudGenericoDTO {

  private static final long serialVersionUID = -4945630010929190715L;

  private String descricao;
  private FormaCadastroEnum formaCadastro;
  private String nome;
  private StatusEnum status;
  private Date dtRevisao;
  private String matricula;

  /**
   * Instantiates a new dados tipode para aud DTO.
   */
  public DadosTipodeParaAudDTO() {}
    /**
     * Instantiates a new dados tipode para aud DTO.
     *
     * @param id the id
     * @param revisao the revisao
     * @param rev the rev
     * @param descricao the descricao
     * @param formaCadastro the forma cadastro
     * @param nome the nome
     * @param status the status
     * @param dtRevisao the dt revisao
     * @param matricula the matricula
     */
    public DadosTipodeParaAudDTO(Long id, Revision revisao, RevisionType rev, String descricao,
        FormaCadastroEnum formaCadastro, String nome, StatusEnum status, Date dtRevisao,
        String matricula) {
    this.setId(id);
    this.setRevisao(revisao);
    this.setRevType(rev);
      this.descricao = descricao;
      this.formaCadastro = formaCadastro;
      this.nome = nome;
      this.status = status;
      this.dtRevisao = dtRevisao;
      this.matricula = matricula;
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
