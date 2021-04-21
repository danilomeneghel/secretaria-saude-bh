package br.gov.pbh.prodabel.hubsmsa.dto.usuario;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class VisualizarUsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = -1300954050045777006L;

	private Long id;

	private String nome;
	private String email;
	private String login;

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

}
