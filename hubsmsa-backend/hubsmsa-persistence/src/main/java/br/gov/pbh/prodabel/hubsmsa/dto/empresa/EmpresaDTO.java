package br.gov.pbh.prodabel.hubsmsa.dto.empresa;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.util.json.notmasked.NotMasked;

public class EmpresaDTO extends BaseDTO {

	private static final long serialVersionUID = -1219778436485294080L;

	@NotBlank(message = "O campo nome empresarial é obrigatório(a).")
	@Size(max = 100, message = "O campo Site deve ter, no máximo {max} caracteres.")
	private String nomeEmpresarial;

	@NotBlank(message = "O campo nome fantasia é obrigatório(a).")
	@Size(max = 100, message = "O campo Site deve ter, no máximo {max} caracteres.")
	private String nomeFantasia;

	@NotMasked
	private String cnpj;

//	@Size(min = 0, max = 10, message = "O campo CNES deve ter, no máximo {max} caracteres.")
	private Long cnes;

//	@Size(max = 100, message = "O campo Site deve ter, no máximo {max} caracteres.")
	private String site;

    @EnumValues(enumClass = StatusEnum.class,
        message = "Para o campo Empresa Ativa deve ser informado os valores Ativo ou Inativo.",
        ignoreCase = true)
	private String status;

	public String getNomeEmpresarial() {
		return this.nomeEmpresarial;
	}

	public void setNomeEmpresarial(final String nomeEmpresarial) {
		this.nomeEmpresarial = nomeEmpresarial;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(final String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(final String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCnes() {
		return this.cnes;
	}

	public void setCnes(final Long cnes) {
		this.cnes = cnes;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(final String site) {
		this.site = site;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}


}
