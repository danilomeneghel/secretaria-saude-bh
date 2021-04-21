package br.gov.pbh.prodabel.hubsmsa.dto.exemplo;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.MESSAGE;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EditarExemploDTO extends BaseDTO{

	private static final long serialVersionUID = 6023697860078473854L;
	
	@NotBlank(message = "Código é obrigatório(a).")
	@Size(max = 4, message= MESSAGE)	
	private String codigo;
	
	@NotBlank(message = "Nome do Exemplo é obrigatório(a).")
	@Size(min = 3, max = 100, message = "O campo Nome do Exemplo deve ter, no mínimo, {min} caracteres e no máximo {max} caracteres.")
	private String nomeExemplo;
	
	@EnumValues(enumClass = StatusEnum.class, message = "Para o campo status deve ser informado os valores A(Ativo) ou I(Inativo).", ignoreCase = true)
	private String status;

	public String getNomeExemplo() {
		return nomeExemplo;
	}

	public void setNomeExemplo(String nomeExemplo) {
		this.nomeExemplo = nomeExemplo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
