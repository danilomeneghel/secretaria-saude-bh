package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

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
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

@Entity
@Table(name = "contato_empresa_aud")
public class ContatoEmpresaAud extends EntidadeBase<Long> {

	private static final long serialVersionUID = -157569861344668085L;

	@Id
	@Column(name = "id_contato_empresa", nullable = false, unique = true)
	private Long id;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Column(name = "telefone", nullable = false, length = 14)
	private String telefone;

	@Column(name = "setor", nullable = false, length = 50)
	private String setor;

	@Column(name = "ativo", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_sistema_empresa"), nullable = false)
	private Empresa empresa;
	
	@JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_contato_empresa_aud"), nullable = false)
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
