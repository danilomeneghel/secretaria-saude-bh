package br.gov.pbh.prodabel.hubsmsa.exception;

/**
 * Exceção padrão que será propagada para a camada de serviços
 */
public class DAOException extends Exception {

  private static final long serialVersionUID = -653060566410176358L;

  public DAOException() {
    super();
  }

  public DAOException(Throwable cause) {
    super(cause);
  }

  public DAOException(String message) {
    super(message);
  }
}
