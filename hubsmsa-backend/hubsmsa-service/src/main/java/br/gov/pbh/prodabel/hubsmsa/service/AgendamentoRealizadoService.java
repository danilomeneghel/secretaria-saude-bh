package br.gov.pbh.prodabel.hubsmsa.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.Atendimento;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.Atendimento.Medico;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AtendimentoAgendadoResponse;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.Paciente;
import br.gov.pbh.prodabel.hubsmsa.email.MailService;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.handler.DeParaHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.EmailHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ValidacoesHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.AgendamentoRealizado;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogContatoNotificado;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.AgendamentoRealizadoMapper;
import br.gov.pbh.prodabel.hubsmsa.util.ClientUtil;


/**
 * 
 * @author weverton.lucena
 * 
 * Classe responsável por buscar agendamentos no sigrah e enviar ao sisrede
 *
 */
@Stateless
@LocalBean
public class AgendamentoRealizadoService {

  private static final Logger LOG = LoggerFactory.getLogger(AgendamentoRealizadoService.class);

  private static final String SIGRAH = "sigrah";
  private static final String SISREDE = "sisrede";

  private static final String SERVICO = "recuperarAtendimentosAgendados";

  @EJB
  private DeParaService deParaService;

  @EJB
  private SistemaService sistemaService;

  @EJB
  private ContatoEmpresaServicoService contatoServico;

  @EJB
  private ServicoService servicoService;

  @EJB
  private MailService mailService;

  @EJB
  private LogContatoNotificadoService logContatoService;

  @EJB
  private LogServicoService logServicoService;

  // @EJB
  // private AgendamentoRealizadoDAO agendamentoRealizadoDAO;

  private ObjectMapper mapper = new ObjectMapper();

  private String jsonRecebido;

  private LogServico logServico;

  /**
   * Deparas.
   *
   * @param agendamento the agendamento
   */
  public String processar(String date) {
    String retorno = "OK";

    ServicosHandler servicosHandler = new ServicosHandler(SISREDE, SIGRAH, SERVICO);

    Sistema sistemaPrimario = sistemaService.consultarSistemaPorNome(SISREDE);
    Sistema sistemaSecundario = sistemaService.consultarSistemaPorNome(SIGRAH);
    Servico servico = servicoService
        .consultarServico(sistemaSecundario.getId(), sistemaPrimario.getId(), SERVICO).get(0);

    // busca lista de contatos que devem ser notificados em caso de sucesso
    List<ContatoEmpresaServico> contatosEmpresaSucesso =
        contatoServico.buscarContatosSucesso(servico);
    List<ContatoEmpresaServico> contatosEmpresaFalha = contatoServico.buscarContatosFalha(servico);

    logServico = inicializaLogServico(servico);

    // vai no serviço do sigrah buscar os agendamentos da data
    AtendimentoAgendadoResponse response = recuperarAtendimentosAgendados(date);

    for (Atendimento atendimento : response.getAtendimentos()) {

      DeParaHandler handler;
      try {
        // validacoes
        ValidacoesHandler.validarEmpresaAtiva(sistemaPrimario.getEmpresa());
        ValidacoesHandler.validarEmpresaAtiva(sistemaSecundario.getEmpresa());
        ValidacoesHandler.validarSistemaAtivo(sistemaPrimario);
        ValidacoesHandler.validarSistemaAtivo(sistemaSecundario);

        ValidacoesHandler.validarCPF(atendimento.getPaciente().getCpf());
        popularTelefone(atendimento.getPaciente());
        validarCamposObrigatorios(atendimento);

        // realizar de/para
        Map<String, String> map = prepararMap(atendimento);
        handler = deParaService.buscarDeParaHandler(map, sistemaPrimario, sistemaSecundario);
        realizarDePara(atendimento, handler);

        // gravar no banco do sisrede
        AgendamentoRealizado agendamentoRealizado = AgendamentoRealizadoMapper.mapper(atendimento);
        // agendamentoRealizadoDAO.gravar(agendamentoRealizado);

        String logRequestEnviado = mapper.writeValueAsString(agendamentoRealizado);

        logServico.setResposta(logRequestEnviado);
        logServico.setStatus(StatusExecucao.S);

        LogServico logServicoGravado = logServicoService.gravarLogServico(logServico);

        EmailHandler.enviarEmailNotificacaoSucesso(logServicoGravado, contatosEmpresaSucesso,
            mailService);

        // grava log contato notificado
        contatosEmpresaSucesso.stream().forEach(c -> gravarLogNotificado(logServico, c));

        LOG.info(logRequestEnviado);
        retorno = logRequestEnviado;
      } catch (NegocioException e) {
        String errorMsg = e.getMessages().get(0).getMsg();
        LOG.error(errorMsg, e);

        logServico.setStatus(StatusExecucao.F);
        LogServico logServicoGravado = logServicoService.gravarLogServico(logServico);
        EmailHandler.enviarEmailNotificacaoFalha(logServicoGravado, contatosEmpresaFalha, "",
            jsonRecebido, errorMsg, mailService);
        contatosEmpresaFalha.stream().forEach(c -> gravarLogNotificado(logServico, c));
      } catch (JsonProcessingException e) {
        LOG.error(e.getMessage(), e);

        logServico.setStatus(StatusExecucao.F);
        LogServico logServicoGravado = logServicoService.gravarLogServico(logServico);
        EmailHandler.enviarEmailNotificacaoFalha(logServicoGravado, contatosEmpresaFalha, "",
            jsonRecebido, "Erro ao recuperar dados do sigrah", mailService);
      }

    }
    return retorno;

  }

