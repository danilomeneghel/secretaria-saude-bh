package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class HistoricoAlteracaoDetalheDTO implements Serializable {

	private static final long serialVersionUID = 5594568027959439900L;
	
	private String nome;
	
	private String email;
	
	private String login;
	
	private String dataEvento;
	
	private Long revisao;
	
	private List<AlteracaoDetalhe> alteracoes;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	public List<AlteracaoDetalhe> getAlteracoes() {
		return alteracoes;
	}
	
	public Long getRevisao() {
		return revisao;
	}

	public void setRevisao(Long revisao) {
		this.revisao = revisao;
	}

	public void setAlteracoes(List<HistoricoAlteracaoDTO> alteracoes) {
		List<AlteracaoDetalhe> listaDetalhes = new ArrayList<>();
		for(HistoricoAlteracaoDTO historico : alteracoes) {
			AlteracaoDetalhe detalhe = new AlteracaoDetalhe();
			
			detalhe.setCampo(historico.getCampo());
			detalhe.setAntes(historico.getDadoAnterior());
			detalhe.setDepois(historico.getDadoAtual());
			listaDetalhes.add(detalhe);
		}
		
		this.alteracoes = listaDetalhes;
	}
	
	@SuppressWarnings("unused")
	private static class AlteracaoDetalhe {
		
		private String campo;
		
		private String antes;
		
		private String depois;

		
		public String getCampo() {
			return campo;
		}

		public void setCampo(String campo) {
			this.campo = campo;
		}

		public String getAntes() {
			return antes;
		}

		public void setAntes(String antes) {
			this.antes = antes;
		}

		public String getDepois() {
			return depois;
		}

		public void setDepois(String depois) {
			this.depois = depois;
		}
		
	}

}
