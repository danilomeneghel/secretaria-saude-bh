package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum StatusExecucao {
  S("Sucesso"), F("Falha");

  private String name;

  /**
   * Instantiates a new status execucao.
   *
   * @param name the name
   */
  private StatusExecucao(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
