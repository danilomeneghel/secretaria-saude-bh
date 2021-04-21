package br.gov.pbh.prodabel.hubsmsa.ws.service;

import java.sql.Timestamp;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.pbh.prodabel.hubsmsa.email.MailService;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.ServicoException;
import br.gov.pbh.prodabel.hubsmsa.handler.EmailHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ValidacoesHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.DeParaService;
import br.gov.pbh.prodabel.hubsmsa.service.LogContatoNotificadoService;
import br.gov.pbh.prodabel.hubsmsa.service.LogServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model.SolicitacaoColetaExame;
import br.gov.pbh.prodabel.hubsmsa.ws.service.mapper.SolicitacaoColetaMapper;

/**
 * 
 * @author weverton.lucena@ctis.com.br classe responsável por orquestrar as chamadas relacionadas
 *         aos servicos de coletas de exames
 * 
 */

@Stateless
@LocalBean
public class ColetaExameService {

  private static final Logger LOG = LoggerFactory.getLogger(ColetaExameService.class);

  private static final String SIGRAH = "sigrah";
  private static final String SISREDE = "sisrede";
  private static final String SERVICO = "buscarSolicitacoesColetaExameDoPaciente";

  @EJB
  private DeParaService deParaService;

  @EJB
  private SisredeService sisredeService;

  @EJB
  private SistemaService sistemaService;

  @EJB
  private ContatoEmpresaServicoService contatoService;

  @EJB
  private ServicoService servicoService;

  @EJB
  private LogServicoService logServicoService;
  
  @EJB
  private MailService mailService;
  
  @EJB
  private LogContatoNotificadoService logContatoNotificadoService;
  
  private ServicosHandler servicosHandler;

  private LogServico logServico;

  /**
   * Buscar solicitacoes coleta exame do paciente.
   *
   * @param solicitacaoColeta the solicitacao coleta
   * @return the solicitacoes coleta exame response
   * @throws JsonProcessingException
   */
  public SolicitacoesColetaExameResponse buscarSolicitacoesColetaExameDoPaciente(
      SolicitacaoColetaDTO solicitacaoColeta) {

    SolicitacaoColetaMapper solicitacaoColetaMapper = new SolicitacaoColetaMapper();
    ObjectMapper mapper = new ObjectMapper();
    SolicitacoesColetaExameResponse response;
    inicializarServicos();
    
    validarCPF(solicitacaoColeta);

    try {
      inicializarLogServico();
      logServico.setRequisicao(mapper.writeValueAsString(solicitacaoColeta));

      SolicitacaoColetaExame solicitacoesExame =
          sisredeService.buscarSolicitacoesColetaExame(solicitacaoColeta);

      response = solicitacaoColetaMapper.mapper(solicitacoesExame);

      logServico.setResposta(mapper.writeValueAsString(response));
      logServico.setStatus(StatusExecucao.S);
      response = deParaService.realizarDePara(response, servicosHandler);
      response.setCodigoRetorno(200L);
    } catch (Exception e) {
      logServico.setResposta(e.getMessage());
      logServico.setStatus(StatusExecucao.F);
      LOG.error(e.getMessage(), e);
      throw new ServicoException();
    }

    // criar uma task para executar
    notificarELogar();

    return response;
  }

  /**
   * Validar CPF.
   *
   * @param solicitacaoColeta the solicitacao coleta
   */
  private void validarCPF(SolicitacaoColetaDTO solicitacaoColeta) {
    if (!StringUtils.isEmpty(solicitacaoColeta.getCpf()))
      ValidacoesHandler.validarCPF(solicitacaoColeta.getCpf());
  }

  /**
   * Inicializar log servico.
   */
  private void inicializarLogServico() {
    logServico = new LogServico();
    logServico.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setServico(servicosHandler.getServico());
  }

  /**
   * Notificar E logar.
   */
  public void notificarELogar() {

    LogServico logServicoGravado = logServicoService.gravarLogServico(logServico);
    
    EmailHandler.enviarEmailNotificacaoSucesso(logServicoGravado,
        servicosHandler.getContatosEmpresaSucesso(), mailService);

    if (logServico.getStatus() == StatusExecucao.S) {
      servicosHandler.getContatosEmpresaSucesso().stream()
          .forEach(c -> logContatoNotificadoService.gravarLogNotificadoSucesso(logServico, c));
    } else {
      servicosHandler.getContatosEmpresaFalha().stream()
          .forEach(c -> logContatoNotificadoService.gravarLogNotificadoFalha(logServico, c));
    }

  }

  /**
   * Inicializar servicos.
   */
  private void inicializarServicos() {
    servicosHandler = new ServicosHandler(SIGRAH, SISREDE, SERVICO);
    servicosHandler.setServices(sistemaService, servicoService, contatoService);
  }

  public void setSistemaService(SistemaService sistemaService) {
    this.sistemaService = sistemaService;
  }

  public void setContatoService(ContatoEmpresaServicoService contatoService) {
    this.contatoService = contatoService;
  }

  public void setServicoService(ServicoService servicoService) {
    this.servicoService = servicoService;
  }

  public void setSisredeService(SisredeService sisredeService) {
    this.sisredeService = sisredeService;
  }

  public void setDeParaService(DeParaService deParaService) {
    this.deParaService = deParaService;
  }

  public void setLogServicoService(LogServicoService logServicoService) {
    this.logServicoService = logServicoService;
  }

}
