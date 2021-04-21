package br.gov.pbh.prodabel.hubsmsa.dto.alteracao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.AtributoTamanhoMinimoValido;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoObrigatorio;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoValido;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoAlteracaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

@PeriodoAlteracaoValido.List(value = {
		@PeriodoAlteracaoValido(dataInicial = "dataInicial", dataFinal = "dataFinal", message = "A data final deve ser maior ou igual à data inicial.") })
@PeriodoAlteracaoObrigatorio.List(value = {
		@PeriodoAlteracaoObrigatorio(dataInicial = "dataInicial", dataFinal = "dataFinal", message = "0(s) campo(s) Período de Alteração são de preenchimento obrigatório.") })
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroPesquisaAlteracaoDTO extends BaseDTO {

	private static final long serialVersionUID = -3082876057118591430L;

	private static final String HORA_INICIAL = "00:00:00";
	private static final String HORA_FINAL = "23:59:59";

	@Parameter(description = "Filtro Data Inicial", required = true)
	@QueryParam("dataInicial")
	private String dataInicial;

	@Parameter(description = "Filtro Data Final", required = true)
	@QueryParam("dataFinal")
	private String dataFinal;

	@Parameter(description = "Filtro Assunto", required = true)
	@QueryParam("assunto")
	@EnumValues(enumClass = AssuntoEnum.class, ignoreCase = true, acceptNull = false, message = "Selecione um assunto para pesquisa de alteração de cadastros.")
	private String assunto;

	@Parameter(description = "Filtro Usuário Responsável pela Alteração", required = false)
	@QueryParam("usuarioResponsavel")
	private String usuarioResponsavel;

	@Parameter(description = "Filtro Código", required = false)
	@Size(min = 1, max = 100, message = "Campo deve ter, no mínimo, {min} caracteres e no máximo {max} caracteres.")
	@QueryParam("codigo")
	private String codigo;

	@AtributoTamanhoMinimoValido
	@Parameter(description = "Filtro Descrição", required = false)
	@QueryParam("descricao")
	private String descricao;

	@Parameter(description = "Tipo de Ordenação", required = false)
	@QueryParam("ordem")
	@EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
	@DefaultValue("DESC")
	private String tipoOrdenacao;

	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;

	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@EnumValues(enumClass = ColunaOrdenacaoAlteracaoEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
	@DefaultValue("DATAALTERACAO")
	private String orderBy;

	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;

	/**
	 * @return the dataInicial
	 */
	public LocalDateTime getDataInicial() {
		if (!this.dataInicial.isEmpty()) {
			StringBuilder dataHora = new StringBuilder(this.dataInicial).append(" ").append(HORA_INICIAL);
			return StringUtils.isNotBlank(this.dataInicial)
					? LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
					: null;
		} else {
			return null;
		}
	}

	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * @return the dataFinal
	 */
	public LocalDateTime getDataFinal() {
		if (!this.dataFinal.isEmpty()) {
			StringBuilder dataHora = new StringBuilder(this.dataFinal).append(" ").append(HORA_FINAL);
			return StringUtils.isNotBlank(this.dataFinal)
					? LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
					: null;
		} else {
			return null;
		}

	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
