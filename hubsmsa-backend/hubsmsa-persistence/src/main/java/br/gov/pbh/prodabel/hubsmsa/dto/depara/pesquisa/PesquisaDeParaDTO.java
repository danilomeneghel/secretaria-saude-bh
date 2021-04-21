package br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa;

import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;

public class PesquisaDeParaDTO extends BaseDTO {

  /**
   * 
   */
  private static final long serialVersionUID = -1669994560655989015L;
  private Long id;
  private String nomeDePara;
  private String nomeTipoDePara;
  private String sistemaPrimario;
  private String sistemaSecundario;
  private String codigosPrimarios;
  private String codigosSecundarios;
  private String status;
  private String descricaoPrimario;
  private String descricaoSecundario;
  private List<DeParaPrimario> listaDeParaPrimario;
  private List<DeParaSecundario> listaDeParaSecundario;
  

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomeDePara() {
    return nomeDePara;
  }

  public void setNomeDePara(String nomeDePara) {
    this.nomeDePara = nomeDePara;
  }

  public String getNomeTipoDePara() {
    return nomeTipoDePara;
  }

  public void setNomeTipoDePara(String nomeTipoDePara) {
    this.nomeTipoDePara = nomeTipoDePara;
  }

  public String getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(String sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public String getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(String sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public String getCodigosPrimarios() {
    return codigosPrimarios;
  }

  public void setCodigosPrimarios(String codigosPrimarios) {
    this.codigosPrimarios = codigosPrimarios;
  }

  public String getCodigosSecundarios() {
    return codigosSecundarios;
  }

  public void setCodigosSecundarios(String codigosSecundarios) {
    this.codigosSecundarios = codigosSecundarios;
  }

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getDescricaoPrimario() {
	return descricaoPrimario;
}

public void setDescricaoPrimario(String descricaoPrimario) {
	this.descricaoPrimario = descricaoPrimario;
}

public String getDescricaoSecundario() {
	return descricaoSecundario;
}

public void setDescricaoSecundario(String descricaoSecundario) {
	this.descricaoSecundario = descricaoSecundario;
}

public List<DeParaSecundario> getListaDeParaSecundario() {
	return listaDeParaSecundario;
}

public void setListaDeParaSecundario(List<DeParaSecundario> listaDeParaSecundario) {
	this.listaDeParaSecundario = listaDeParaSecundario;
}

public List<DeParaPrimario> getListaDeParaPrimario() {
	return listaDeParaPrimario;
}

public void setListaDeParaPrimario(List<DeParaPrimario> listaDeParaPrimario) {
	this.listaDeParaPrimario = listaDeParaPrimario;
}

}