  /**
   * Buscar agendamentos.
   *
   * @param date the date
   */
  private AtendimentoAgendadoResponse recuperarAtendimentosAgendados(String date) {

    AtendimentoAgendadoResponse response = new AtendimentoAgendadoResponse();

    try {
      jsonRecebido = ClientUtil.get("https://api.mocki.io/v1/85ced712", null, String.class);
      // jsonRecebido = ClientUtil.get("http://localhost:3000/agendamentos", null, String.class);

      logServico.setRequisicao(jsonRecebido);

      LOG.info("dados recebidos: {}", jsonRecebido);
      response = mapper.readValue(jsonRecebido, AtendimentoAgendadoResponse.class);

    } catch (IOException e) {
      LOG.error("Falha ao recuperar atendimentos agendados", e);
    }

    return response;
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
   * Preparar log servico.
   *
   * @param servico the servico
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   * @return the log servico
   */
  private LogServico inicializaLogServico(Servico servico) {
    LogServico log = new LogServico();
    log.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    log.setServico(servico);

    return log;
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
   * Validar campos obrigatorios.
   *
   * @param agendamento the agendamento
   */
  private void validarCamposObrigatorios(Atendimento atendimento) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<Atendimento>> constraintViolations = validator.validate(atendimento);
    if (!constraintViolations.isEmpty()) {
      LOG.error(constraintViolations.iterator().next().getMessage());
    }

    Set<ConstraintViolation<Paciente>> constraintViolationsPaciente =
        validator.validate(atendimento.getPaciente());
    if (!constraintViolationsPaciente.isEmpty()) {
      LOG.error(constraintViolationsPaciente.iterator().next().getMessage());
      throw new NegocioException(constraintViolationsPaciente.iterator().next().getMessage());
    }

    Set<ConstraintViolation<Medico>> constraintViolationsMedico =
        validator.validate(atendimento.getMedicoExecutante());
    if (!constraintViolationsMedico.isEmpty()) {
      LOG.error(constraintViolationsMedico.iterator().next().getMessage());
      throw new NegocioException(constraintViolationsMedico.iterator().next().getMessage());
    }

    constraintViolationsMedico = validator.validate(atendimento.getMedicoSolicitante());
    if (!constraintViolationsMedico.isEmpty()) {
      LOG.error(constraintViolationsMedico.iterator().next().getMessage());
      throw new NegocioException(constraintViolationsMedico.iterator().next().getMessage());
    }

  }

  /**
   * Realizar de para.
   *
   * @param agendamento the agendamento
   * @param handler the handler
   */
  private void realizarDePara(Atendimento atendimento, DeParaHandler handler) {
    atendimento.getPaciente().setSexo(handler.getDeParaPrimarioMap().get("Sexo").getCodigo());

    atendimento
        .setCodigoProcedimento(handler.getDeParaPrimarioMap().get("Procedimento").getCodigo());
    atendimento.setPrioridade(
        Integer.valueOf(handler.getDeParaPrimarioMap().get("Prioridade").getCodigo()));
  }

  /**
   * Popular telefone.
   *
   * @param agendamento the agendamento
   */
  private void popularTelefone(Paciente paciente) {
    String telefone =
        StringUtils.isEmpty(paciente.getCelularContato()) == false ? paciente.getCelularContato()
            : paciente.getTelefoneFixoContato();
    paciente.setTelefone(telefone);
    paciente.setTelefoneFixoContato(null);
    paciente.setCelularContato(null);
  }

  /**
   * Preparar map.
   *
   * @param agendamento the agendamento
   * @return the map
   */
  private Map<String, String> prepararMap(Atendimento atendimento) {
    Map<String, String> map = new HashMap<>();
    map.put("Sexo", atendimento.getPaciente().getSexo());

    map.put("Procedimento", atendimento.getCodigoProcedimento());
    map.put("Prioridade", atendimento.getPrioridade().toString());

    return map;
  }

}
