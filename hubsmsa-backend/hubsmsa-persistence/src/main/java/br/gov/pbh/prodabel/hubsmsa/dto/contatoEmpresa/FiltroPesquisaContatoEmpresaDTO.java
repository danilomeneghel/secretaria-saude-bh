package br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa;

import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.util.json.notmasked.NotMasked;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroPesquisaContatoEmpresaDTO extends BaseDTO {
	private static final long serialVersionUID = -7679339407089309082L;

	@Parameter(description = "Filtro empresa do sistema", required = false)
	@QueryParam("idEmpresa")
	private Long idEmpresa;

	@Parameter(description = "Filtro nome do sistema", required = false)
	@QueryParam("nome")
	private String nome;

	@Parameter(description = "Filtro email do sistema", required = false)
	@QueryParam("email")
	private String email;

	@Parameter(description = "Filtro email do sistema", required = false)
	@QueryParam("telefone")
    @NotMasked
	private String telefone;

	@EnumValues(enumClass = StatusEnum.class, ignoreCase = true, acceptNull = true, message = "Para o campo status deve ser informado os valores A(Ativo),I(Inativo).")
	@Parameter(description = "Filtro Status", required = false)
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

	@EnumValues(enumClass = ColunaOrdenacaoContatoEmpresaEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("NOME")
	private String orderBy;

	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;


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
