package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@SequenceGenerator(sequenceName = "sq_usuario", name = "sq_usuario", allocationSize = 1)
@Table(name = "usuario")
@Audited
public class Usuario extends EntidadeBase<Long> {

	private static final long serialVersionUID = -6360125537072481806L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
	@Column(name = "id_usuario", nullable = false, unique = true)
	private Long id;

	
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Column(name = "login", nullable = false, length = 30)
	private String login;

	@Override
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
