package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.model;

import java.util.Date;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

public class SolicitacaoColetaExame extends EntidadeBase<Long> {

  private static final long serialVersionUID = 1649367763777359358L;

  private Long id;

  private String nomePaciente;

  private String nomeMaePaciente;

  private String cpf;

  private String cns;

  private Date dataNascimento;

  private String idade;

  private String sexo;

  private String cor;

  private Long cnesEstabelecimentoPaciente;

  private Long numeroEquipePsfPaciente;

  private Date dataUltimaMenstruacao;

  private Long pesoPaciente;

  private Long alturaPaciente;

  private String pacienteEstaJejum;

  private String pacienteEmPreNatal;

  private Date dataInicioSintoma;

  private String telefonePaciente;

  private String medicamentosEmUso;

  private Long numeroPedido;

  private Long codigoCentroSaude;

  private Long cnesEstabelecimentoSolicitante;

  private String indicadorPedidoSus;

  private String tipoPedido;

  private String statusPedido;

  private String nomeMedico;

  private String cpfMedico;

  private String tipoSolicitante;

  private Long numeroConselhoProfissional;

  private String numeroRegistroProfissional;

  private String ufRegistro;

  private Date dataHoraSolicitacao;

  private Long cnesSolicitante;

  private Long numeroExamePaciente;

  private Long codigoExamePaciente;

  private String descricaoExamePacente;

  private Long codigoMaterial;

  private String descricaoMaterial;

  private String observacoesColeta;

  private Long codigoSigtapExame;

  private Long codigoProcedimentoLocal;

  private Long statusColeta;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Date getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(Date dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getIdade() {
    return idade;
  }

  public void setIdade(String idade) {
    this.idade = idade;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
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

  public Long getNumeroEquipePsfPaciente() {
    return numeroEquipePsfPaciente;
  }

  public void setNumeroEquipePsfPaciente(Long numeroEquipePsfPaciente) {
    this.numeroEquipePsfPaciente = numeroEquipePsfPaciente;
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

  public Long getNumeroPedido() {
    return numeroPedido;
  }

  public void setNumeroPedido(Long numeroPedido) {
    this.numeroPedido = numeroPedido;
  }

  public Long getCodigoCentroSaude() {
    return codigoCentroSaude;
  }

  public void setCodigoCentroSaude(Long codigoCentroSaude) {
    this.codigoCentroSaude = codigoCentroSaude;
  }

  public Long getCnesEstabelecimentoSolicitante() {
    return cnesEstabelecimentoSolicitante;
  }

  public void setCnesEstabelecimentoSolicitante(Long cnesEstabelecimentoSolicitante) {
    this.cnesEstabelecimentoSolicitante = cnesEstabelecimentoSolicitante;
  }

  public String getIndicadorPedidoSus() {
    return indicadorPedidoSus;
  }

  public void setIndicadorPedidoSus(String indicadorPedidoSus) {
    this.indicadorPedidoSus = indicadorPedidoSus;
  }

  public String getTipoPedido() {
    return tipoPedido;
  }

  public void setTipoPedido(String tipoPedido) {
    this.tipoPedido = tipoPedido;
  }

  public String getStatusPedido() {
    return statusPedido;
  }

  public void setStatusPedido(String statusPedido) {
    this.statusPedido = statusPedido;
  }

  public String getNomeMedico() {
    return nomeMedico;
  }

  public void setNomeMedico(String nomeMedico) {
    this.nomeMedico = nomeMedico;
  }

  public String getCpfMedico() {
    return cpfMedico;
  }

  public void setCpfMedico(String cpfMedico) {
    this.cpfMedico = cpfMedico;
  }

  public String getTipoSolicitante() {
    return tipoSolicitante;
  }

  public void setTipoSolicitante(String tipoSolicitante) {
    this.tipoSolicitante = tipoSolicitante;
  }

  public Long getNumeroConselhoProfissional() {
    return numeroConselhoProfissional;
  }

  public void setNumeroConselhoProfissional(Long numeroConselhoProfissional) {
    this.numeroConselhoProfissional = numeroConselhoProfissional;
  }

  public String getNumeroRegistroProfissional() {
    return numeroRegistroProfissional;
  }

  public void setNumeroRegistroProfissional(String numeroRegistroProfissional) {
    this.numeroRegistroProfissional = numeroRegistroProfissional;
  }

  public String getUfRegistro() {
    return ufRegistro;
  }

  public void setUfRegistro(String ufRegistro) {
    this.ufRegistro = ufRegistro;
  }

  public Date getDataHoraSolicitacao() {
    return dataHoraSolicitacao;
  }

  public void setDataHoraSolicitacao(Date dataHoraSolicitacao) {
    this.dataHoraSolicitacao = dataHoraSolicitacao;
  }

  public Long getCnesSolicitante() {
    return cnesSolicitante;
  }

  public void setCnesSolicitante(Long cnesSolicitante) {
    this.cnesSolicitante = cnesSolicitante;
  }

  public Long getNumeroExamePaciente() {
    return numeroExamePaciente;
  }

  public void setNumeroExamePaciente(Long numeroExamePaciente) {
    this.numeroExamePaciente = numeroExamePaciente;
  }

  public Long getCodigoExamePaciente() {
    return codigoExamePaciente;
  }

  public void setCodigoExamePaciente(Long codigoExamePaciente) {
    this.codigoExamePaciente = codigoExamePaciente;
  }

  public String getDescricaoExamePacente() {
    return descricaoExamePacente;
  }

  public void setDescricaoExamePacente(String descricaoExamePacente) {
    this.descricaoExamePacente = descricaoExamePacente;
  }

  public Long getCodigoMaterial() {
    return codigoMaterial;
  }

  public void setCodigoMaterial(Long codigoMaterial) {
    this.codigoMaterial = codigoMaterial;
  }

  public String getDescricaoMaterial() {
    return descricaoMaterial;
  }

  public void setDescricaoMaterial(String descricaoMaterial) {
    this.descricaoMaterial = descricaoMaterial;
  }

  public String getObservacoesColeta() {
    return observacoesColeta;
  }

  public void setObservacoesColeta(String observacoesColeta) {
    this.observacoesColeta = observacoesColeta;
  }

  public Long getCodigoSigtapExame() {
    return codigoSigtapExame;
  }

  public void setCodigoSigtapExame(Long codigoSigtapExame) {
    this.codigoSigtapExame = codigoSigtapExame;
  }

  public Long getCodigoProcedimentoLocal() {
    return codigoProcedimentoLocal;
  }

  public void setCodigoProcedimentoLocal(Long codigoProcedimentoLocal) {
    this.codigoProcedimentoLocal = codigoProcedimentoLocal;
  }

  public Long getStatusColeta() {
    return statusColeta;
  }

  public void setStatusColeta(Long statusColeta) {
    this.statusColeta = statusColeta;
  }

}
