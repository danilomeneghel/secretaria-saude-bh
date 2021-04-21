package br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario;

import javax.validation.constraints.NotNull;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;


public class DeParaPrimarioSecundarioDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4330562439365325938L;

	private Long idDePara;
	
	private Long id;
	
	@NotNull(message = "O campo Código do De/Para é obrigatório(a).")
	private String codigo;
	
	@NotNull(message = "O campo Código do De/Para é obrigatório(a).")
	private String descricao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDePara() {
		return idDePara;
	}

	public void setIdDePara(Long idDePara) {
		this.idDePara = idDePara;
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
	
	

}
