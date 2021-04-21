package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoLogServicoEnum {

  DATAINICIOEVENTO("logServico.dataInicioEvento"), DATAFIMEVENTO(
      "logServico.dataFimEvento"), SERVICO(
      "servico.nome"), SISTEMA(
          "sistemaPrimario.nome, sistemaSecundario.nome"), STATUS("logServico.status");

  private String name;

  /**
   * Instantiates a new coluna ordenacao log servico enum.
   *
   * @param name the name
   */
  private ColunaOrdenacaoLogServicoEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
