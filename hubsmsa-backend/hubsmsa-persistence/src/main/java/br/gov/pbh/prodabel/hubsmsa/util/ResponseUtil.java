package br.gov.pbh.prodabel.hubsmsa.util;

import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.mensagem.MensagemSucesso;

public final class ResponseUtil {

	private ResponseUtil() {

	}

	public static <T> ResponseDTO<T> montarRetorno(String mensagem, T payload) {
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.setPayload(payload);

		MensagemSucesso mensagemSucesso = new MensagemSucesso(mensagem);
		responseDTO.setMensagem(mensagemSucesso);

		return responseDTO;
	}
}
