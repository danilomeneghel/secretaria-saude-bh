package br.gov.pbh.prodabel.hubsmsa.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AtendimentoPacienteDTO;
import br.gov.pbh.prodabel.hubsmsa.email.MailService;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.ServicoException;
import br.gov.pbh.prodabel.hubsmsa.handler.DeParaHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.EmailHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ValidacoesHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.AtendimentoPaciente;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogContatoNotificado;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.AtendimentoPacienteMapper;
import br.gov.pbh.prodabel.hubsmsa.util.ClientUtil;

/**
 * 
 * @author weverton.lucena@ctis.com.br Classe responsável por tratar buscar atendimentos no sisrede
 * e enviar para o sigrah
 * 
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AtendimentoPacienteService {

  private static final Logger LOG = LoggerFactory.getLogger(AtendimentoPacienteService.class);

  private static final String SIGRAH = "sigrah";
  private static final String SISREDE = "sisrede";
  private static final String SERVICO = "recuperarAtendimentosAgendados";

  @EJB
  private DeParaService deParaService;

  @EJB
  private SistemaService sistemaService;

  @EJB
  private ContatoEmpresaServicoService contatoService;

  @EJB
  private ServicoService servicoService;

  @EJB
  private MailService mailService;

  @EJB
  private LogContatoNotificadoService logContatoService;

  @EJB
  private LogServicoService logServicoService;

  // @EJB
  // private AtendimentoPacienteDAO atendimentoDAO;

  private ObjectMapper mapper = new ObjectMapper();

  private LogServico logServico;

  private ServicosHandler servicosHandler;

  /**
   * Buscar atendimento do dia.
   *
   * @param dataAtendimento the data atendimento
   * @return the list
   */
  private List<AtendimentoPaciente> buscarAtendimentoDoDia(LocalDateTime dataAtendimento) {

    try {
      return consultarAtendimentosDoDiaMock();
      // return atendimentoDAO.consultarAtendimentosDoDia(dataAtendimento);
    } catch (DAOException e) {
      throw new ServicoException(e.getMessage());
    }

  }

  /**
   * Consultar atendimentos do dia mock.
   *
   * @return the list
   */
  private List<AtendimentoPaciente> consultarAtendimentosDoDiaMock() throws DAOException {
    AtendimentoPaciente atendimentoPaciente = new AtendimentoPaciente();
    atendimentoPaciente.setCns("1111111111");
    atendimentoPaciente.setCpf("52040268006");
    atendimentoPaciente.setDataAtendimento(LocalDateTime.now());
    atendimentoPaciente.setId(1L);
    atendimentoPaciente.setMunicipioNascimento("66600");
    atendimentoPaciente.setNomeMaePaciente("Mae");
    atendimentoPaciente.setNomePaciente("Pedro");
    atendimentoPaciente.setNumeroAgendamentoSigrah(10L);
    atendimentoPaciente.setNumeroSolicitacaoSISREDE(100L);
    atendimentoPaciente.setSexo("1");
    atendimentoPaciente.setStatusAtendimento("A");
    atendimentoPaciente.setTelefone("11996857474");
    atendimentoPaciente.setDataNascimento("2000-10-01");

    return Arrays.asList(atendimentoPaciente);

  }


  /**
   * Reprocessar registro com falha.
   */
  public void reprocessarRegistroComFalha() {
    List<LogServico> logServicoList = logServicoService.buscarResquisicoesFalhas();

    if (logServicoList != null && !logServicoList.isEmpty()) {
      LOG.info("quantidade de registros para reprocessar: {}", logServicoList.size());
      for (LogServico logServicoReenvio : logServicoList) {
        LOG.info("reprocessando request: {}", logServicoReenvio.getRequisicao());
        try {
          reprocessarEnvioSigrah(logServicoReenvio);
        } catch (IOException e) {
          LOG.error("erro ao reprocessar transacao. {}", e.getMessage());
        }
      }
    }
  }

  /**
   * Reprocessar envio sigrah.
   *
   * @param logServico the log servico
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CloneNotSupportedException
   */
  public void reprocessarEnvioSigrah(LogServico logServico)
      throws IOException {

    inicializarServicos();

    AtendimentoPacienteDTO atendimentoPaciente =
        mapper.readValue(logServico.getRequisicao(), AtendimentoPacienteDTO.class);
    String response = enviarAtendimento(atendimentoPaciente);

    LogServico logFilho = criarLogFilho(logServico);

    logFilho.setResposta(response);
    logFilho.setStatus(StatusExecucao.S);
    LogServico logServicoGravado = logServicoService.gravarLogServico(logFilho);

    EmailHandler.enviarEmailNotificacaoSucesso(logServicoGravado,
        servicosHandler.getContatosEmpresaSucesso(), mailService);

    servicosHandler.getContatosEmpresaSucesso().stream()
        .forEach(c -> gravarLogNotificado(logServico, c));
  }

  /**
   * Criar log filho.
   *
   * @param logServico the log servico
   * @return the log servico
   */
  private LogServico criarLogFilho(LogServico logServico) {
    LogServico logFilho = new LogServico();
    logFilho.setLogServicoPai(logServico);
    logFilho.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    logFilho.setRequisicao(logServico.getRequisicao());
    logFilho.setServico(logServico.getServico());
    return logFilho;
  }

  /**
   * Registrar Atendimento do Paciente.
   */
  public String registrarAtendimentoPaciente() {

    String retorno = "";
    inicializarServicos();

    AtendimentoPacienteMapper atendimentomapper = new AtendimentoPacienteMapper();
    String request = "";
    // buscar atendimentos no sisrede
    List<AtendimentoPaciente> atendimentos = new ArrayList<>();

    logServico = iniciarLogServico(servicosHandler.getServico());
    try {
      atendimentos = buscarAtendimentoDoDia(LocalDateTime.now());
    } catch (ServicoException e) {
      LOG.error(e.getMessage());
      gravarLogServicoFalha();
    }

    for (AtendimentoPaciente atendimento : atendimentos) {
      AtendimentoPacienteDTO dto = atendimentomapper.mapper(atendimento);

      try {

        ValidacoesHandler.validarCPF(atendimento.getCpf());

        // realizar de/para
        Map<String, String> map = prepararMap(atendimento);
        DeParaHandler handler = deParaService.buscarDeParaHandler(map,
            servicosHandler.getSistemaPrimario(), servicosHandler.getSistemaSecundario());
        realizarDePara(atendimento, handler);

        request = mapper.writeValueAsString(dto);
        logServico.setRequisicao(request);

        String response = enviarAtendimento(dto);
        retorno = response;
        // recuperar response
        logServico.setResposta(response);
        logServico.setStatus(StatusExecucao.S);

        LogServico logServicoGravado = logServicoService.gravarLogServico(logServico);

        EmailHandler.enviarEmailNotificacaoSucesso(logServicoGravado,
            servicosHandler.getContatosEmpresaSucesso(), mailService);

        // grava log contato notificado
        servicosHandler.getContatosEmpresaSucesso().stream()
            .forEach(c -> gravarLogNotificado(logServico, c));

      } catch (JsonProcessingException e) {
        LOG.error("Falha ao converter json: {}", e.getMessage());
      } catch (ServicoException e) {
        gravarLogServicoFalha();
        EmailHandler.enviarEmailNotificacaoFalha(logServico,
            servicosHandler.getContatosEmpresaFalha(), request, "", e.getMessage(), mailService);

        LOG.error("Falha ao executar servico: {}", e.getMessage());
      }
    }
    return retorno;
  }

  /**
   * Inicializar servicos.
   */
  private void inicializarServicos() {
    servicosHandler = new ServicosHandler(SISREDE, SIGRAH, SERVICO);
    servicosHandler.setServices(sistemaService, servicoService, contatoService);
  }

  /**
   * Gravar log servico falha.
   */
  private void gravarLogServicoFalha() {
    logServico.setStatus(StatusExecucao.F);
    logServico.setResposta("");
    logServicoService.gravarLogServico(logServico);
  }

  /**
   * Preparar log servico.
   *
   * @param servico the servico
   * @param request the request
   * @return the log servico
   */
  private LogServico iniciarLogServico(Servico servico) {
    LogServico log = new LogServico();
    log.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    log.setServico(servico);
    log.setRequisicao("");
    return log;
  }

  /**
   * Gravar log notificado.
   *
   * @param logServico the log servico
   * @param contato the contato
   */
  private void gravarLogNotificado(LogServico logServico, ContatoEmpresaServico contato) {
    LogContatoNotificado logContatoNotificado = prepararLogNotificado(logServico, contato);
    logContatoService.gravarLogContatoNotificado(logContatoNotificado);
  }

  /**
   * Preparar log notificado.
   *
   * @param logServico the log servico
   * @param contato the contato
   * @return the log contato notificado
   */
  private LogContatoNotificado prepararLogNotificado(LogServico logServico,
      ContatoEmpresaServico contato) {
    LogContatoNotificado logContatoNotificado = new LogContatoNotificado();
    logContatoNotificado.setLogServico(logServico);
    logContatoNotificado.setNotificacaoSucesso("1");
    logContatoNotificado.setNotificacaoFalha("0");
    logContatoNotificado.setContatoEmpresaServico(contato);
    return logContatoNotificado;
  }

  /**
   * Realizar de para.
   *
   * @param agendamento the agendamento
   * @param handler the handler
   */
  private void realizarDePara(AtendimentoPaciente atendimentoPaciente, DeParaHandler handler) {
    atendimentoPaciente.setSexo(handler.getDeParaSecundarioMap().get("Sexo").getCodigo());
    atendimentoPaciente
        .setStatusAtendimento(handler.getDeParaSecundarioMap().get("Atendimento").getCodigo());
  }

  /**
   * Enviar atendimento.
   *
   * @param atendimento the atendimento
   * @return
   * @throws JsonProcessingException
   */
  public String enviarAtendimento(AtendimentoPacienteDTO atendimentoDTO)
      throws JsonProcessingException {
    String atendimentoJson = mapper.writeValueAsString(atendimentoDTO);
    LOG.info("Enviando atendimento: {}", atendimentoJson);

    try {
      Response response = ClientUtil.post("https://api.mocki.io/v1/45c2c454", null, atendimentoDTO);
      String strResponse = response.readEntity(String.class);
      LOG.info("response:  {}", strResponse);
      return strResponse;
    } catch (WebApplicationException e) {
      LOG.error("Erro ao enviar atentimento ao Sigrah", e);
      throw new ServicoException("Erro ao enviar atentimento ao Sigrah");
    }

  }

  /**
   * Preparar map.
   *
   * @param atendimento the atendimento
   * @return the map
   */
  private Map<String, String> prepararMap(AtendimentoPaciente atendimento) {
    Map<String, String> map = new HashMap<>();
    map.put("Sexo", atendimento.getSexo());
    map.put("Atendimento", atendimento.getStatusAtendimento());
    return map;
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

}
