package br.gov.pbh.prodabel.hubsmsa.dto.alteracao;

import java.io.Serializable;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;

public class PaginacaoPublicaAlteracaoDTO<T extends Serializable> extends PaginacaoPublicaDTO<T> implements Serializable {

	private static final long serialVersionUID = 1308578700677703742L;
	
	private AssuntoEnum assunto;
	private String descricao;
	
    public PaginacaoPublicaAlteracaoDTO(AssuntoEnum assunto, String descricao) {
        super();
        this.assunto = assunto;
        this.descricao = descricao;
    }
    
    public PaginacaoPublicaAlteracaoDTO() {
        super();
    }
	public AssuntoEnum getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoEnum assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

  
}
