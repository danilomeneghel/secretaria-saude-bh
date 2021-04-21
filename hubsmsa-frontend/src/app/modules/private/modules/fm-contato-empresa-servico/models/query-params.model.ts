export interface QueryParamsContatoServico {
    idEmpresa: number;
    idContatoEmpresa: number;
    idSistema: number;
    idServico: number;

    ordem: string;
    pagina: number;
    coluna: string;
    itensPorPagina: number;
}
