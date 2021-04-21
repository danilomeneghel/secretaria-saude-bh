package br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class VisualizarContatoEmpresaDTO extends BaseDTO {

	private static final long serialVersionUID = -1300954050045777006L;

	private Long id;

	private VisualizarEmpresaDTO empresa;

	private String nome;

	private String email;

	private String telefone;

	private String setor;

	private StatusEnum status;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public VisualizarEmpresaDTO getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(final VisualizarEmpresaDTO empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(final String telefone) {
		this.telefone = telefone;
	}

	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(final StatusEnum status) {
		this.status = status;
	}

	public String getSetor() {
		return this.setor;
	}

	public void setSetor(final String setor) {
		this.setor = setor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
