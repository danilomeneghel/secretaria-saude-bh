package br.gov.pbh.prodabel.hubsmsa.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoRequestDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.email.MailMessage;
import br.gov.pbh.prodabel.hubsmsa.email.MailService;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogContatoNotificado;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.util.ClientUtil;

// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
public class AgendamentoService {

  private static final String ASSUNTO_FALHA = "HUB SMSA – Falha ao executar o serviço";
  private static final String ASSUNTO_SUCESSO = "HUB SMSA – Serviço executado com sucesso";
  private static final String SIGRAH = "sigrah";
  private static final String SISREDE = "sisrede";
  private static final Logger LOG = LoggerFactory.getLogger(AgendamentoService.class);
  private static final String SERVICO = "AGENDAMENTO";

  @EJB
  private ServicoService servicoService;

  @EJB
  private LogServicoService logServicoService;

  @EJB
  private DeParaService deParaService;

  @EJB
  private ContatoEmpresaServicoService contatoService;

  @EJB
  private MailService mailService;

  @EJB
  private LogContatoNotificadoService logContatoService;

  @EJB
  private SistemaService sistemaService;

  /**
   * Solicitar agendamento.
   *
   * @param agendamentoDTO dados para agendamento
   */
  public AgendamentoResponseDTO solicitarAgendamento(AgendamentoRequestDTO requestAgendamentoDTO) {
    ObjectMapper objectMapper = new ObjectMapper();

    ServicosHandler servicosHandler = new ServicosHandler(SISREDE, SIGRAH, SERVICO);
    servicosHandler.setServices(sistemaService, servicoService, contatoService);

    // busca lista de contatos para notificar
    List<ContatoEmpresaServico> contatosEmpresa = servicosHandler.getContatosEmpresaSucesso();

    String logRequestRecebido = "{}";
    String logRequestEnviado = "{}";
    String logResponseRecebido = "{}";
    try {

      logRequestRecebido = objectMapper.writeValueAsString(requestAgendamentoDTO);
      LOG.info("parametros recebidos: {}", logRequestRecebido);

      popularTelefone(requestAgendamentoDTO);
      realizarDeParaRequest(requestAgendamentoDTO, servicosHandler.getSistemaPrimario(),
          servicosHandler.getSistemaSecundario());

      logRequestEnviado = objectMapper.writeValueAsString(requestAgendamentoDTO);
      LOG.info("parametros enviados: {}", logRequestEnviado);
      // realizar as validações e enviar para o sigrah

      AgendamentoResponseDTO agendamentoDto =
          ClientUtil.get("https://api.mocki.io/v1/03f55c17", null, AgendamentoResponseDTO.class);

      realizarDeparaResponse(agendamentoDto, servicosHandler.getSistemaSecundario(),
          servicosHandler.getSistemaPrimario());

      // salvar log dados retornados
      logResponseRecebido = objectMapper.writeValueAsString(agendamentoDto);
      LOG.info("parametros retornados: {}", logResponseRecebido);

      // envia email e grava o log
      enviarEmailNotificacaoSucesso(servicosHandler.getServico(), contatosEmpresa,
          logRequestEnviado,
          logResponseRecebido);

      return agendamentoDto;

    } catch (JsonProcessingException e) {
      LOG.error(e.getMessage());
      throw new NegocioException();
    } catch (NegocioException e) {
      enviarEmailNotificacaoFalha(servicosHandler.getServico(), contatosEmpresa, logRequestEnviado,
          logResponseRecebido,
          e.getMessage());
      throw e;
    }

  }

  /**
   * Popular telefone.
   *
   * @param requestAgendamentoDTO the request agendamento DTO
   */
  private void popularTelefone(AgendamentoRequestDTO requestAgendamentoDTO) {

    String telefone = requestAgendamentoDTO.getPaciente().getTelefone();

    if (telefone != null && telefone.length() == 11) {
      requestAgendamentoDTO.getPaciente().setCelularContato(telefone);
    }
    if (telefone != null && telefone.length() == 10) {
      requestAgendamentoDTO.getPaciente().setTelefoneFixoContato(telefone);
    }
    requestAgendamentoDTO.getPaciente().setTelefone(null);

  }

