package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
@JsonInclude(Include.NON_NULL)
public class AtendimentoAgendadoResponse implements Serializable {

  private static final long serialVersionUID = 1643592823880603300L;

  private Integer codigoRetorno;

  private String descricaoErro;

  @JsonProperty("dadosDoAtendimento")
  private List<Atendimento> atendimentos;

  public List<Atendimento> getAtendimentos() {
    return atendimentos;
  }

  public void setAtendimentos(List<Atendimento> atendimentos) {
    this.atendimentos = atendimentos;
  }

  public String getDescricaoErro() {
    return descricaoErro;
  }

  public void setDescricaoErro(String descricaoErro) {
    this.descricaoErro = descricaoErro;
  }

  public Integer getCodigoRetorno() {
    return codigoRetorno;
  }

  public void setCodigoRetorno(Integer codigoRetorno) {
    this.codigoRetorno = codigoRetorno;
  }



}
