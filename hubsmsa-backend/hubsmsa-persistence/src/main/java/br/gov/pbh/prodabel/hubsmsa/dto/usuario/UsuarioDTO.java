package br.gov.pbh.prodabel.hubsmsa.dto.usuario;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.MESSAGE;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.gov.pbh.prodabel.hubsmsa.annotations.NotEmpty;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = 4947798441333645054L;

    @NotEmpty(message = "Nome é obrigatório(a).")
	@Size(max = 100, message = MESSAGE)
	private String username;

    @NotEmpty(message = "E-mail é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
	private String email;

    @NotEmpty(message = "Login é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
	private String firstName;

    @NotEmpty(message = "Login é obrigatório(a).")
	@Size(max = 250, message = MESSAGE)
	private String lastName;

	@NotNull(message = "Login é obrigatório(a).")
	private Boolean emailVerified;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
