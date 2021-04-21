package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServicoBaseDTO implements Serializable {

  private static final long serialVersionUID = 6290999161924882609L;

  private Integer codigoRetorno;

  private String descricaoErro;

  private Paciente paciente;

  public Integer getCodigoRetorno() {
    return codigoRetorno;
  }

  public void setCodigoRetorno(Integer codigoRetorno) {
    this.codigoRetorno = codigoRetorno;
  }

  public String getDescricaoErro() {
    return descricaoErro;
  }

  public void setDescricaoErro(String descricaoErro) {
    this.descricaoErro = descricaoErro;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

}
