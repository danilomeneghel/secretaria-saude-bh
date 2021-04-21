package br.gov.pbh.prodabel.hubsmsa.dto.sistema;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.MESSAGE;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class SistemaDTO extends BaseDTO {

	private static final long serialVersionUID = 4947798441333645054L;

	@NotNull(message = "Empresa é obrigatório")
	private Long idEmpresa;

	@NotBlank(message = "Nome é obrigatório(a).")
	@Size(max = 100, message= MESSAGE)
	private String nome;

	@NotBlank(message = "Descrição é obrigatório(a).")
	@Size(max = 250, message= MESSAGE)
	private String descricao;

    @EnumValues(enumClass = StatusEnum.class,
        message = "Para o campo Sistema Ativo deve ser informado os valores Ativo ou Inativo.",
        ignoreCase = true)
	private String status;

	public Long getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(final Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

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
