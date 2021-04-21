package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@Entity
@SequenceGenerator(sequenceName = "sq_tipo_de_para", name = "sq_tipo_de_para", allocationSize = 1)
@Table(name = "tipo_de_para")
@Audited
public class TipoDePara extends EntidadeBase<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1887922655988681791L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_tipo_de_para")
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
	
}
