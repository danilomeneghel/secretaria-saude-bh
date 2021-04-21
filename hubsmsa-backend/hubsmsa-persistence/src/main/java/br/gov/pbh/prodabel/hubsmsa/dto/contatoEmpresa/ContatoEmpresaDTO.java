package br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.MESSAGE;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.util.json.notmasked.NotMasked;

public class ContatoEmpresaDTO extends BaseDTO {
	private static final long serialVersionUID = 4947798441333645054L;

	@NotNull(message = "Empresa é obrigatório")
	private Long idEmpresa;

	@NotBlank(message = "Nome é obrigatório(a).")
	@Size(max = 100, message = MESSAGE)
	private String nome;

	@NotBlank(message = "E-mail é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
	@Email
	private String email;

	@NotBlank(message = "Telefone é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
    @NotMasked
	private String telefone;

	@NotBlank(message = "Setor é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
	private String setor;

    @EnumValues(enumClass = StatusEnum.class,
        message = "Para o campo Contato Ativo deve ser informado os valores Ativo ou Inativo.",
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

	public String getSetor() {
		return this.setor;
	}

	public void setSetor(final String setor) {
		this.setor = setor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
