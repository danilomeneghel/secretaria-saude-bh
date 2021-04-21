package br.gov.pbh.prodabel.hubsmsa.dto;

/**
 * Retorna uma mensagem
 * 
 * @author alysson.cordeiro@ctis.com.br
 *
 */
public class MensagemRetornoDTO extends BaseDTO {

	private static final long serialVersionUID = -2192684310426807375L;

	private String mensagem;
	
	public MensagemRetornoDTO(){
		super();
	}
	
	public MensagemRetornoDTO(String mensagem){
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MensagemRetornoDTO other = (MensagemRetornoDTO) obj;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else {
			if (!mensagem.equals(other.mensagem)) {
				return false;
			}
		}
		
		return true;
	}
}
