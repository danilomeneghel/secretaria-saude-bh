package br.gov.pbh.prodabel.hubsmsa.dto;

import java.io.Serializable;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoRevisaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroRevisaoDTO implements Serializable {

	private static final long serialVersionUID = -2929325912028046212L;
	
	@Parameter(description = "Tipo de Ordenação", required = false)
	@QueryParam("ordem")
	@EnumValues(enumClass=TipoOrdenacaoEnum.class, message="Tipo de Ordenação inválida.", ignoreCase = true)
	@DefaultValue("DESC")
	private String tipoOrdenacao;
	
	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;
	
	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("10")
	private Integer itensPorPagina;

	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")	
	@EnumValues(enumClass=ColunaOrdenacaoRevisaoEnum.class, message="Coluna para ordenação inválida.", ignoreCase = true)
	@DefaultValue("REVISAO")
	private String orderBy;
	
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

	public Integer getItensPorPagina() {
		return itensPorPagina;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setItensPorPagina(Integer itensPorPagina) {
		this.itensPorPagina = itensPorPagina;
	}

}
