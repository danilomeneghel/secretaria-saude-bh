package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import org.hibernate.validator.constraints.br.CPF;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.annotations.NotEmpty;
import br.gov.pbh.prodabel.hubsmsa.util.json.notmasked.NotMasked;

@JsonInclude(Include.NON_NULL)
public class Solicitante implements Serializable {

  private static final long serialVersionUID = 2483853420925712309L;

  @NotEmpty
  private String nomeMedico;

  @NotEmpty
  @NotMasked
  @CPF(message = "CPF inválido.")
  private String cpfMedico;

  @NotEmpty
  private String tipoSolicitante;

  @NotEmpty
  private String cnesSolicitante;

  public String getNomeMedico() {
    return nomeMedico;
  }

  public void setNomeMedico(String nomeMedico) {
    this.nomeMedico = nomeMedico;
  }

  public String getCpfMedico() {
    return cpfMedico;
  }

  public void setCpfMedico(String cpfMedico) {
    this.cpfMedico = cpfMedico;
  }

  public String getTipoSolicitante() {
    return tipoSolicitante;
  }

  public void setTipoSolicitante(String tipoSolicitante) {
    this.tipoSolicitante = tipoSolicitante;
  }

  public String getCnesSolicitante() {
    return cnesSolicitante;
  }

  public void setCnesSolicitante(String cnesSolicitante) {
    this.cnesSolicitante = cnesSolicitante;
  }

}
