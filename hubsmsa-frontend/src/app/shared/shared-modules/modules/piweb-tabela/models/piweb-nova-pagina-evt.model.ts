export interface PiwebPaginaEvt {
    /**
     * Número da pagina solicitada
     */
    pagina: number;

    /**
     * Quantidade de itens que a pagina deve exibir
     */
    itensPorPagina: number;
}
