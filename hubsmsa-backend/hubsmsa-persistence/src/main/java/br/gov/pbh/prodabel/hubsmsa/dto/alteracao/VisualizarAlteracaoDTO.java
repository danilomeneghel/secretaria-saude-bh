package br.gov.pbh.prodabel.hubsmsa.dto.alteracao;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.util.DateSerializer;

public class VisualizarAlteracaoDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7575465743427819093L;

	private Long id;

	private AssuntoEnum assunto;

	private String descricao;

	@JsonSerialize(using = DateSerializer.class)
	private Date dataAlteracao;

	public VisualizarAlteracaoDTO() {
	}

	public VisualizarAlteracaoDTO(Long id, AssuntoEnum assunto, String descricao, Date dataAlteracao) {
		this.id = id;
		this.assunto = assunto;
		this.descricao = descricao;
		this.dataAlteracao = dataAlteracao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssuntoEnum getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoEnum assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}
