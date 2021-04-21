package br.gov.pbh.prodabel.hubsmsa.dto.tipodepara;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.MESSAGE;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class TipoDeParaDTO extends BaseDTO {

	private static final long serialVersionUID = -2011596751131720892L;

	@NotBlank(message = "Nome é obrigatório(a).")
	@Size(max = 100, message= MESSAGE)
	private String nome;

	@NotBlank(message = "Descricao é obrigatório(a).")
	@Size(max = 250, message= MESSAGE)
	private String descricao;

    @EnumValues(enumClass = StatusEnum.class,
        message = "Para o campo Ativo deve ser informado os valores Ativo ou Inativo",
        ignoreCase = true)
	private String status;

	public String getNome() {
		return this.nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}



}
