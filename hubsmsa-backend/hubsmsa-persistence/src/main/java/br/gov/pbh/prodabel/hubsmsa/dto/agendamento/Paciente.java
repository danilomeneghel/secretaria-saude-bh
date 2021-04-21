package br.gov.pbh.prodabel.hubsmsa.dto.agendamento;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.util.json.notmasked.NotMasked;

@JsonInclude(Include.NON_NULL)
public class Paciente implements Serializable{

  private static final long serialVersionUID = -1565694879025405325L;

  @NotBlank(message = "O campo nomePaciente é obrigatório")
  private String nomePaciente;
  
  @NotBlank(message = "O campo nomeMaePaciente é obrigatório")
  private String nomeMaePaciente;
  
  @NotMasked
  @CPF(message = "CPF inválido.")
  private String cpf;

  @NotBlank(message = "O campo cns é obrigatório")
  @Pattern(regexp = "\\d{15}", message = "O formato do campo cns não é válido.")
  private String cns;

  @NotBlank(message = "O campo dataNascimento é obrigatório")
  private String dataNascimento;

  @NotBlank
  @Pattern(regexp = "[1-3]|[MFI]", message = "O valor informado não é válido para o campo sexo")
  private String sexo;

  @NotBlank(message = "O campo municipioNascimento é obrigatório")
  private String municipioNascimento;

  private Integer distrito;

  private String nomeSocial;

  private String telefone;

  private String telefoneFixoContato;

  private String celularContato;

  @Email(message = "O campo e-mail não é válido.")
  private String email;

  @NotNull
  private Integer codigoMunicipioProcedencia;

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public String getNomeMaePaciente() {
    return nomeMaePaciente;
  }

  public void setNomeMaePaciente(String nomeMaePaciente) {
    this.nomeMaePaciente = nomeMaePaciente;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCns() {
    return cns;
  }

  public void setCns(String cns) {
    this.cns = cns;
  }

  public String getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(String dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getMunicipioNascimento() {
    return municipioNascimento;
  }

  public void setMunicipioNascimento(String municipioNascimento) {
    this.municipioNascimento = municipioNascimento;
  }

  public Integer getDistrito() {
    return distrito;
  }

  public void setDistrito(Integer distrito) {
    this.distrito = distrito;
  }

  public String getNomeSocial() {
    return nomeSocial;
  }

  public void setNomeSocial(String nomeSocial) {
    this.nomeSocial = nomeSocial;
  }

  public String getTelefoneFixoContato() {
    return telefoneFixoContato;
  }

  public void setTelefoneFixoContato(String telefoneFixoContato) {
    this.telefoneFixoContato = telefoneFixoContato;
  }

  public String getCelularContato() {
    return celularContato;
  }

  public void setCelularContato(String celularContato) {
    this.celularContato = celularContato;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getCodigoMunicipioProcedencia() {
    return codigoMunicipioProcedencia;
  }

  public void setCodigoMunicipioProcedencia(Integer codigoMunicipioProcedencia) {
    this.codigoMunicipioProcedencia = codigoMunicipioProcedencia;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

}
