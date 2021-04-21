package br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import br.gov.pbh.prodabel.hubsmsa.annotations.DeParaSistemaPrimarioValido;
import br.gov.pbh.prodabel.hubsmsa.annotations.DeParaSistemaSecundarioValido;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class CadastrarDeParaDTO extends BaseDTO {

  /**
  * 
  */
  private static final long serialVersionUID = -5969644899707230222L;

  @NotNull(message = "O campo Tipo do De/Para é obrigatório(a).")
  private Long idTipoDePara;

  @EnumValues(enumClass = StatusEnum.class,
      message = "Para o campo status deve ser informado os valores Ativo ou Inativo.",
      ignoreCase = true)
  private String status;

  @NotBlank(message = "O campo Nome De/Para é obrigatório.")
  private String nomeDePara;

  @Valid
  @DeParaSistemaPrimarioValido
  private CadastrarSistemaDeParaDTO sistemaPrimario;

  @Valid
  @DeParaSistemaSecundarioValido
  private CadastrarSistemaDeParaDTO sistemaSecundario;

  public Long getIdTipoDePara() {
    return idTipoDePara;
  }

  public void setIdTipoDePara(Long idTipoDePara) {
    this.idTipoDePara = idTipoDePara;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public CadastrarSistemaDeParaDTO getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(CadastrarSistemaDeParaDTO sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public CadastrarSistemaDeParaDTO getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(CadastrarSistemaDeParaDTO sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

}
