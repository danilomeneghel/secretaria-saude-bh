package br.gov.pbh.prodabel.hubsmsa.enumeration;

/***
 * Enum com o sexo do registro- M - Masculino, F - Feminino ou I - Indefinido
 */
public enum SexoEnum {

  M("3"), F("2"), I("1");

  private String name;

  /**
   * Sexo enum.
   *
   * @param name the name
   */
  private SexoEnum(String name) {
      this.name = name;
  }

  public String getName() {
    return name;
  }

}
