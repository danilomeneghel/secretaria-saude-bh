export interface PaginaResultadoPiweb<T> {
    /**
     * Posição atual da página
     */
    paginaIndex?: number;
    /**
     * Quantidade total de registros existentes na base de dados.
     */
    totalRegistros: number;
    /**
     * Lista dos itens que serão listados na pagina atual.
     */
    itens: Array<T>;
}
