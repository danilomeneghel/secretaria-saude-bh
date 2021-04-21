package br.gov.pbh.prodabel.hubsmsa.dto.logservico;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;

public class VisualizarLogServicoDTO extends BaseDTO {


  private static final long serialVersionUID = 3188902513570636470L;

  private Long id;

  private VisualizarServicoDTO servico;

  private String dataInicioEvento;
  private String dataFimEvento;
  private StatusExecucao status;

  public VisualizarServicoDTO getServico() {
    return servico;
  }

  public void setServico(VisualizarServicoDTO servico) {
    this.servico = servico;
  }

  public String getDataInicioEvento() {
    return dataInicioEvento;
  }

  public void setDataInicioEvento(String dataInicioEvento) {
    this.dataInicioEvento = dataInicioEvento;
  }

  public String getDataFimEvento() {
    return dataFimEvento;
  }

  public void setDataFimEvento(String dataFimEvento) {
    this.dataFimEvento = dataFimEvento;
  }

  public StatusExecucao getStatus() {
    return status;
  }

  public void setStatus(StatusExecucao status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
	

	

}
