package br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro;

import javax.validation.constraints.NotNull;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class CadastrarCampoDeParaDTO extends BaseDTO {



  /**
   * 
   */
  private static final long serialVersionUID = 14556675749776806L;

  private Long id;

  @NotNull(message = "O campo Código do De/Para é obrigatório(a).")
  private String codigo;
  @NotNull(message = "O campo Descrição do De/Para é obrigatório(a).")
  private String descricao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
