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
@SequenceGenerator(sequenceName = "sq_exemplo", name = "sq_exemplo", allocationSize = 1)
@Table(name = "exemplo")
@Audited
public class Exemplo extends EntidadeBase<Long> {

	private static final long serialVersionUID = -3004522243526821056L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_exemplo")
	@Column(name = "id_exemplo", nullable = false, unique = true)
	private Long id;

	@Column(name = "cod_exemplo", nullable = false, length = 4)
	private String codigo;

	@Column(name = "descricao_exemplo", nullable = false, length = 100)
	private String descricao;

	@Column(name = "ind_status", nullable = false)
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

}
