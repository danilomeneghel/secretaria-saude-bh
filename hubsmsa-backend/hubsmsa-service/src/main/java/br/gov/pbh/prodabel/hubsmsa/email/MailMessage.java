package br.gov.pbh.prodabel.hubsmsa.email;

import java.util.Arrays;
import java.util.List;

public class MailMessage {

    private final List<String> recipients;
    private final String subject;
    private final String message;

    public MailMessage(List<String> recipients, String subject, String message) {
        super();
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
    }
    
    public MailMessage(String recipients, String subject, String message) {
        super();
        this.recipients = Arrays.asList(recipients);
        this.subject = subject;
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EmailMessage [recipients=" + recipients + ", subject=" + subject + ", message=" + message + "]";
    }

}
