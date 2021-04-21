package br.gov.pbh.prodabel.hubsmsa.dto.alteracao;

import java.io.Serializable;
import javax.ws.rs.QueryParam;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import io.swagger.v3.oas.annotations.Parameter;

public class FiltroRevisaoAlteracaoDTO extends FiltroRevisaoDTO implements Serializable{

	private static final long serialVersionUID = 8640924253651104812L;
	
	@Parameter(description = "Filtro Assunto", required = false)	
	@QueryParam("assunto")
	@EnumValues(enumClass = AssuntoEnum.class, ignoreCase = true, acceptNull = false, message = "Selecione um assunto para pesquisa de alterações.")
	private String assunto;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	
}
