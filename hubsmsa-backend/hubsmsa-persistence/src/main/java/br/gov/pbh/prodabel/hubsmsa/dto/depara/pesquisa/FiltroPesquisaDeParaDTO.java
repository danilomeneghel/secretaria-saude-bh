package br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoDeParaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import io.swagger.v3.oas.annotations.Parameter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroPesquisaDeParaDTO extends BaseDTO {

  /**
  * 
  */
  private static final long serialVersionUID = 5776135911958578151L;

  @Parameter(description = "Filtro De/Para", required = false)
  private Long idTipoDePara;

  @Parameter(description = "Filtro nome do De/Para", required = false)
  private String nomeDePara;

  @EnumValues(enumClass = StatusEnum.class, ignoreCase = true, acceptNull = true,
      message = "Para o campo status deve ser informado os valores A(Ativo),I(Inativo).")
  @Parameter(description = "Filtro Status", required = false)
  private List<String> status;

  @Parameter(description = "Filtros do sistema primário", required = false)
  private FiltroSistemaDTO sistemaPrimario;

  @Parameter(description = "Filtros do sistema secundario", required = false)
  private FiltroSistemaDTO sistemaSecundario;

  @EnumValues(enumClass = TipoOrdenacaoEnum.class, message = "Tipo de Ordenação inválida.",
      ignoreCase = true)
  @Parameter(description = "Tipo de Ordenação", required = false)
  private String ordem;

  @Parameter(description = "Número da página", required = false)
  private Integer pagina;

  @EnumValues(enumClass = ColunaOrdenacaoDeParaEnum.class,
      message = "Coluna para ordenação inválida.", ignoreCase = true)
  @Parameter(description = "Coluna para ordenação", required = false)
  private String coluna;

  @Parameter(description = "Itens por página", required = false)
  private Integer itensPorPagina;

  public FiltroPesquisaDeParaDTO() {}

  public Long getIdTipoDePara() {
    return idTipoDePara;
  }

  public void setIdTipoDePara(Long idTipoDePara) {
    this.idTipoDePara = idTipoDePara;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public List<String> getStatus() {
    return status;
  }

  public void setStatus(List<String> status) {
    this.status = status;
  }

  public FiltroSistemaDTO getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(FiltroSistemaDTO sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public FiltroSistemaDTO getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(FiltroSistemaDTO sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public String getOrdem() {
    if (Objects.isNull(this.ordem)) {
      this.ordem = TipoOrdenacaoEnum.ASC.toString();
    }
    return ordem;
  }

  public void setOrdem(String ordem) {
    this.ordem = ordem;
  }

  public Integer getPagina() {
    if (Objects.isNull(this.pagina)) {
      this.pagina = 1;
    }
    return pagina;
  }

  public void setPagina(Integer pagina) {
    this.pagina = pagina;
  }

  public String getColuna() {
    if (Objects.isNull(this.coluna)) {
      this.coluna = ColunaOrdenacaoDeParaEnum.NOME_DEPARA.toString();
    }
    return coluna;
  }

  public void setColuna(String coluna) {
    this.coluna = coluna;
  }

  public Integer getItensPorPagina() {
    if (Objects.isNull(this.itensPorPagina)) {
      this.itensPorPagina = PaginacaoUtil.NUMERO_MAXIMO_REGISTROS_POR_PAGINA_20;
    }
    return itensPorPagina;
  }

  public void setItensPorPagina(Integer itensPorPagina) {
    this.itensPorPagina = itensPorPagina;
  }

}