  /**
   * Enviar email notificacao.
   *
   * @param servico the servico
   * @param contatosEmpresa the contatos empresa
   * @param contatos the contatos
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   */
  private void enviarEmailNotificacaoSucesso(Servico servico,
      List<ContatoEmpresaServico> contatosEmpresa, String logRequestEnviado,
      String logResponseRecebido) {
    LogServico logServico = prepararLogServico(servico, logRequestEnviado, logResponseRecebido);
    logServico = logServicoService.gravarLogServico(logServico);

    // enviar email e gravar log de notificacao
    for (ContatoEmpresaServico contato : contatosEmpresa) {
      String body = formatarEmailSucesso(logServico, contato);
      LOG.info("enviando email para {}", contato.getContatoEmpresa().getEmail());
      LOG.info("corpo do email \r\n{}", body);
      MailMessage email =
          new MailMessage(contato.getContatoEmpresa().getEmail(), ASSUNTO_SUCESSO, body);
      mailService.sendMail(email);
      LogContatoNotificado logContatoNotificado = prepararLogNotificado(logServico, contato);
      logContatoService.gravarLogContatoNotificado(logContatoNotificado);
    }
  }

  /**
   * Enviar email notificacao falha.
   *
   * @param servico the servico
   * @param contatosEmpresa the contatos empresa
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   * @param msgErro the msg erro
   */
  private void enviarEmailNotificacaoFalha(Servico servico,
      List<ContatoEmpresaServico> contatosEmpresa, String logRequestEnviado,
      String logResponseRecebido, String msgErro) {
    LogServico logServico = prepararLogServico(servico, logRequestEnviado, logResponseRecebido);
    logServico = logServicoService.gravarLogServico(logServico);

    // enviar email e gravar log de notificacao
    for (ContatoEmpresaServico contato : contatosEmpresa) {
      String body = formatarEmailFalha(logServico, contato, msgErro);
      LOG.info("enviando email para {}", contato.getContatoEmpresa().getEmail());
      LOG.info("corpo do email \r\n{}", body);
      MailMessage email =
          new MailMessage(contato.getContatoEmpresa().getEmail(), ASSUNTO_FALHA, body);
      mailService.sendMail(email);
      LogContatoNotificado logContatoNotificado = prepararLogNotificado(logServico, contato);
      logContatoService.gravarLogContatoNotificado(logContatoNotificado);
    }
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
   * Formatar email.
   *
   * @param logServico the log servico
   * @param sdfData the sdf data
   * @param sdfHora the sdf hora
   * @param contato the contato
   * @return the string
   */
  private String formatarEmailSucesso(LogServico logServico, ContatoEmpresaServico contato) {
    SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyy");
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    return String.format(
        "Prezado %s,\r\n" + "\r\n" + "O serviço “%s” foi executado com sucesso em %s às %sh.\r\n"
            + "\r\n" + "Atenciosamente,\r\n" + "\r\n" + "HUB SMSA – Sistema de Integração\r\n"
            + "Secretaria Municipal de Saúde de Belo Horizonte.\r\n"
            + "* Este e-mail foi enviado automaticamente pelo HUB SMSA.",
        contato.getContatoEmpresa().getNome(), logServico.getServico().getNome(),
        sdfData.format(logServico.getDataInicioEvento()),
        sdfHora.format(logServico.getDataInicioEvento()));
  }

  /**
   * Formatar email falha.
   *
   * @param logServico the log servico
   * @param contato the contato
   * @return the string
   */
  private String formatarEmailFalha(LogServico logServico, ContatoEmpresaServico contato,
      String msgErro) {
    SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyy");
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    return String.format(
        "Prezado %s,\r\n" + "\r\n" + "O serviço “%s” foi executado com FALHA em %s às %sh.\r\n"
            + "\r\n" + "Motivo:\r\n" + "%s",
        contato.getContatoEmpresa().getNome(), logServico.getServico().getNome(),
        sdfData.format(logServico.getDataInicioEvento()),
        sdfHora.format(logServico.getDataInicioEvento()), msgErro);
  }

  /**
   * Preparar log servico.
   *
   * @param servico the servico
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   * @return the log servico
   */
  private LogServico prepararLogServico(Servico servico, String logRequestEnviado,
      String logResponseRecebido) {
    LogServico logServico = new LogServico();
    logServico.setDataInicioEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setServico(servico);
    logServico.setRequisicao(logRequestEnviado);
    logServico.setResposta(logResponseRecebido);
    logServico.setDataFimEvento(new Timestamp(System.currentTimeMillis()));
    logServico.setStatus(StatusExecucao.S);
    return logServico;
  }

  /**
   * Realizar depara response.
   *
   * @param agendamentoDto the agendamento dto
   * @param sistemaSecundario the sistema secundario
   * @param sistemaPrimario the sistema primario
   */
  private void realizarDeparaResponse(AgendamentoResponseDTO agendamentoDto,
      Sistema sistemaSecundario, Sistema sistemaPrimario) {

    DePara deParaStatusAgendamento =
        deParaService.consultarDePara("Agendamento", sistemaPrimario.getId(),
            sistemaSecundario.getId(), agendamentoDto.getAgendamento().getStatusAgendamento());
    if (deParaStatusAgendamento != null) {
      Optional<DeParaPrimario> statusAgendamentoOpt =
          deParaStatusAgendamento.getDeParaPrimario().stream().findFirst();
      if (statusAgendamentoOpt.isPresent()) {
        agendamentoDto.getAgendamento()
            .setStatusAgendamento(statusAgendamentoOpt.get().getCodigo());
      }
    }

  }

  /**
   * Realizar de para.
   *
   * @param requestAgendamentoDTO the request agendamento DTO
   * @param sistemaPrimario the sistema primario
   * @param sistemaSecundario the sistema secundario
   */
  private void realizarDeParaRequest(AgendamentoRequestDTO requestAgendamentoDTO,
      Sistema sistemaPrimario, Sistema sistemaSecundario) {
    DePara deParaSexo = deParaService.consultarDePara("Sexo", sistemaPrimario.getId(),
        sistemaSecundario.getId(), requestAgendamentoDTO.getPaciente().getSexo());
    DePara deParaProcedimento = deParaService.consultarDePara("Procedimento",
        sistemaPrimario.getId(), sistemaSecundario.getId(),
        requestAgendamentoDTO.getAgendamento().getCodigoProcedimento());

    if (deParaSexo != null) {
      Optional<DeParaSecundario> deParaSecundarioOpt =
          deParaSexo.getDeParaSecundario().stream().findFirst();
      if (deParaSecundarioOpt.isPresent())
        requestAgendamentoDTO.getPaciente().setSexo(deParaSecundarioOpt.get().getCodigo());
    }
    if (deParaProcedimento != null) {
      Optional<DeParaSecundario> deParaSecundarioOpt =
          deParaProcedimento.getDeParaSecundario().stream().findFirst();

      if (deParaSecundarioOpt.isPresent())
        requestAgendamentoDTO.getAgendamento()
            .setCodigoProcedimento(deParaSecundarioOpt.get().getCodigo());
    }
    if (Boolean.TRUE.equals(requestAgendamentoDTO.getAgendamento().getRegulacao())) {
      requestAgendamentoDTO.getAgendamento().setPrioridade(1);
    } else {
      DePara deParaPrioridade = deParaService.consultarDePara("Prioridade", sistemaPrimario.getId(),
          sistemaSecundario.getId(),
          requestAgendamentoDTO.getAgendamento().getPrioridade().toString());
      if (deParaPrioridade != null) {

        Optional<DeParaSecundario> deParaSecundarioOpt =
            deParaPrioridade.getDeParaSecundario().stream().findFirst();
        if (deParaSecundarioOpt.isPresent())
          requestAgendamentoDTO.getAgendamento()
              .setPrioridade(Integer.valueOf(deParaSecundarioOpt.get().getCodigo()));
      }
    }
  }

}
