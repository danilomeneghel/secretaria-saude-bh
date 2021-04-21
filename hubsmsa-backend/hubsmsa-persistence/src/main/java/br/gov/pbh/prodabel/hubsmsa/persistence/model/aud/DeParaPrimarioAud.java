package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "de_para_primario_aud")
public class DeParaPrimarioAud extends EntidadeBase<Long> {
	
	

  /**
  * 
  */
  private static final long serialVersionUID = 1607257421330540790L;

  @Id
	@Column(name = "id_de_para_primario", nullable = false, unique = true)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_de_para", foreignKey = @ForeignKey(name = "fk_de_para_de_para_primario_aud"), nullable = false)
    private DePara dePara;
	
	@Column(name = "codigo", nullable = false)
    private String codigo;
	
	@Column(name = "descricao", nullable = false, length = 75)
	private String descricao;
	
	@NotAudited
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDate dataAtualizacao;

	@Column(name = "ind_forma_cadastro", nullable = false)
	@Enumerated(EnumType.STRING)
	private FormaCadastroEnum formaCadastro;
	
	@JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_de_para_primario_aud"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Revision revisao;
	
	@Column(name = "rev_type")
	@Enumerated(EnumType.ORDINAL)
	private RevisionType revType;

	@Override
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Gets the de para.
     *
     * @return the de para
     */
    public DePara getdePara() {
      return dePara;
	}

    public void setDePara(DePara dePara) {
      this.dePara = dePara;
	}

    public String getCodigo() {
		return codigo;
	}

    public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public FormaCadastroEnum getFormaCadastro() {
		return formaCadastro;
	}

	public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
		this.formaCadastro = formaCadastro;
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

	public void setRevType(RevisionType revType) {
		this.revType = revType;
	}

}
