package br.gov.pbh.prodabel.hubsmsa.email;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;

@Stateless
public class MailService {
    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());
            
    @Resource(name = ConstanteUtil.JNDI_SMTP_MAIL)
    private Session session;

    /**
     * Send mail.
     *
     * @param email the email
     */
    @Asynchronous
    public void sendMail(MailMessage email) {

    // teste local inicio
    /*
     * final String username = "teste@teste.com.br"; final String password = "pass";
     * 
     * Properties prop = new Properties(); prop.put("mail.smtp.host", "smtp.umbler.com");
     * prop.put("mail.smtp.port", "587"); prop.put("mail.smtp.auth", "true");
     * prop.put("mail.smtp.starttls.enable", "true"); // TLS
     * 
     * session = Session.getInstance(prop, new javax.mail.Authenticator() {
     * 
     * @Override protected PasswordAuthentication getPasswordAuthentication() { return new
     * PasswordAuthentication(username, password); } });
     */
    // teste local fim (deletar este trecho)

        MimeMessage msg = new MimeMessage(this.session);        
        LOGGER.info(MensagemUtil.getMessage(MensagemEnum.ME030));
        try {
            msg.setFrom(new InternetAddress(ConstanteUtil.SENDER_NO_REPLY));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(email.getRecipients(), ",")));
            msg.setSubject(email.getSubject(), ConstanteUtil.UTF_8);
            msg.setContent(email.getMessage(), ConstanteUtil.CONTENT_TYPE_UTF_8);
            Transport.send(msg);
        } catch (MessagingException e) {
            LOGGER.error(MensagemUtil.getMessage(MensagemEnum.ME029), e);
            throw new MailException(e);
        }
    }
}
