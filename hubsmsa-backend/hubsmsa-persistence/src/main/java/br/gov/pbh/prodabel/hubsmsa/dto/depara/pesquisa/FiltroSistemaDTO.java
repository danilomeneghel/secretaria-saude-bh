package br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa;

import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.CampoDeParaDTO;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroSistemaDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = -9194873593117706845L;
  @Parameter(description = "Identificador da empresa", required = false)
  private Long idEmpresa;
  @Parameter(description = "Identificador do sistema", required = false)
  private Long idSistema;
  @Parameter(description = "Lista de campos com código e descricao", required = false)
  private List<CampoDeParaDTO> campos;

  public Long getIdEmpresa() {
    return idEmpresa;
  }

  public void setIdEmpresa(Long idEmpresa) {
    this.idEmpresa = idEmpresa;
  }

  public Long getIdSistema() {
    return idSistema;
  }

  public void setIdSistema(Long idSistema) {
    this.idSistema = idSistema;
  }

  public List<CampoDeParaDTO> getCampos() {
    return campos;
  }

  public void setCampos(List<CampoDeParaDTO> campos) {
    this.campos = campos;
  }

}
