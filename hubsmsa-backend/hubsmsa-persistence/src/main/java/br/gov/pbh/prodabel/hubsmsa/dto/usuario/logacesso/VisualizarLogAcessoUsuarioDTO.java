package br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class VisualizarLogAcessoUsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = -1300954050045777006L;

	private Long id;

	private String nome;
	private String email;
	private String login;
	private String dataAcesso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(String dataAcesso) {
		this.dataAcesso = dataAcesso;
	}
	
	

}
