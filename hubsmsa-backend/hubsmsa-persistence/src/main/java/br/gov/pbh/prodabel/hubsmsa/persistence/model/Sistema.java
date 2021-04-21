package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.time.LocalDate;
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
import org.hibernate.envers.NotAudited;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@SequenceGenerator(sequenceName = "sq_sistema", name = "sq_sistema", allocationSize = 1)
@Table(name = "sistema")
@Audited
public class Sistema extends EntidadeBase<Long> {

	private static final long serialVersionUID = -157569861344668085L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_sistema")
	@Column(name = "id_sistema", nullable = false, unique = true)
	private Long id;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "descricao", nullable = false, length = 250)
	private String descricao;

	@Column(name = "ativo", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusEnum ativo;

	@NotAudited
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDate dataAtualizacao;

	@Column(name = "ind_forma_cadastro", nullable = false)
	@Enumerated(EnumType.STRING)
	private FormaCadastroEnum formaCadastro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa", foreignKey = @ForeignKey(name = "fk_sistema_empresa"), nullable = false)
	private Empresa empresa;

    @Transient
    @Column(name = "nome_empresa")
    private String nomeEmpresa;

	@Override
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	public StatusEnum getAtivo() {
		return ativo;
	}

	public void setAtivo(StatusEnum ativo) {
		this.ativo = ativo;
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

    public String getNomeEmpresa() {
      return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
      this.nomeEmpresa = nomeEmpresa;
    }

}
