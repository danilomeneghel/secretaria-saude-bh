package br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import io.swagger.v3.oas.annotations.Parameter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroPesquisaContatoEmpresaServicoDTO extends BaseDTO {



  /**
   * 
   */
  private static final long serialVersionUID = 5362902264240445764L;

  @Parameter(description = "Filtro id da empresa contato serviço", required = false)
  @QueryParam("idEmpresa")
  private Long idEmpresa;

  @Parameter(description = "Filtro id do contato", required = false)
  @QueryParam("idContato")
  private Long idContato;

  @Parameter(description = "Filtros do id do sistema", required = false)
  @QueryParam("idSistema")
  private Long idSistema;

  @Parameter(description = "Filtros do id do serviço", required = false)
  @QueryParam("idServico")
  private Long idServico;

 
  @EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.", ignoreCase = true)
  @Parameter(description = "Tipo de Ordenação", required = false)
  @DefaultValue("ASC")
  @QueryParam("ordem")
  private String tipoOrdenacao;

  @Parameter(description = "Número da página", required = false)
  @QueryParam("pagina")
  @DefaultValue("1")
  private Integer numeroPagina;
  
  @EnumValues(enumClass = ColunaOrdenacaoContatoEmpresaServicoEnum.class, message = "Coluna para ordenação inválida.", ignoreCase = true)
  @Parameter(description = "Coluna para ordenação", required = false)
  @QueryParam("coluna")
  @DefaultValue("EMPRESA")
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

  public Long getIdContato() {
    return idContato;
  }

  public void setIdContato(Long idContato) {
    this.idContato = idContato;
  }

  public Long getIdSistema() {
    return idSistema;
  }

  public void setIdSistema(Long idSistema) {
    this.idSistema = idSistema;
  }

  public Long getIdServico() {
    return idServico;
  }

  public void setIdServico(Long idServico) {
    this.idServico = idServico;
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
