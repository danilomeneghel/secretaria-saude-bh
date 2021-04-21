package br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

@JsonInclude(Include.NON_NULL)
public class VisualizarDeParaPrimarioSecundarioDTO extends BaseDTO {

  /**
  * 
  */
  private static final long serialVersionUID = -4109883901728251077L;

  private Long id;

  private Long idDePara;

  private String codigo;

  private String descricao;

  public VisualizarDeParaPrimarioSecundarioDTO() {

  }

  public VisualizarDeParaPrimarioSecundarioDTO(Long id, Long idDePara, String codigo,
      String descricao) {
    super();
    this.id = id;
    this.idDePara = idDePara;
    this.codigo = codigo;
    this.descricao = descricao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdDePara() {
    return idDePara;
  }

  public void setIdDePara(Long idDePara) {
    this.idDePara = idDePara;
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

}
