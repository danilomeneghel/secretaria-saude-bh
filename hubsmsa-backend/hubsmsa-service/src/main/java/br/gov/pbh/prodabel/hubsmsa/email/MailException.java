package br.gov.pbh.prodabel.hubsmsa.email;

public class MailException extends RuntimeException {

	private static final long serialVersionUID = 8944177403894763235L;

	public MailException() {
        super();
    }

    public MailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(Throwable cause) {
        super(cause);
    }

}
