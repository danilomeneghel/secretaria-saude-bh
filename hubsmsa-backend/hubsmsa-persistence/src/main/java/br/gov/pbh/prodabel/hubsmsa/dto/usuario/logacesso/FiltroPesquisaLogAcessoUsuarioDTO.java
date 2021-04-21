package br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso;

import java.util.Date;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoLogAcessoUsuarioEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import io.swagger.v3.oas.annotations.Parameter;


public class FiltroPesquisaLogAcessoUsuarioDTO extends BaseDTO {

  private static final String HORA_ZERO = " 00:00:00";
  private static final String HORA_23_59 = " 23:59:59";

	private static final long serialVersionUID = 5783486887245229624L;

	
	@Parameter(description = "Filtro usuario do sistema", required = false)
	@QueryParam("idUsuario")
	private Long idUsuario;
	
	@Parameter(description = "Filtro Data Inicial do Evento", required = false)
	@QueryParam("dataInicial")
	private String dataInicial;
	

	@Parameter(description = "Filtro Data Final do Evento", required = false)
	@QueryParam("dataFinal")
	private String dataFinal;

	@EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Tipo de Ordenação", required = false)
	@DefaultValue("DESC")
	@QueryParam("ordem")
	private String tipoOrdenacao;
	
	@Parameter(description = "Número da página", required = false)
	@QueryParam("pagina")
	@DefaultValue("1")
	private Integer numeroPagina;
	
	@EnumValues(enumClass = ColunaOrdenacaoLogAcessoUsuarioEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
	@Parameter(description = "Coluna para ordenação", required = false)
	@QueryParam("coluna")
	@DefaultValue("dataAcesso")
	private String orderBy;
	
	@Parameter(description = "Itens por página", required = false)
	@QueryParam("itensPorPagina")
	@DefaultValue("20")
	private Integer itensPorPagina;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

}
