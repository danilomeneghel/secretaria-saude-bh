package br.gov.pbh.prodabel.hubsmsa.dto.exemplo;

import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.annotations.ExemploValido;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoExemploEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

@ExemploValido(message= "Pelo menos um campo deve ser informado para pesquisa.")
public class FiltroPesquisaExemploDTO extends BaseDTO{

	private static final long serialVersionUID = -8243203881052556573L;
	
	@Parameter(description = "Filtro Codigo do Exemplo", required = false)
	@QueryParam("codigo")
	private String codigo;
	
	@Size(min=3, message="É necessária a informação de pelo menos 3 caracteres para pesquisa pelo nome ou descrição do campo.")
	@Parameter(description = "Filtro Nome do Exemplo", required = false)
	@QueryParam("nomeExemplo")
	private String nomeExemplo;
	
	@EnumValues(enumClass = StatusEnum.class, ignoreCase = true, acceptNull = true, message = "Para o campo status deve ser informado os valores A(Ativo),I(Inativo).")
	@Parameter(description = "Filtro Status", required = false)
	@QueryParam("status")
	private String status;
	
	@EnumValues(enumClass=TipoOrdenacaoEnum.class, message="Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@DefaultValue("ASC")
	@QueryParam("ordem")
	private String tipoOrdenacao;
	
	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;
	
	@EnumValues(enumClass=ColunaOrdenacaoExemploEnum.class, message="Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("NOMEEXEMPLO")
	private String orderBy;	
	
	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

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

	public String getTipoOrdenacao() {
		return tipoOrdenacao;
	}

	public void setTipoOrdenacao(String tipoOrdenacao) {
		this.tipoOrdenacao = tipoOrdenacao;
	}

	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getItensPorPagina() {
		return itensPorPagina;
	}

	public void setItensPorPagina(Integer itensPorPagina) {
		this.itensPorPagina = itensPorPagina;
	}
	
	

}
