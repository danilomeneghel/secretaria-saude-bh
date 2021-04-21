package br.gov.pbh.prodabel.hubsmsa.dto.sistema;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.util.LocalDateSerializer;

@JsonInclude(Include.NON_NULL)
public class VisualizarSistemaDTO extends BaseDTO {

	private static final long serialVersionUID = -1300954050045777006L;

	private Long id;

	private VisualizarEmpresaDTO empresa;

	private String nome;

	private String descricao;

	private StatusEnum status;

	private FormaCadastroEnum formaCadastro;

	private String nomeEmpresa;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataAtualizacao;

	public VisualizarSistemaDTO() {

	}

	public VisualizarSistemaDTO(Long id, VisualizarEmpresaDTO empresa, String nome, String descricao, StatusEnum status,
			FormaCadastroEnum formaCadastro, LocalDate dataAtualizacao) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.nome = nome;
		this.descricao = descricao;
		this.status = status;
		this.formaCadastro = formaCadastro;
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VisualizarEmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(VisualizarEmpresaDTO empresa) {
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public FormaCadastroEnum getFormaCadastro() {
		return formaCadastro;
	}

	public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
		this.formaCadastro = formaCadastro;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

}
