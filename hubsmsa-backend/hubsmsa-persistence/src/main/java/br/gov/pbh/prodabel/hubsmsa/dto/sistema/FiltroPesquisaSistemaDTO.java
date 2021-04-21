package br.gov.pbh.prodabel.hubsmsa.dto.sistema;

import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoSistemaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroPesquisaSistemaDTO extends BaseDTO {
	
	private static final long serialVersionUID = -7679339407089309082L;
	
	@Parameter(description = "Filtro empresa do sistema", required = false)
	@QueryParam("idEmpresa")
	private Long idEmpresa;
	
	@Parameter(description = "Filtro nome do sistema", required = false)
	@QueryParam("nomeSistema")
	private String nome;
	
	@EnumValues(enumClass = StatusEnum.class, ignoreCase = true, acceptNull = true, message = "Para o campo status deve ser informado os valores A(Ativo),I(Inativo).")
	@Parameter(description = "Filtro Status", required = false)
	@QueryParam("status")
	private List<String> status;
	
	@EnumValues(enumClass=TipoOrdenacaoEnum.class, message="Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@DefaultValue("ASC")
	@QueryParam("ordem")
	private String tipoOrdenacao;
	
	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;
	
	@EnumValues(enumClass=ColunaOrdenacaoSistemaEnum.class, message="Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("NOME")
	private String orderBy;	
	
	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;
	
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
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
