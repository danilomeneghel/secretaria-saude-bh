package br.gov.pbh.prodabel.hubsmsa.dto;

import java.io.Serializable;
import java.util.List;

public class PaginacaoPublicaDTO<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -2929325912028046212L;

	private Integer totalRegistros;
    private List<T> itens;

    public PaginacaoPublicaDTO() {
        super();
    }

    public PaginacaoPublicaDTO(Integer totalRegistros, List<T> itens) {
        this.totalRegistros = totalRegistros;
        this.itens = itens;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public List<T> getItens() {
        return itens;
    }

    public void setItens(List<T> itens) {
        this.itens = itens;
    }
}
