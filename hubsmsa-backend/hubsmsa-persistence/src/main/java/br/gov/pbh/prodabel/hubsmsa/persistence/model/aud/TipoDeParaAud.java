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
import javax.persistence.Transient;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "tipo_de_para_aud")
public class TipoDeParaAud extends EntidadeBase<Long> {
 	
	private static final long serialVersionUID = -2156099050903608814L;

    @Id
	@Column(name = "id_tipo_de_para", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "descricao", nullable = false, length = 250)
	private String descricao;
	
	@Column(name = "ativo", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@NotAudited
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDate dataAtualizacao;

	@Column(name = "ind_forma_cadastro", nullable = false)
	@Enumerated(EnumType.STRING)
	private FormaCadastroEnum formaCadastro;
	
	@JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_tipo_de_para_aud"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Revision revisao;
	
	@Column(name = "rev_type")
	@Enumerated(EnumType.ORDINAL)
	private RevisionType revType;

    @Transient
    private String tipoRevisao;

	@Override
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

    public String getTipoRevisao() {
      return tipoRevisao;
    }

    public void setTipoRevisao(String tipoRevisao) {
      this.tipoRevisao = tipoRevisao;
    }
	
	
}
