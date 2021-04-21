package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;

@Entity
@SequenceGenerator(sequenceName = "sq_log_servico", name = "sq_log_servico", allocationSize = 1)
@Table(name = "log_servico")
public class LogServico extends EntidadeBase<Long> {

  private static final long serialVersionUID = -1673014216359867317L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_log_servico")
  @Column(name = "id_log_servico", nullable = false, unique = true)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_servico", foreignKey = @ForeignKey(name = "fk_log_servico_servico"),
      nullable = false)
  private Servico servico;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_reenvio_servico", nullable = true)
  private LogServico logServicoPai;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_reenvio_servico", nullable = true)
  private List<LogServico> logsServicoFilhos;

  @Column(name = "data_inicio_evento", nullable = false)
  private Timestamp dataInicioEvento;

  @Column(name = "data_fim_evento", nullable = false)
  private Timestamp dataFimEvento;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusExecucao status;

  @Column(nullable = false)
  private String requisicao;

  @Column(nullable = false)
  private String resposta;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Servico getServico() {
    return servico;
  }

  public void setServico(Servico servico) {
    this.servico = servico;
  }

  public Timestamp getDataInicioEvento() {
    return dataInicioEvento;
  }

  public void setDataInicioEvento(Timestamp dataIncioEvento) {
    this.dataInicioEvento = dataIncioEvento;
  }

  public Timestamp getDataFimEvento() {
    return dataFimEvento;
  }

  public void setDataFimEvento(Timestamp dataFimEvento) {
    this.dataFimEvento = dataFimEvento;
  }

  public StatusExecucao getStatus() {
    return status;
  }

  public void setStatus(StatusExecucao status) {
    this.status = status;
  }

  public String getRequisicao() {
    return requisicao;
  }

  public void setRequisicao(String requisicao) {
    this.requisicao = requisicao;
  }

  public String getResposta() {
    return resposta;
  }

  public void setResposta(String resposta) {
    this.resposta = resposta;
  }

  public LogServico getLogServicoPai() {
    return logServicoPai;
  }

  public void setLogServicoPai(LogServico logServicoPai) {
    this.logServicoPai = logServicoPai;
  }

}
