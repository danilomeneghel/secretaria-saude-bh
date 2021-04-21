package br.gov.pbh.prodabel.hubsmsa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import br.gov.pbh.prodabel.hubsmsa.mensagem.MensagemSucesso;

/**
 * DTO para ser quando tiver a necessidade de Enviar uma mensagem de retorno junto com outras informações
 * 
 * @param <T>
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> extends BaseDTO {

	private static final long serialVersionUID = 7023313614294945495L;
	
	private T payload;
	private MensagemSucesso mensagem;

	public ResponseDTO() {
	}

	public ResponseDTO(T payload, MensagemSucesso mensagem) {
		this.payload = payload;
		this.mensagem = mensagem;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public MensagemSucesso getMensagem() {
		return mensagem;
	}

	public void setMensagem(MensagemSucesso mensagem) {
		this.mensagem = mensagem;
	}
}
