package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.envers.Audited;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@Audited
@Table(name = "contato_empresa")
@SequenceGenerator(sequenceName = "sq_contato_empresa", name = "sq_contato_empresa", allocationSize = 1)
public class ContatoEmpresa extends EntidadeBase<Long> {

	private static final long serialVersionUID = -157569861344668085L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_contato_empresa")
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
    @JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_contato_empresa"),
        nullable = false)
	private Empresa empresa;

    @Transient
    private String nomeEmpresa;

    public String getNomeEmpresa() {
      return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
      this.nomeEmpresa = nomeEmpresa;
    }

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
