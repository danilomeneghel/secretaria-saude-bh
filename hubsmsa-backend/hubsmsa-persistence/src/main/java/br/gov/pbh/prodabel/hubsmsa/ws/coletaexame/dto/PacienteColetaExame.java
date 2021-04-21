package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.util.Date;
import br.gov.pbh.prodabel.hubsmsa.ws.dto.PacienteDTO;

public class PacienteColetaExame extends PacienteDTO {

  private static final long serialVersionUID = -703977641388276991L;

  private String idade;

  private Long numeroProntuarioSisrede;

  private String cor;

  private Long cnesEstabelecimentoPaciente;

  private Long numeroEquipePSFPaciente;

  private Date dataUltimaMenstruacao;

  private Long pesoPaciente;

  private Long alturaPaciente;

  private String pacienteEstaJejum;

  private String pacienteEmPreNatal;

  private Date dataInicioSintoma;

  private String telefonePaciente;

  private String medicamentosEmUso;

  public String getIdade() {
    return idade;
  }

  public void setIdade(String idade) {
    this.idade = idade;
  }

  public Long getNumeroProntuarioSisrede() {
    return numeroProntuarioSisrede;
  }

  public void setNumeroProntuarioSisrede(Long numeroProntuarioSisrede) {
    this.numeroProntuarioSisrede = numeroProntuarioSisrede;
  }

  public String getCor() {
    return cor;
  }

  public void setCor(String cor) {
    this.cor = cor;
  }

  public Long getCnesEstabelecimentoPaciente() {
    return cnesEstabelecimentoPaciente;
  }

  public void setCnesEstabelecimentoPaciente(Long cnesEstabelecimentoPaciente) {
    this.cnesEstabelecimentoPaciente = cnesEstabelecimentoPaciente;
  }

  public Long getNumeroEquipePSFPaciente() {
    return numeroEquipePSFPaciente;
  }

  public void setNumeroEquipePSFPaciente(Long numeroEquipePSFPaciente) {
    this.numeroEquipePSFPaciente = numeroEquipePSFPaciente;
  }

  public Date getDataUltimaMenstruacao() {
    return dataUltimaMenstruacao;
  }

  public void setDataUltimaMenstruacao(Date dataUltimaMenstruacao) {
    this.dataUltimaMenstruacao = dataUltimaMenstruacao;
  }

  public Long getPesoPaciente() {
    return pesoPaciente;
  }

  public void setPesoPaciente(Long pesoPaciente) {
    this.pesoPaciente = pesoPaciente;
  }

  public Long getAlturaPaciente() {
    return alturaPaciente;
  }

  public void setAlturaPaciente(Long alturaPaciente) {
    this.alturaPaciente = alturaPaciente;
  }

  public String getPacienteEstaJejum() {
    return pacienteEstaJejum;
  }

  public void setPacienteEstaJejum(String pacienteEstaJejum) {
    this.pacienteEstaJejum = pacienteEstaJejum;
  }

  public String getPacienteEmPreNatal() {
    return pacienteEmPreNatal;
  }

  public void setPacienteEmPreNatal(String pacienteEmPreNatal) {
    this.pacienteEmPreNatal = pacienteEmPreNatal;
  }

  public Date getDataInicioSintoma() {
    return dataInicioSintoma;
  }

  public void setDataInicioSintoma(Date dataInicioSintoma) {
    this.dataInicioSintoma = dataInicioSintoma;
  }

  public String getTelefonePaciente() {
    return telefonePaciente;
  }

  public void setTelefonePaciente(String telefonePaciente) {
    this.telefonePaciente = telefonePaciente;
  }

  public String getMedicamentosEmUso() {
    return medicamentosEmUso;
  }

  public void setMedicamentosEmUso(String medicamentosEmUso) {
    this.medicamentosEmUso = medicamentosEmUso;
  }

}
