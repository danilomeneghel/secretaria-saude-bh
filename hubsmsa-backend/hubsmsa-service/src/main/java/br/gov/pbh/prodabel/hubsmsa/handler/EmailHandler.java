package br.gov.pbh.prodabel.hubsmsa.handler;

import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.email.MailMessage;
import br.gov.pbh.prodabel.hubsmsa.email.MailService;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.service.AgendamentoRealizadoService;

/**
 * 
 * @author weverton.lucena@ctis.com.br classe responsável por formatar e enviar emails
 *
 */
public class EmailHandler {

  private static final Logger LOG = LoggerFactory.getLogger(AgendamentoRealizadoService.class);

  private static final String ASSUNTO_FALHA = "HUB SMSA – Falha ao executar o serviço";
  private static final String ASSUNTO_SUCESSO = "HUB SMSA – Serviço executado com sucesso";

  /**
   * Enviar email notificacao sucesso.
   *
   * @param logServico the log servico
   * @param contatosEmpresa the contatos empresa
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   */
  public static void enviarEmailNotificacaoSucesso(LogServico logServico,
      List<ContatoEmpresaServico> contatosEmpresa, MailService mailService) {
    // enviar email e gravar log de notificacao
    for (ContatoEmpresaServico contato : contatosEmpresa) {
      String body = formatarEmailSucesso(logServico, contato);
      LOG.info("enviando email para {}", contato.getContatoEmpresa().getEmail());
      LOG.info("corpo do email \r\n{}", body);
      MailMessage email =
          new MailMessage(contato.getContatoEmpresa().getEmail(), ASSUNTO_SUCESSO, body);
      mailService.sendMail(email);
    }
  }

  /**
   * Formatar email sucesso.
   *
   * @param logServico the log servico
   * @param contato the contato
   * @return the string
   */
  private static String formatarEmailSucesso(LogServico logServico, ContatoEmpresaServico contato) {
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
   * Enviar email notificacao falha.
   *
   * @param logServico the log servico
   * @param contatosEmpresa the contatos empresa
   * @param logRequestEnviado the log request enviado
   * @param logResponseRecebido the log response recebido
   * @param msgErro the msg erro
   */
  public static void enviarEmailNotificacaoFalha(LogServico logServico,
      List<ContatoEmpresaServico> contatosEmpresa, String logRequestEnviado,
      String logResponseRecebido, String msgErro, MailService mailService) {
    // enviar email e gravar log de notificacao
    for (ContatoEmpresaServico contato : contatosEmpresa) {
      String body = formatarEmailFalha(logServico, contato, msgErro);
      LOG.info("enviando email para {}", contato.getContatoEmpresa().getEmail());
      LOG.info("corpo do email \r\n{}", body);
      MailMessage email =
          new MailMessage(contato.getContatoEmpresa().getEmail(), ASSUNTO_FALHA, body);
      mailService.sendMail(email);
    }
  }

  /**
   * Formatar email falha.
   *
   * @param logServico the log servico
   * @param contato the contato
   * @param msgErro the msg erro
   * @return the string
   */
  private static String formatarEmailFalha(LogServico logServico, ContatoEmpresaServico contato,
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

}
