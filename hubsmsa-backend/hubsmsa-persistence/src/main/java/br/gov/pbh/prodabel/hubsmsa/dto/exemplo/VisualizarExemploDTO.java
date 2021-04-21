package br.gov.pbh.prodabel.hubsmsa.dto.exemplo;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.util.LocalDateSerializer;

public class VisualizarExemploDTO extends BaseDTO {

	private static final long serialVersionUID = 6562450424844788816L;

	private Long id;

	private String codigo;

	private String nomeExemplo;

	private FormaCadastroEnum formaCadastro;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dataAtualizacao;

	private StatusEnum status;
	
	public VisualizarExemploDTO() {
		
	}
	
	public VisualizarExemploDTO(Long id, String codigo, String nomeExemplo, FormaCadastroEnum formaCadastro,
			LocalDate dataAtualizacao, StatusEnum status) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.setNomeExemplo(nomeExemplo);
		this.formaCadastro = formaCadastro;
		this.dataAtualizacao = dataAtualizacao;
		this.status = status;
	}

	public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
		this.formaCadastro = formaCadastro;
	}


	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	public Long getId() {
		return id;
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

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public FormaCadastroEnum getFormaCadastro() {
		return formaCadastro;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public String getNomeExemplo() {
		return nomeExemplo;
	}

	public void setNomeExemplo(String nomeExemplo) {
		this.nomeExemplo = nomeExemplo;
	}


}
