package br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class CadastrarSistemaDeParaDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = -7976073450149038159L;
  private Long idSistema;
  private Long idEmpresa;
  @Valid
  @NotNull(message = "O campo Código e Descrição do De/Para são obrigatório(a). (Sistema Primário)")
  List<CadastrarCampoDeParaDTO> camposDePara;

  public Long getIdSistema() {
    return idSistema;
  }

  public void setIdSistema(Long idSistema) {
    this.idSistema = idSistema;
  }

  public Long getIdEmpresa() {
    return idEmpresa;
  }

  public void setIdEmpresa(Long idEmpresa) {
    this.idEmpresa = idEmpresa;
  }

  public List<CadastrarCampoDeParaDTO> getCamposDePara() {
    return camposDePara;
  }

  public void setCamposDePara(List<CadastrarCampoDeParaDTO> camposDePara) {
    this.camposDePara = camposDePara;
  }

}
