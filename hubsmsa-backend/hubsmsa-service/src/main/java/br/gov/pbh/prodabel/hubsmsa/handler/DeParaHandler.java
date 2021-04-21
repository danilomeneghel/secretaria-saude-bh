package br.gov.pbh.prodabel.hubsmsa.handler;

import java.util.Map;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;

public class DeParaHandler {

  private Map<String, DeParaPrimario> deParaPrimarioMap;

  private Map<String, DeParaSecundario> deParaSecundarioMap;

  public Map<String, DeParaPrimario> getDeParaPrimarioMap() {
    return deParaPrimarioMap;
  }

  public void setDeParaPrimarioMap(Map<String, DeParaPrimario> deParaPrimarioMap) {
    this.deParaPrimarioMap = deParaPrimarioMap;
  }

  public Map<String, DeParaSecundario> getDeParaSecundarioMap() {
    return deParaSecundarioMap;
  }

  public void setDeParaSecundarioMap(Map<String, DeParaSecundario> deParaSecundarioMap) {
    this.deParaSecundarioMap = deParaSecundarioMap;
  }

}
