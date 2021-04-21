package br.gov.pbh.prodabel.hubsmsa.dto.log.aplicacao;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.annotations.FormatoDataValido;
import br.gov.pbh.prodabel.hubsmsa.annotations.FormatoHoraValido;
import br.gov.pbh.prodabel.hubsmsa.annotations.HoraPeriodo;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoValido;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoLogAplicacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

@PeriodoAlteracaoValido.List(value = {
		@PeriodoAlteracaoValido(
			dataInicial = "dataInicial",
			dataFinal = "dataFinal",
			message = "A data final deve ser maior ou igual à data inicial.")}
)
@HoraPeriodo.List(value = {
		@HoraPeriodo(
				horaInicio = "horaInicial",
				horaFim = "horaFinal",
				message="A hora final deve ser maior que a hora inicial."
				)
})
public class FiltroPesquisaLogAplicacaoDTO extends BaseDTO {

	private static final long serialVersionUID = 8697945405488633585L;

	@Size(min=3, message="É necessária a informação de pelo menos {min} caracteres para pesquisa pelo nome ou descrição do campo.")
	@Parameter(description = "Filtro Nome do Sistema e Cliente", required = false)
	@QueryParam("nomeSistemaCliente")
	private String nomeSistemaCliente;


	@Size(min=3, message="É necessária a informação de pelo menos {min} caracteres para pesquisa pelo nome ou descrição do campo.")
	@Parameter(description = "Filtro Mensagem", required = false)
	@QueryParam("assuntoMensagem")
	private String assuntoMensagem;


	@Size(min=3, message="É necessária a informação de pelo menos {min} caracteres para pesquisa pelo nome ou descrição do campo.")
	@Parameter(description = "Filtro Nome do Usuario do Sistema", required = false)
	@QueryParam("nomeUsuarioSistema")
	private String nomeUsuarioSistema;

	@FormatoDataValido(message="A data inicial informada é inválida.")
	@Parameter(description = "Filtro Data Inicial", required = true)
	@NotNull(message = "Data Inicial é obrigatório.")
	@QueryParam("dataInicial")
	private String dataInicial;

	@FormatoDataValido(message="A data final informada é inválida.")
	@Parameter(description = "Filtro Data Final", required = true)
	@NotNull(message = "Data final é obrigatório.")
	@QueryParam("dataFinal")
	private String dataFinal;

	@Parameter(description = "Filtro Hora Inicial", required = false)
	@QueryParam("horaInicial")
	@FormatoHoraValido
	@DefaultValue("00:00:00")
	private String horaInicial;

	@Parameter(description = "Filtro Hora Final", required = false)
	@QueryParam("horaFinal")
	@FormatoHoraValido
	@DefaultValue("23:59:59")
	private String horaFinal;

	@EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@QueryParam("ordem")
	@DefaultValue("ASC")
	private String tipoOrdenacao;

	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;

	@EnumValues(enumClass = ColunaOrdenacaoLogAplicacaoEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("DATAHORA")
	private String orderBy;

	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;
	
	private LocalDateTime diaHoraInicio;
	
	private LocalDateTime diaHoraFinal;
	
	public LocalDateTime getDiaHoraInicio() {
		return diaHoraInicio;
	}

	public void setDiaHoraInicio(LocalDateTime diaHoraInicio) {
		this.diaHoraInicio = diaHoraInicio;
	}

	public LocalDateTime getDiaHoraFinal() {
		return diaHoraFinal;
	}

	public void setDiaHoraFinal(LocalDateTime diaHoraFinal) {
		this.diaHoraFinal = diaHoraFinal;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;

	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public String getNomeSistemaCliente() {
		return nomeSistemaCliente;
	}

	public void setNomeSistemaCliente(String nomeSistemaCliente) {
		this.nomeSistemaCliente = nomeSistemaCliente;
	}

	public String getAssuntoMensagem() {
		return assuntoMensagem;
	}

	public void setAssuntoMensagem(String assuntoMensagem) {
		this.assuntoMensagem = assuntoMensagem;
	}

	public String getNomeUsuarioSistema() {
		return nomeUsuarioSistema;
	}

	public void setNomeUsuarioSistema(String nomeUsuarioSistema) {
		this.nomeUsuarioSistema = nomeUsuarioSistema;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
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
