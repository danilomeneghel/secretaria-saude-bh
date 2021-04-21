package br.gov.pbh.prodabel.hubsmsa.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import br.gov.pbh.prodabel.hubsmsa.enumeration.SortItem;

/**
 * Objeto para paginação de registros
 *
 * @author diogo.matos
 */
public class PaginacaoDTO<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageIndex;
    private Integer pageSize;
    private Long totalResults;
    private SortItem sortItem;
    private transient Map<String, Object> filtros;

    private List<T> itens;

    public PaginacaoDTO() {
        super();
    }

    public PaginacaoDTO(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<T> getItens() {
        return itens;
    }

    public void setItens(List<T> itens) {
        this.itens = itens;
    }

    public Map<String, Object> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, Object> filtros) {
        this.filtros = filtros;
    }


    public SortItem getSortItem() {
        return sortItem;
    }

    public void setSortItem(SortItem sortItem) {
        this.sortItem = sortItem;
    }
}
