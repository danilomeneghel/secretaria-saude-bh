package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.enumeration.SexoEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacaoColetaDTO implements Serializable {

  private static final long serialVersionUID = 6382271400889374796L;

  @QueryParam("nomePaciente")
  @NotBlank(message = "O campo nomePaciente é obrigatório")
  private String nomePaciente;

  @QueryParam("nomeMaePaciente")
  @NotBlank(message = "O campo nomeMaePaciente é obrigatório")
  private String nomeMaePaciente;

  @QueryParam("cpf")
  private String cpf;

  @QueryParam("cns")
  @NotBlank(message = "O campo cns é obrigatório")
  @Pattern(regexp = "\\d{15}", message = "O formato do campo cns não é válido.")
  private String cns;

  @QueryParam("dataNascimento")
  @Pattern(regexp = "([0-9]{4})(\\/)([0-9]{2})(\\/)([0-9]{2})",
      message = "O formato do campo dataNascimento não é válido.")
  private String dataNascimento;

  @QueryParam("sexo")
  @NotEmpty(message = "P campo sexo é obrigatório")
  @EnumValues(enumClass = SexoEnum.class,
      message = "Para o campo sexo Ativa deve ser informado os valores M, F ou I.",
      ignoreCase = true)
  private String sexo;

  @QueryParam("municipioNascimento")
  @NotBlank(message = "O campo municipioNascimento é obrigatório")
  private String municipioNascimento;

  @QueryParam("numeroProntuarioSISREDE")
  private Long numeroProntuarioSISREDE;

  @QueryParam("numeroProntuarioSIGRAH")
  private Long numeroProntuarioSIGRAH;

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public String getNomeMaePaciente() {
    return nomeMaePaciente;
  }

  public void setNomeMaePaciente(String nomeMaePaciente) {
    this.nomeMaePaciente = nomeMaePaciente;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCns() {
    return cns;
  }

  public void setCns(String cns) {
    this.cns = cns;
  }

  public String getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(String dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getMunicipioNascimento() {
    return municipioNascimento;
  }

  public void setMunicipioNascimento(String municipioNascimento) {
    this.municipioNascimento = municipioNascimento;
  }

  public Long getNumeroProntuarioSISREDE() {
    return numeroProntuarioSISREDE;
  }

  public void setNumeroProntuarioSISREDE(Long numeroProntuarioSISREDE) {
    this.numeroProntuarioSISREDE = numeroProntuarioSISREDE;
  }

  public Long getNumeroProntuarioSIGRAH() {
    return numeroProntuarioSIGRAH;
  }

  public void setNumeroProntuarioSIGRAH(Long numeroProntuarioSIGRAH) {
    this.numeroProntuarioSIGRAH = numeroProntuarioSIGRAH;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
