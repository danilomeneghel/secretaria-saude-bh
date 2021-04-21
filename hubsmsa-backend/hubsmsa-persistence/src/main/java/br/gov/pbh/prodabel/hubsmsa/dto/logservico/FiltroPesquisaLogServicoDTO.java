package br.gov.pbh.prodabel.hubsmsa.dto.logservico;

import java.util.Date;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoLogServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import io.swagger.v3.oas.annotations.Parameter;


public class FiltroPesquisaLogServicoDTO extends BaseDTO {

  private static final long serialVersionUID = 4899127402580122361L;

  private static final String HORA_ZERO = " 00:00:00";
  private static final String HORA_23_59 = " 23:59:59";


	
  @Parameter(description = "Filtro id do serviço", required = false)
  @QueryParam("idServico")
  private Long idServico;
	
	@Parameter(description = "Filtro Data Inicial do Evento", required = false)
	@QueryParam("dataInicial")
	private String dataInicial;
	

	@Parameter(description = "Filtro Data Final do Evento", required = false)
	@QueryParam("dataFinal")
	private String dataFinal;

    @EnumValues(enumClass = StatusExecucao.class, ignoreCase = true, acceptNull = true,
        message = "Para o campo status deve ser informado os valores S(Sucesso),F(Falha).")
    @Parameter(description = "Filtro Status", required = false)
    @QueryParam("status")
    private List<String> status;

	@EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@DefaultValue("DESC")
	@QueryParam("ordem")
	private String tipoOrdenacao;
	
	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;
	
    @EnumValues(enumClass = ColunaOrdenacaoLogServicoEnum.class,
        message = "Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
    @DefaultValue("DATAINICIOEVENTO")
	private String orderBy;
	
	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
    @DefaultValue("5")
	private Integer itensPorPagina;
	
    public Long getIdServico() {
      return idServico;
	}

    public void setIdServico(Long idServico) {
      this.idServico = idServico;
	}
	
    public Date getDataInicial() {
      if (null != this.dataInicial) {
        return TimeUtil.toDateTime(TimeUtil.toFormatoDataPtBr(this.dataInicial + HORA_ZERO));
      }
      return null;
	}


	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

    public Date getDataFinal() {
      if (null != this.dataFinal) {
        return TimeUtil.toDateTime(TimeUtil.toFormatoDataPtBr(this.dataFinal + HORA_23_59));
      }
      return null;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
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

    public List<String> getStatus() {
      return status;
    }

    public void setStatus(List<String> status) {
      this.status = status;
    }

}
