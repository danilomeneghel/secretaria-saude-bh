package br.gov.pbh.prodabel.hubsmsa.dto.empresa;

import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import org.hibernate.validator.constraints.br.CNPJ;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoEmpresaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;


public class FiltroPesquisaEmpresaDTO extends BaseDTO {

	private static final long serialVersionUID = -8243203881052556573L;

	@Parameter(description = "Filtro do nome empresarial", required = false)
	@QueryParam("nomeEmpresarial")
	private String nomeEmpresarial;

	@Parameter(description = "Filtro Nome Fantasia", required = false)
	@QueryParam("nomeFantasia")
	private String nomeFantasia;

	@CNPJ
	@Parameter(description = "Filtro CNPJ", required = false)
	@QueryParam("cnpj")
	private String cnpj;

	@Parameter(description = "Filtro CNES", required = false)
	@QueryParam("cnes")
	private Integer cnes;

	@EnumValues(enumClass = StatusEnum.class, ignoreCase = true, acceptNull = true, message = "Para o campo status deve ser informado os valores A(Ativo),I(Inativo).")
	@Parameter(description = "Filtro Ativo", required = false)
	@QueryParam("status")
    private List<String> status;

	@EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@DefaultValue("ASC")
	@QueryParam("ordem")
	private String tipoOrdenacao;

	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;

	@EnumValues(enumClass = ColunaOrdenacaoEmpresaEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("NOMEFANTASIA")
	private String orderBy;

	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;

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

	public Integer getCnes() {
		return this.cnes;
	}

	public void setCnes(final Integer cnes) {
		this.cnes = cnes;
	}

    public List<String> getStatus() {
		return this.status;
	}

    public void setStatus(final List<String> status) {
		this.status = status;
	}

	public String getTipoOrdenacao() {
		return this.tipoOrdenacao;
	}

	public void setTipoOrdenacao(final String tipoOrdenacao) {
		this.tipoOrdenacao = tipoOrdenacao;
	}

	public Integer getNumeroPagina() {
		return this.numeroPagina;
	}

	public void setNumeroPagina(final Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getItensPorPagina() {
		return this.itensPorPagina;
	}

	public void setItensPorPagina(final Integer itensPorPagina) {
		this.itensPorPagina = itensPorPagina;
	}

}
