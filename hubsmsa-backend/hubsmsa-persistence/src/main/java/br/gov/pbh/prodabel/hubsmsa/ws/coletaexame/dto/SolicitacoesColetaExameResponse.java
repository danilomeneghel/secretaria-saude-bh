package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.ws.dto.ExameSolicitadoDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.dto.ServicoResponseDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacoesColetaExameResponse extends ServicoResponseDTO {

  private static final long serialVersionUID = -6454794859488771576L;

  private PacienteColetaExame paciente;

  private PedidoColeta pedido;

  private MedicoSolicitante solicitante;

  private List<ExameSolicitadoDTO> examesSolicitados;

  public PacienteColetaExame getPaciente() {
    return paciente;
  }

  public void setPaciente(PacienteColetaExame paciente) {
    this.paciente = paciente;
  }

  public PedidoColeta getPedido() {
    return pedido;
  }

  public void setPedido(PedidoColeta pedido) {
    this.pedido = pedido;
  }

  public MedicoSolicitante getSolicitante() {
    return solicitante;
  }

  public void setSolicitante(MedicoSolicitante solicitante) {
    this.solicitante = solicitante;
  }

  public List<ExameSolicitadoDTO> getExamesSolicitados() {
    return examesSolicitados;
  }

  public void setExamesSolicitados(List<ExameSolicitadoDTO> examesSolicitados) {
    this.examesSolicitados = examesSolicitados;
  }


}
