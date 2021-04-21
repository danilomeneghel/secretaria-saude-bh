package br.gov.pbh.prodabel.hubsmsa.exception;

import br.gov.pbh.prodabel.hubsmsa.mensagem.Mensagem;
import br.gov.pbh.prodabel.hubsmsa.mensagem.MensagemErro;

/**
 * Exceção para Registro não encontrado do tipo {@link RuntimeException}.
 */
public class RegistroNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;

	private Mensagem mensagem;

	public RegistroNaoEncontradoException() {
		super();
	}

	public RegistroNaoEncontradoException(String message) {
		this.mensagem = new MensagemErro(message);
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

}
