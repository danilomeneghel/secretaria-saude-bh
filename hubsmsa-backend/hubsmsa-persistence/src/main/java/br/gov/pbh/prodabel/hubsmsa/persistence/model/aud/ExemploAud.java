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
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "exemplo_aud")
public class ExemploAud extends EntidadeBase<Long> {

	private static final long serialVersionUID = -3004522243526821056L;

	@Id	
	@Column(name = "id_exemplo", nullable = false, unique = true)
	private Long id;

	@Column(name = "cod_exemplo", nullable = true, length = 4)
	private String codigo;

	@Column(name = "descricao_exemplo", nullable = true, length = 100)
	private String descricao;

	@Column(name = "ind_status", nullable = true)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@Column(name = "data_atualizacao", nullable = true)
	private LocalDate dataAtualizacao;

	@Column(name = "ind_forma_cadastro", nullable = true)
	@Enumerated(EnumType.STRING)
	private FormaCadastroEnum formaCadastro;
	
	@JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_exemplo_aud"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Revision revisao;
	
	@Column(name = "rev_type")
	@Enumerated(EnumType.ORDINAL)
	private RevisionType revType;
	
	@Override
  public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
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
	@Override
  public String toString() {
		return this.codigo + " - " + this.descricao;
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
